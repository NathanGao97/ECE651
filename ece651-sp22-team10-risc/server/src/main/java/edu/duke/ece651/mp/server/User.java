package edu.duke.ece651.mp.server;

import edu.duke.ece651.mp.common.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;


public class User implements Runnable {
  final private RiscGameServer server;
  final String username;
  final private String password;
  List<Player> players;
  ObjectInputStream input;
  ObjectOutputStream output;
  boolean loggedIn = true;

  /*
    Constructs user of the server with username and password
   */
  public User(RiscGameServer server, ObjectInputStream in, ObjectOutputStream out, String username, String password) {
    this.server = server;
    this.input = in;
    this.output = out;
    this.username = username;
    this.password = password;
    this.players = new ArrayList<>();
  }

  /*
    Logs in the user
  */
  public boolean login(String username, String password, ObjectInputStream in, ObjectOutputStream out) {
    if (username.equals(this.username) && password.equals(this.password) && !loggedIn) {
      this.input = in;
      this.output = out;
      this.loggedIn = true;
      return true;
    }
    return false;
  }
  
  /*
    This function sends message to the client controlling this user
   */
  public void sendMessage(Message message) throws IOException {
      output.writeObject(message);
      output.flush();
      System.out.print("(" + username + "): ");
      System.out.println(message);
  }

  /*
    This function recv message from the client controlling this user
   */
  public Message recvMessage() throws IOException, ClassNotFoundException {
      Message msg =  (Message) input.readObject();
      System.out.print("(" + username + "): ");
      System.out.println(msg);
      return msg;
  }
  
  /*
    Send object to the client controlling this user
   */
  public void sendObject(Object obj) throws IOException {
    output.reset();
    output.writeObject(obj);
    output.flush();
    System.out.println("Sent " + obj.getClass() + " to user " + username);
  }

  /*
    Let the user to join an open game with the specified game capacity
   */
  private boolean joinOpenGame(int numPlayers) {
    synchronized(server.games) {
      for (RiscGame game : server.games) { //might need lock here
        if (!game.gameStarted && numPlayers == game.getGameCapacity()) {
          Player player = game.joinGame(this);
          if (player != null) {
            players.add(player);
            return true;
          }
        }
      }
    }
    return false;
  }

  /*
    Lets the user play a new game either by joining an open game or creating a new game
   */
  private void playNewGame() throws IOException, ClassNotFoundException {
    Message msg = recvMessage();
    int numPlayers = Integer.parseInt(msg.getType());
    if (numPlayers < 2 || numPlayers > 5) {
      throw new IllegalArgumentException("Invalid game capacity");
    }
    if (!joinOpenGame(numPlayers)) {
      RiscGame game = server.createNewGame(numPlayers);
      Player player = game.joinGame(this);
      players.add(player);
    }
  }

  /*
    Updates the active players that the user controls
   */
  private void updatePlayerList() {
    for (int i = players.size() - 1; i >=0; --i) {
      Player player = players.get(i);
      if (player.hasLost || player.theGame.gameOver()) {
        players.remove(player);
      }
    }
  }

  /*
    Lets the user resume to a game that has not ended
   */
  private void resumeGame() throws IOException, ClassNotFoundException {
    while (true) {
      updatePlayerList();
      for (Player player : players) {
        sendMessage(new Message("continue"));
        sendObject(player.color);
        sendObject(player.theGame.gameId);
        sendObject(player.theGame.gameCapacity);
      }
      sendMessage(new Message("done"));
      Message msg = recvMessage();
      int index = Integer.parseInt(msg.getType());
      if (players.get(index).reconnect(input, output)) {
        break;
      }
      sendMessage(new Message("fail"));
    }
      
  }

  @Override
  public synchronized void run() {
    System.out.println("Running user " + username);
    try {
      sendMessage(new Message("ok"));
      Message msg = recvMessage();
      if (msg.equals(new Message("new"))) {
        playNewGame();
      } else if (msg.equals(new Message("continue"))) {
        resumeGame();
      } else {
        throw new IllegalArgumentException("Invalid request");
      }
      loggedIn = false;
    } catch (Exception e) {
      loggedIn = false;
      e.printStackTrace();
      System.out.println("User " + username + " logged out");
    }
  }

}

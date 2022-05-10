package edu.duke.ece651.mp.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.function.Supplier;

import edu.duke.ece651.mp.common.*;

public class RiscGameServer {
  final private ServerSocket gameServerSocket;
  final private V1MapFactory mapFactory;
  final private AbstractUnitFactory unitFactory;
  final HashMap<Integer, Supplier<RiscMap>> mapCreationFns;
  private HashMap<String, User> users;
  List<RiscGame> games;

  public RiscGameServer(int port, V1MapFactory mapFactory, AbstractUnitFactory unitFactory) throws IOException {
    this.gameServerSocket = new ServerSocket(port);
    gameServerSocket.setSoTimeout(200);
    this.mapFactory = mapFactory;
    this.unitFactory = unitFactory;
    this.mapCreationFns = new HashMap<>();
    this.users = new HashMap<>();
    this.games = new ArrayList<>();
    setupMapCreationMap();
  }

  // Maps the number of players to their corresponding map creation function
  private void setupMapCreationMap() {
    mapCreationFns.put(2, () -> mapFactory.createTwoPlayerMap());
    mapCreationFns.put(3, () -> mapFactory.createThreePlayerMap());
    mapCreationFns.put(4, () -> mapFactory.createFourPlayerMap());
    mapCreationFns.put(5, () -> mapFactory.createFivePlayerMap());
  }

  /* 
     Return false if client enters invalid password or user already loggedIn.
     Otherwise, return true upon successful login
   */
  private synchronized User userLogin(String username, String password, ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
    User user = users.get(username);
    if (user != null) {
      if (user.login(username, password, in, out)) {
        return user;
      }
    }
    return null;
  }

  /* 
     Return false if client enters an already registered username.
     Otherwise, return true upon successful user creation
   */
  public synchronized User createUser(String username, String password, ObjectInputStream in, ObjectOutputStream out) throws IOException, ClassNotFoundException {
    if (!users.containsKey(username)) {
      User user = new User(this, in, out, username, password);
      users.put(username, user);
      return user;
    }
    return null;
  }

  /*
    Handles client initial login or create user
   */
  private void handleLoginOrCreateUser(Socket client) {
    try {
      ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
      output.flush();
      ObjectInputStream input = new ObjectInputStream(client.getInputStream());
      while (true) {
        Message msg = (Message) input.readObject();
        Message username = (Message) input.readObject();
        Message password = (Message) input.readObject();
        User user = null;
        if (msg.equals(new Message("login"))) {
          user = userLogin(username.getType(), password.getType(), input, output);
        } else if (msg.equals(new Message("create"))) {
          user = createUser(username.getType(), password.getType(), input, output);
        } else {
          throw new IllegalArgumentException("Invalid request");
        }
        if(user != null) {
          System.out.println("User " + username.getType() + " logged in");
          Thread th = new Thread(user);
          th.start();
          break;
        }
        System.out.println("User " + username.getType() + " failed to log in");
        output.writeObject(new Message("Failed to login or create user"));
        output.flush();
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  /*
    Listen for client connections
   */
  public void listenForConnection() {
    while (true) {
      try {
        Socket client = gameServerSocket.accept();
        Thread th = new Thread() {
            @Override()
            public void run() {
              handleLoginOrCreateUser(client);
            }
          };
        System.out.println("A client connected");
        th.start();
      } catch (IOException e) {
        
      }
    }
  }

  // Creates game from the given number of players
  public RiscGame createNewGame(int numPlayers) {
    RiscMap map = mapCreationFns.get(numPlayers).get();
    ResultChecker loseChecker = new LoseChecker();
    PlacementRuleChecker placementChecker = new PlacementRuleChecker();
    RiscGame game = new RiscGame(map, numPlayers, unitFactory, loseChecker, placementChecker);
    this.games.add(game);
    return game;
  }

  // Adds player to the game
  private void addPlayerToGame(Color color, RiscGame game) throws IOException {
    Socket client = gameServerSocket.accept();
    ObjectOutputStream output = new ObjectOutputStream(client.getOutputStream());
    output.flush();
    ObjectInputStream input = new ObjectInputStream(client.getInputStream());
    Player player = new Player(game, color, input, output);
    game.players.add(player);
  }

  // Initializes the game by adding players
  public void initializeNewGame(RiscGame game) {
    int playersJoined = 0;
    List<Color> colorList = game.gameMap.getColorList();
    while (playersJoined < game.getGameCapacity()) {
      try {
        addPlayerToGame(colorList.get(playersJoined), game);
        System.out.println("Player " + colorList.get(playersJoined) + " has joined the game");
        playersJoined++;
      } catch (IOException e) {
        // e.printStackTrace();
      }
      
    }
  }
  
  // Starts the game thread of the given game
  public void playGame(RiscGame game) {
    initializeNewGame(game);
    Thread th = new Thread(game);
    th.start();
  }
}

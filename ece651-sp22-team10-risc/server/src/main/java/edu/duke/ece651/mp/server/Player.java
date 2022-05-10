package edu.duke.ece651.mp.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;
import java.util.ArrayList;

import edu.duke.ece651.mp.common.*;

public class Player implements Runnable {
  final RiscGame theGame;
  final Color color;
  private ObjectInputStream input;
  private ObjectOutputStream output;
  final private User user;
  private final List<Unit> unitsToPlace;
  private boolean suspended = false;
  private boolean disconnected = false;
  boolean hasLost = false;
  String stage = "placement";
  int round = 0;
  
  /*
    Constructs a player in theGame controlled by the user.
  */
  public Player(RiscGame theGame, Color color, ObjectInputStream in, ObjectOutputStream out, User user) {
    this.theGame = theGame;
    this.color = color;
    this.input = in;
    this.output = out;
    this.user = user;
    this.unitsToPlace = new ArrayList<>();
    setupUnitsToPlaceList();
  }

  /*
    Constructor for evo1 testcases
  */
  public Player(RiscGame theGame, Color color, ObjectInputStream in, ObjectOutputStream out) {
    this(theGame, color, in, out, new User(null, null, null, null, null));
  }

  /*
    Sets up the units to place for the player
   */
  private void setupUnitsToPlaceList() {
    for (int i = 0; i < 20; i++) {
      unitsToPlace.add(theGame.unitFactory.createChopperUnit(color));
    }
  }

  /*
    This function will suspend this player thread.
  */
  synchronized void suspend() {
    System.out.println("Player " + color + " finished round " + round + " wait for other players");
    //suspended = true;
    round++;
    while (suspended) {
      try {
        // wait for notifyAll
        wait();
      } catch (InterruptedException e) {
        // stop the thread (that runs this player) if we get an thread interupt
        Thread.currentThread().interrupt();
        System.out.println("Thread Interrupted");
      }
    }
    System.out.println("Player " + color + " resumed, start round " + round);
  }

  /*
    This function will resume the player from suspened status
   */
  synchronized void resume() {
    suspended = false;
    //notify the threads that run this player
    //PS: there is just one thread for each player
    notify();
  }

  /*
    This function suspends the player and notify the game to resume if the last player is processed
   */
  private void waitForGameToProcess() {
    // add the current player to the cout of total players
    theGame.addPlayerProcessed();
    suspended = true;
    if (theGame.allPlayersProcessed()) {
      //if all the players are connected
      theGame.resume();
    }
    suspend();
  }
  
  /*
    Barrier to wait for other players to process
   */
  private void waitForOtherPlayers() {
    theGame.addPlayerProcessed();
    suspended = true;
    if (theGame.allPlayersProcessed()) {
      theGame.resetNumPlayersProcessed();
      theGame.resumeAllPlayers();
    }
    suspend();
    
  }

  /*
    Checks if this player has disconnected
   */
  public boolean hasDisconnected() {
    return disconnected;
  }

  /*
    Lets the user reconnect back to this player
   */
  public synchronized boolean reconnect(ObjectInputStream in, ObjectOutputStream out) {
    if (!hasLost && !theGame.gameOver() && disconnected) {
      this.input = in;
      this.output = out;
      disconnected = false;
      sendMessage(new Message("ok"));
      sendMessage(new Message(stage));
      sendObject(theGame.gameMap);
      return true;
    }
    return false;
  }
  
  /*
    This function sends message to the client controlling this player
   */
  public void sendMessage(Message message) {
    try {
      output.writeObject(message);
      output.flush();
      System.out.println("Sent " + color + ": " + message);
    } catch (IOException e) {
      //System.out.println("Player " + color + " has left game " +  theGame.gameId);
      disconnected = true;
      user.loggedIn = false;
    }
  }

  /*
    This function recv message from the client controlling this player
   */
  private Message recvMessage() {
    try {
      Message msg =  (Message) input.readObject();
      System.out.println("Receive " + msg);
      /*
      if (msg.equals(new Message("exit"))) {
        // need to handle exit game
        disconnected = true;
        Thread th = new Thread(user);
        th.start();
      }
      */
      return msg;
    } catch (IOException e) {
      //System.out.println("Player " + color + " has left game " +  theGame.gameId);
      disconnected = true;
      user.loggedIn = false;
    } catch (ClassNotFoundException e) {      
    }
    return null;
  }

  /*
    Send object to the client controlling this player
   */
  public void sendObject(Object obj) {
    try {
      output.reset();
      output.writeObject(obj);
      output.flush();
      System.out.println("Sent " + color + ": " + obj.getClass());
    } catch (IOException e) {
      //System.out.println("Player " + color + " has left game " +  theGame.gameId);
      disconnected = true;
      user.loggedIn = false;
    }
  }

  /*
    check whether the mess is owned by the player specified by color
   */
  private String checkMessageOwnership(Message message) {
    if (message.getOwner() == null || !message.getOwner().equals(color)) {
      return "Invalid message owner";
    }
    return null;
  }

  /*
    Process this player after the game has ended for this player
   */
  public synchronized void doPostGamePhase() {
    sendObject(theGame.gameMap);
    if (hasLost && theGame.gameOver()) {
      sendMessage(new Message("done"));
    } else if (hasLost) {
      sendMessage(new Message("lose"));
      theGame.addAudience(this);
    } else {
      sendMessage(new Message("win"));
    }
    
  }

  /*
    Process this player for the order issue phase
  */
  public void doIssueOrderPhase() {
    while (!hasLost && !theGame.gameOver()) {
      playOneTurn();
    }
  }

  /*
    Process this player to play one turn
  */  
  public synchronized void playOneTurn() {
    sendObject(theGame.gameMap);
    sendMessage(new Message("continue"));
    waitForOtherPlayers();
    while (!disconnected) {
      Message order = recvMessage();
      if (order != null) {
        String result = checkMessageOwnership(order);
        if (result == null) {
          if (order.getType().equals("commit")) {
            sendObject(theGame.currentEvents);
            break;
          } else {
            result = theGame.tryExecuteOrder(order);
          }
        }
        sendMessage(new Message(result));
      }
    }
    waitForGameToProcess();
  }

  /*
    Do placement phase for the players who have disconnected
   */
  private void finishPlacement() {
    Territory terr = theGame.gameMap.getTerritoryListOwnedByColor(color).get(0);
    Message placement = new Message("placement", color, terr.getName(), "", new ArrayList(unitsToPlace));    
    theGame.tryExecutePlacement(placement, unitsToPlace);
  }
  
  /*
    Process this player to do placement
  */
  public synchronized void doPlacementPhase() {
    sendObject(color);
    sendObject(theGame.gameMap);
    waitForOtherPlayers();
    while (unitsToPlace.size() > 0 && !disconnected) {
      Message placement = recvMessage();
      if (placement != null) {
        String result = checkMessageOwnership(placement);
        if (result == null) {
          result = theGame.tryExecutePlacement(placement, unitsToPlace);
          if (unitsToPlace.size() == 0) {
            result = "commit";
          }
        }
        sendMessage(new Message(result, null, "", "", placement.getAllocatedUnits())); // does not send color in the message CHANGE
      }
    }    
    this.stage = "order";
    if (unitsToPlace.size() > 0) {
      finishPlacement();
    }
    waitForGameToProcess();
  }
  
  @Override
  public void run() {
    doPlacementPhase();
    System.out.println("Player " + color + " do order issue phase");
    doIssueOrderPhase();
    System.out.println("Player " + color + " do post game phase");
    doPostGamePhase();
  }

  /*
    Get the represent color of the player
  */
  public Color showIdentity() {
    return color;
  }

}

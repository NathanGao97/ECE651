package edu.duke.zg79.battleship;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.function.Function;

/**
 * This class represent a text player of the battleship game
 */
public class TextPlayer {

  /**
   * The name of the player
   */
  public final String name;

  /**
   * The reamaining count of move actions
   */
  public Integer moveCounts;

  /**
   * The remaining count of the scan actions
   */
  public Integer scanCounts;
  
  /**
   * The battleship board
   */
  public final Board<Character> theBoard;
  
  /**
   * The textual dispaly information of the board 
   */
  public final BoardTextView view;

  /**
   * The input from player
   */
  public final BufferedReader inputReader;

  /**
   * The output to player
   */
  public final PrintStream out;

  /**
   * The factory of the ship
   */
  public final AbstractShipFactory<Character> shipFactory;

  /**
   * The list of ship names that we want to work from
   */
  public final ArrayList<String> shipsToPlace;

  /**
   * The map from ship name to the lambda to create it
   */
  public final HashMap<String, Function<Placement, Ship<Character>>> shipCreationFns;

  /**
   * The status to show whether the player has lost the game
   */
  public SimpleCompletionRules<Character> myStatus;

  /**
   * This get the name of the player
   *
   * @return the name
   */
  protected String getName() {
    return this.name;
  }

  /**
   * This get the board of the coordinate
   *
   * @return the board
   */
  protected Board<Character> getBoard() {
    return this.theBoard;
  }
  
  /**
   * Constructs a textplayer of the battleship game  
   */
  public TextPlayer(String name, Board<Character> theBoard, BufferedReader inputSource, PrintStream out, V2ShipFactory factory) {
    this.name = name;
    this.moveCounts = 3;
    this.scanCounts = 3;
    this.theBoard = theBoard;
    this.view = new BoardTextView(theBoard);
    this.inputReader = inputSource;
    this.out = out;
    this.shipFactory = factory;
    this.shipsToPlace = new ArrayList<String>();
    this.shipCreationFns = new HashMap<String, Function<Placement, Ship<Character>>>();
    this.myStatus = new SimpleCompletionRules<Character>(theBoard);
    setupShipCreationList();
    setupShipCreationMap();
  }

  /**
   * This set up the map of different kinds of ships for creation  
   */
  protected void setupShipCreationMap() {
    shipCreationFns.put("Submarine", (p) -> shipFactory.makeSubmarine(p));
    shipCreationFns.put("BattleShip", (p) -> shipFactory.makeBattleship(p));
    shipCreationFns.put("Carrier", (p) -> shipFactory.makeCarrier(p));
    shipCreationFns.put("Destroyer", (p) -> shipFactory.makeDestroyer(p));
  }

  /**
   * This set up the list of different kinds of ships for creation  
   */
  protected void setupShipCreationList() {
    shipsToPlace.addAll(Collections.nCopies(2, "Submarine"));
    shipsToPlace.addAll(Collections.nCopies(3, "Destroyer"));
    shipsToPlace.addAll(Collections.nCopies(3, "BattleShip"));
    shipsToPlace.addAll(Collections.nCopies(2, "Carrier"));
  }
  
  /**
   * This read input from player and get the placement information  
   */
  public Placement readPlacement(String prompt) throws IOException {
    out.println(prompt);
    String s = inputReader.readLine();
    if (s == null) {
      throw new EOFException();
    }
    return new Placement(s);
  }

  /**
   * This add one ship to the borad and then display the borad  
   */
  public void doOnePlacement(String shipName, Function<Placement, Ship<Character>> createFn) throws IOException { 
    Placement p = null;
    try { p = readPlacement("Player " + name + " where do you want to place a " + shipName + "?"); // read a Placement
    }
    catch (IllegalArgumentException e) { // invalid placement with incorrect input information
      out.println(e.getMessage()); // print out the message to user
      doOnePlacement(shipName, createFn); // read input again
      return;
    }
    Ship<Character> s = null;
    try {
      s = createFn.apply(p); // create a ship based on the location in that Placement
    }
    catch(IllegalArgumentException e) {
      out.println(e.getMessage()); // print out the message to user
      doOnePlacement(shipName, createFn); // read input again
      return;
    }
    String res = theBoard.tryAddShip(s); // add that ship to the board  
    if (res != null) { // invalid placement that violates placement rules. fail to add ship
      out.println(res); // print out the message to user      
      doOnePlacement(shipName, createFn); // read input again
      return;
    }    
    out.print(view.displayMyOwnBoard()); // print out the board
  }

  /**
   * This generate the make placement phase for players during the battleship game 
   */
  public void doPlacementPhase() throws IOException {
    out.print(view.displayMyOwnBoard()); // the initial empty board
    // the instruction
    out.print("Player " + name + ":"
              + " you are going to place the following ships (which are all rectangular)."
              + " For each ship, type the coordinate of the upper left side of the ship,"
              + " followed by either H (for horizontal) or V (for vertical)."
              + "  For example M4H would place a ship horizontally starting at M4 and going to the right."
              + "  You have\n\n"
              + "2 \"Submarines\" ships that are 1x2\n"
              + "3 \"Destroyers\" that are 1x3\n"
              + "3 \"Battleships\" that are 1x4\n"
              + "2 \"Carriers\" that are 1x6\n");
    for (String s : shipsToPlace) { // call doOnePlacement iteratively from the list of ships for creation
      doOnePlacement(s, shipCreationFns.get(s));
    }
  } 

  /**
   * This check whether the player has lost the game
   *
   * @return true if the player has lost the game, false if not
   */
  public boolean checkLose() {
    return myStatus.checkCompletion();
  }

  /**
   * This check whether the coordinate is valid or not
   *
   * @throws IllegalArgumentException if the row or column are invalid
   * @return the coordinate to fire at if it is valid
   */
  private Coordinate coordinateCheck(String s, Board<Character> Board) {
    int width = Board.getWidth();
    int height = Board.getHeight();
   
    // check the input string format
    if (s.length() != 2) { // input string should be a string of length 2   
      throw new IllegalArgumentException("That coordinate is invalid: it does not have the correct format.");
    }
    
    // check the first character, which should be a letter
    char rowletter = s.charAt(0); // row letter at index 0
    char rowletterUp = Character.toUpperCase(rowletter);  // convert letters from lower case to upper case
    if (rowletterUp < 'A' || rowletterUp > 'Z') { // the range of row letter should be from 'A' to 'Z'
      throw new IllegalArgumentException("That coordinate is invalid: it does not have the correct format.");
    }
    int row = rowletterUp - 'A';
    if (row < 0 || row > height - 1) { // row out of bound
      throw new IllegalArgumentException("That coordinate is invalid: it is out of the board.");
    }

    // check the second character, which should be a digit
    char columnletter = s.charAt(1); // column letter at index 1
    String columnString = s.substring(1);  
    if (columnletter < '0' || columnletter > '9') {  // the column should be a single digit
        throw new IllegalArgumentException("That coordinate is invalid: it does not have the correct format.");
    }
    int column = Integer.parseInt(columnString);
    if (column < 0 || column > width - 1) { // column out of bound
      throw new IllegalArgumentException("That coordinate is invalid: it is out of the board.");
    }
    Coordinate c = new Coordinate(s);
    return c;
  }

  /**
   * This get a valid coordinate to fire at
   *
   * @return the coordinate to fire at, which is valid
   */
  private Coordinate getFireCoordinate(Board<Character> enemyBoard) throws IOException {
    String firePrompt = "Please choose a coordinate to fire at:";
    Coordinate c = null;
    // try until the input is a vaild coordinate to fire at
    boolean status = false;
    while(!status) {
      try {
        out.println(firePrompt);
        String s = inputReader.readLine();
        c = coordinateCheck(s, enemyBoard); // check the validity of coordinate
        status = true; // valid input
      }
      catch (IllegalArgumentException e) {
        out.println(e.getMessage()); // print the exception message out     
        continue; // try again
      }
    }
    return c; 
  }

  /**
   * This perform a one-turn fire action of a player
   */
  public void doFire(Board<Character> enemyBoard) throws IOException {
    Coordinate c = getFireCoordinate(enemyBoard); // get a valid coordinate to fire at
    
    Ship<Character> target = enemyBoard.fireAt(c); // find what is located att the coordinate   
    if (target == null) { // a miss
      String missResult = "You missed!";
      out.println(missResult);
    }
    else { // a hit
      String hitResult = "You hit a " + target.getName() + "!";
      out.println(hitResult);
    }
  }

  /**
   * This get the ship to move
   *
   * @return the ship to move
   */
  private Ship<Character> getShipToMove() throws IOException {
    String prompt = "Please choose a ship to move:";
    out.println(prompt);
    Coordinate c = null;
    String s = inputReader.readLine();
    c = coordinateCheck(s, theBoard); // check the validity of coordinate
    Ship<Character> ship = theBoard.getShipAt(c);
    if (ship == null) {
      throw new IllegalArgumentException("That coordinate is invalid: no ship is found."); 
    }
    return ship;
  }

  /**
   * This get the placement to move to
   *
   * @return the placement to move to
   */
  private Placement getMovePlacement(String name) throws IOException {
    String prompt = "Please enter the location to move your ship to:";
    out.println(prompt);
    Placement p = null;
    String s = inputReader.readLine();
    p = new Placement(s);
    return p;
  }
  
  /**
   * This perform a one-turn move action of a player
   */
  public void doMove() throws IOException {
    Ship<Character> toMove = getShipToMove(); // get the ship to move
    String name = toMove.getName();
    Placement p = getMovePlacement(name); // get the placement to move to
    Ship<Character> newShip = null;
    if (name.equals("Submarine")) {
      newShip = shipFactory.makeSubmarine(p);
    }
    if (name.equals("Destroyer")) {
      newShip = shipFactory.makeDestroyer(p);
    }
    if (name.equals("Battleship")) {
      newShip = shipFactory.makeBattleship(p);
    }
    if (name.equals("Carrier")) {
      newShip = shipFactory.makeCarrier(p);
    }
    theBoard.moveShip(toMove, newShip, p); // move the ship
    out.println("Move Successfully!");
  }

  /**
   * This get a valid coordinate as the center for sonar scan
   *
   * @return the center coordinate for sonar scan, which is valid
   */
  private Coordinate getScanCoordinate(Board<Character> enemyBoard) throws IOException {
    String prompt = "Please choose the center coordinate for sonar scan:";
    Coordinate c = null;
    // try until the input is a vaild coordinate for sonar scan
    boolean status = false;
    while(!status) {
      try {
        out.println(prompt);
        String s = inputReader.readLine();
        c = coordinateCheck(s, enemyBoard); // check the validity of coordinate
        status = true; // valid input
      }
      catch (IllegalArgumentException e) {
        out.println(e.getMessage()); // print the exception message out     
        continue; // try again
      }
    }
    return c; 
  }
  
  /**
   * This perform a one-turn scan action of a player
   */
  public void doScan(Board<Character> enemyBoard) throws IOException {
    Coordinate c = getScanCoordinate(enemyBoard); // get a valid center coordinate for sonar scan
    int width = enemyBoard.getWidth();
    int height = enemyBoard.getHeight();
    int row = c.getRow();
    int column = c.getColumn();
    Integer submairneNum = 0; // number of submarines in range
    Integer destroyerNum = 0; // number of destroyers in range
    Integer battleshipNum = 0; // number of battleships in range
    Integer carrierNum = 0; // number of carriers in range
    // use a list to store all the vaild coordinates to take sonar scan
    ArrayList<Coordinate> scanCoordinates= new ArrayList<Coordinate>();

    // add all valid coordinates into the list
    if (0 <= row - 3 && row - 3 <= width - 1 && 0 <= column && column <= height - 1) {
      scanCoordinates.add(new Coordinate(row - 3, column));
    }
    for (int j = column - 1; j <= column + 1; j++) {
      int i = row - 2;
      if (0 <= i && i <= width - 1 && 0 <= j && j <= height - 1) {
        scanCoordinates.add(new Coordinate(i, j));
      }
    }
    for (int j = column - 2; j <= column + 2; j++) {
      int i = row - 1;
      if (0 <= i && i <= width - 1 && 0 <= j && j <= height - 1) {
        scanCoordinates.add(new Coordinate(i, j));
      }
    }
    for (int j = column - 3; j <= column + 3; j++) {
      int i = row;
      if (0 <= i && i <= width - 1 && 0 <= j && j <= height - 1) {
        scanCoordinates.add(new Coordinate(i, j));
      }
    }
    for (int j = column - 2; j <= column + 2; j++) {
      int i = row + 1;
      if (0 <= i && i <= width - 1 && 0 <= j && j <= height - 1) {
        scanCoordinates.add(new Coordinate(i, j));
      }
    }
    for (int j = column - 1; j <= column + 1; j++) {
      int i = row + 2;
      if (0 <= i && i <= width - 1 && 0 <= j && j <= height - 1) {
        scanCoordinates.add(new Coordinate(i, j));
      }
    }
    if (0 <= row + 3 && row + 3 <= width - 1 && 0 <= column && column <= height - 1) {
      scanCoordinates.add(new Coordinate(row + 3, column));
    }

    // count ships in range
    for (Coordinate scanCoordinate : scanCoordinates) {
      Ship<Character> ship = enemyBoard.getShipAt(scanCoordinate);
      if (ship != null) {
        if (ship.getName().equals("Submarine")) {
          submairneNum += 1;
        }
        if (ship.getName().equals("Destroyer")) {
          destroyerNum += 1;
        }
        if (ship.getName().equals("Battleship")) {
          battleshipNum += 1;
        }
        if (ship.getName().equals("Carrier")) {
          carrierNum += 1;
        }
      }
    }

    // print out the scan result
    String res = "Submarines occupy " + submairneNum + " squares\n" +
      "Destroyers occupy " + destroyerNum + "squares\n" +
      "Battleships occupy " + battleshipNum + " squares\n" +
      "Carriers occupy " + carrierNum + " squares\n";
    out.print(res);
  }
  
  /**
   * This check whether the choice is a valid action 
   *
   * @throws IllegalArgumentException if the choice is invalid
   * @return the chosen action
   *
   */
  public Character choiceCheck(String s, Board<Character> enemyBoard) throws IOException {
    // check the input string format
    if (s.length() != 1) { // input string should be a string of length 2
      throw new IllegalArgumentException("That choice is invalid: it does not have the correct format.");
    }
    // get the choice letter
    String sUp = s.toUpperCase();
    Character choice = sUp.charAt(0);
    if (choice != 'F' && choice != 'M' && choice != 'S') { // invalid choice letter
      throw new IllegalArgumentException("That choice is invalid: " + choice);
    }
    if (choice == 'M' && moveCounts == 0) { // no more move action available
      throw new IllegalArgumentException("That choice is invalid: No more available move actions.");
    }
    if (choice == 'S' && scanCounts == 0) { // no more scan action available
      throw new IllegalArgumentException("That choice is invalid: No more available scan actions.");
    }
    return choice;
  }

  /**
   * This perform a choose action process of a player
   */
  public void doChooseAction(Board<Character> enemyBoard) throws IOException {
    String prompt = "Possible actions for Player " + name + ":\n\n\n" +
      "F Fire at a square\n" +
      "M Move a ship to another square (" + moveCounts +" remaining)\n" +
      "S Sonar scan (" + scanCounts + " remaining)\n\n\n" +
      "Player " + name + ", what would you like to do?";
    Character choice = null;
    // try until the input is a vaild coordinate to fire at
    boolean status = false;
    while(!status) {
      try {
        out.println(prompt);
        String s = inputReader.readLine();
        choice = choiceCheck(s, enemyBoard); // check the validity of choice
        status = true; // valid input
      }
      catch (IllegalArgumentException e) {
        out.println(e.getMessage()); // print the exception message out     
        continue; // try again
      }
    }
    if (choice == 'F') { // fire action
      doFire(enemyBoard);
    } 
    if (choice == 'M') { // move action
      try {
        doMove();
        moveCounts -= 1;
      }
      catch (IllegalArgumentException e) {
        out.println(e.getMessage());
        doChooseAction(enemyBoard);
        return;
      }
    }
    if (choice == 'S') { // scan action
      scanCounts -= 1;
      doScan(enemyBoard);
    }
  }
  
  /**
   * This perform a one-turn action of a player
   */
  public void playOneTurn(Board<Character> enemyBoard, BoardTextView enemyView, String enemyName) throws IOException {
    String myTurn = "Player " + name + "'s turn:";
    out.println(myTurn); // print my turn prompt
    String myHeader = "Your ocean";
    String enemyHeader = "Player " + enemyName + "'s ocean";
    out.println(view.displayMyBoardWithEnemyNextToIt(enemyView, myHeader, enemyHeader)); // print the display information of two boards
    doChooseAction(enemyBoard);
  }
  
}

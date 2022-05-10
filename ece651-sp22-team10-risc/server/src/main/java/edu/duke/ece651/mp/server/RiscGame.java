package edu.duke.ece651.mp.server;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;
import edu.duke.ece651.mp.common.*;

public class RiscGame implements Runnable {
  private static int assignId = 0;
  final int gameId;
  final RiscMap gameMap;
  final AbstractUnitFactory unitFactory;
  final private HashMap<String, Actioner> actions;
  final private HashMap<String, Actioner> specialEvents;
  final List<Message> currentEvents;
  final private HashMap<String, OrderRuleChecker> orderCheckers;
  final private PlacementRuleChecker placementChecker;
  final private ResultChecker loseChecker;
  final List<Player> players;
  final List<Player> audiences;
  final int gameCapacity;
  private int numPlayersProcessed = 0;
  boolean gameOver = false;
  boolean gameStarted = false;

  /*
    @param gameMap: the map used by the game
    @param gameCapacity: how many players in this game
    @param orderBuffer: buffer used to store the different types or order (move/attack) 
    @param unitFactory: factory that will be used to create units
    @param loseChecker: check win or lose
    @param placementChecker: check whether the placement is correct
    */

  public RiscGame(RiscMap gameMap, int gameCapacity, AbstractUnitFactory unitFactory, ResultChecker loseChecker, PlacementRuleChecker placementChecker) {
    this.gameId = assignId;
    assignId++;
    this.gameMap = gameMap;
    this.unitFactory = unitFactory;
    this.actions = new HashMap<>();
    setupActionMap();
    this.specialEvents = new HashMap<>();
    setupSpecialEventMap();
    this.currentEvents = new ArrayList<>();
    this.orderCheckers = new HashMap<>();
    setupOrderCheckingMap();
    this.loseChecker = loseChecker;
    this.placementChecker = placementChecker;
    this.players = new ArrayList<>();
    this.audiences = new ArrayList<>();
    this.gameCapacity = gameCapacity;
  }

  /*
    This function will put the orders into the corresponding rule checker
   */
  
  private void setupOrderCheckingMap() {
    OrderRuleChecker moveRuleChecker = new SourceTerritoryRuleChecker(
        new DestinationTerritoryMoveRuleChecker(new UnitsRuleChecker(new ValidPathRuleChecker(null))));
    OrderRuleChecker attackRuleChecker = new SourceTerritoryRuleChecker(
        new DestinationTerritoryAttackRuleChecker(new UnitsRuleChecker(new AdjacentTerritoryRuleChecker(null))));
    orderCheckers.put("attack", attackRuleChecker);
    orderCheckers.put("move", moveRuleChecker);
    orderCheckers.put("unitUpgrade", new UpgradeUnitChecker(null));
    orderCheckers.put("techUpgrade", new UpgradeTechLevelChecker(null));
    orderCheckers.put("cloak", new CloakTerritoryChecker(null));
    orderCheckers.put("researchCloak", new GetCloakChecker(null));
  }

  /*
    set up the action map
   */  
  private void setupActionMap() {
    actions.put("attack", new AttackActioner());
    actions.put("move", new MoveActioner());
    actions.put("techUpgrade", new TechUpgradeActioner());
    actions.put("unitUpgrade", new UnitUpgradeActioner());
    actions.put("cloak", new CloakActioner());
    actions.put("researchCloak", new GetCloakActioner());
  }

  private void setupSpecialEventMap() {
    specialEvents.put("thunder", new ThunderActioner());
    specialEvents.put("degenerate", new DegenerateActioner());
    specialEvents.put("colossus", new ColossusActioner());
    specialEvents.put("goodHarvest", new GoodHarvestActioner());
    specialEvents.put("inferno", new InfernoActioner());
    specialEvents.put("fury", new FuryActioner());
    specialEvents.put("pandemic", new PandemicActioner());
  }

  /* 
     get how many players in this game
   */
  public int getGameCapacity() {
    return gameCapacity;
  }

  /*
    add players in this game
   */
  public synchronized Player joinGame(User user) {
    if (players.size() < gameCapacity && !gameStarted) {
      Color color = gameMap.getColorList().get(players.size());
      Player player = new Player(this, color, user.input, user.output, user);
      players.add(player);
      System.out.println("User " + user.username + " has joined game " + gameId + " as player " + color);
      if (players.size() == gameCapacity) {
        gameStarted = true;
        Thread th = new Thread(this);
        th.start();
      }
      return player;
    }
    return null;
  }

  /*
    check whether the placement is valid
   */
  public String checkPlacement(Message placement, List<Unit> remaining) {
    if (placement.getType() == null || !placement.getType().equals("placement")) {
      return "Invalid placement type";
    }
    return placementChecker.checkMyRule(placement, gameMap, remaining);
  }

  /*
    execute the placement
   */
  public synchronized String tryExecutePlacement(Message placement, List<Unit> remaining) {
    String result = checkPlacement(placement, remaining);
    if (result == null) {
      Territory terr = gameMap.getTerritoryByName(placement.getSource());
      for (Unit unit : placement.getAllocatedUnits()) {
        terr.addUnit(unit);
        remaining.remove(unit);
      }
    }
    return result;
  }

  /*
    check whether the order is valid order
   */
  public String checkOrder(Message order) {
    if (order.getType() == null || orderCheckers.get(order.getType()) == null) {
      return "Invalid order type";
    }
    return orderCheckers.get(order.getType()).checkOrder(this, order);
  }

  /*
    exectue the orders
  */
  public synchronized String tryExecuteOrder(Message order) {
    String result = checkOrder(order);
    if (result == null) {
      actions.get(order.getType()).performAction(order, gameMap);
    }
    return result;
  }
  
  /*
   * This function performs the attack section of the attack order.
   */
  protected void resolveBattle() {
    Set<Territory> battleTerritoryList = new HashSet<>();
    for (Territory territory : gameMap.getTerritoryList()) {
      if (territory.hasEnemy()) {
        battleTerritoryList.add(territory);
      }
    }
    for (Territory territory : battleTerritoryList) {
      Set<Color> attackers = territory.getEnemies();
      Color defender = territory.getOwner();
      Random rand = new Random();
      for (Color attacker : attackers) {
        Unit attackerUnit = territory.removeHighestBonusUnitByColor(attacker);
        Unit defenderUnit = territory.removeLowestBonusUnitByColor(defender);
        boolean attackerAdvantage = true;
        while (attackerUnit != null && defenderUnit != null) {
          //System.out.println(territory.allyUnits);
          //System.out.println(territory.enemyUnits);
          Unit survived = attackerUnit.battle(defenderUnit, rand);
          territory.addUnit(survived);
          //System.out.println(territory.allyUnits);
          //System.out.println(territory.enemyUnits);
          //System.out.println("---------------------------------------------------------");
          attackerAdvantage = !attackerAdvantage;
          if(attackerAdvantage) {
            attackerUnit = territory.removeHighestBonusUnitByColor(attacker);
            defenderUnit = territory.removeLowestBonusUnitByColor(defender);
          } else {
            attackerUnit = territory.removeLowestBonusUnitByColor(attacker);
            defenderUnit = territory.removeHighestBonusUnitByColor(defender);
          }
        }
        if (defenderUnit == null) {
          territory.addUnit(attackerUnit);
          defender = attacker;
          territory.transferOwnershipTo(defender);
        } else {
          territory.addUnit(defenderUnit);
        }
      }
      territory.cleanEnemies();
      //System.out.println(territory.allyUnits);
      //System.out.println(territory.enemyUnits);
      //System.out.println("---------------------------------------------------------");
    }
  }

  /*
    update the game and the player status
   */
  public void updateGameAndPlayerStatuses() {
    // TODO: implement this
    int dcCount = 0;
    for (int i = players.size() - 1; i >= 0; --i) {
      Player player = players.get(i);
      if (loseChecker.resultCheck(gameMap, player.color)) {
        player.hasLost = true;
        player.resume();
        players.remove(player);
      } else if (player.hasDisconnected()) {
        dcCount++;
      }
    }
    if (players.size() <= 1 || dcCount == players.size()) {
      gameOver = true;
    }
  }

  /*
    Updates the map resrouces by adding units to each territory, and resources to each color
   */
  public void updateGameContent() {
    for (Color color : gameMap.getColorList()) {
      color.emptyVisibility();
      for (Territory terr : gameMap.getTerritoryList()) {
        updateTerritoryVisbilityByColor(color, terr);
      }
      System.out.println(color.visible_territories);
    }
    for (Territory terr : gameMap.getTerritoryList()) {
      terr.addUnit(unitFactory.createChopperUnit(terr.getOwner()));
      terr.updateOwnerResource();
      terr.updateCloakCount();
    }
    for (Message event : currentEvents) {
      specialEvents.get(event.getType()).performAction(event, gameMap);
    }
  }

  public void updateTerritoryVisbilityByColor(Color color, Territory territory) {
    if (territory.getOwner().equals(color)) {
      color.addVisibleTerritory(territory.getName());
      return;
    }
    if (territory.neighbourOfColor(color)) {
      System.out.println("Territory " + territory.getName() + " is cloaked = " + territory.isCloaked());
      if (!territory.isCloaked()) {
        color.addVisibleTerritory(territory.getName());
        return;
      }
      if (territory.hasSpyOfColor(color)) {
        color.addVisibleTerritory(territory.getName());
        return;
      }
    } else if (territory.hasSpyOfColor(color)) {
      color.addVisibleTerritory(territory.getName());
      return;
    }
  }

  //stop the game
  public boolean gameOver() {
    return gameOver;
  }
  
  public synchronized void addPlayerProcessed() {
    numPlayersProcessed++;
  }
  
  //check whether all the players have processed
  public synchronized boolean allPlayersProcessed() {
    return numPlayersProcessed == players.size();
  }

  
  public void resetNumPlayersProcessed() {
    numPlayersProcessed = 0;
  }

  // Initialize the players by starting their threads
  public void initializePlayers() {
    resetNumPlayersProcessed();
    for (Player player : players) {
      Thread th = new Thread(player);
      th.start();
    }
  }

  // Facilitate do placement phase
  public void doPlacementPhase() {
    waitForAllPlayersToProcess();
    updateGameAndPlayerStatuses();
    resumeAllPlayers();
  }

  public void generateSpecialEvents() {
    Random rand = new Random();
    this.currentEvents.clear();
    String[] eventList = specialEvents.keySet().toArray(new String[0]);
    for (Color color : gameMap.getColorList()) {
      String event = eventList[rand.nextInt(eventList.length)];
      this.currentEvents.add(new Message(event, color));
    }
  }


  // Facilitate order phase
  public void doIssueOrderPhase() {
    while (!gameOver) {
      generateSpecialEvents();
      waitForAllPlayersToProcess();
      resolveBattle();
      updateGameAndPlayerStatuses();
      updateGameContent();
      System.out.println("Game complete update");
      resumeAllPlayers();
      sendMapToAudiences();
    }
  }



  public synchronized void addAudience(Player audience) {
    this.audiences.add(audience);
  }

  // Send map to audiences
  public void sendMapToAudiences() {
    for (Player audience : audiences) {
      if (!audience.hasDisconnected()) {
        audience.sendObject(gameMap);
        if (gameOver) {
          audience.sendMessage(new Message("done"));
        } else {
          audience.sendMessage(new Message("continue"));
        }
      }
    }
  }

  @Override
  public void run() {
    System.out.println("Starting game " + gameId);
    initializePlayers();
    doPlacementPhase();
    doIssueOrderPhase();
    System.out.println("Game " + gameId + " over");
  }

  // Wait for all players to process
  public synchronized void waitForAllPlayersToProcess() {
    while (!allPlayersProcessed()) {
      try {
        System.out.println("Game wait for players to process");
        wait();
        System.out.println("Game resumed");
      }
      catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
    resetNumPlayersProcessed();
  }

  public synchronized void resume() {
    //notify the threads that run this player
    //PS: there is just one thread for each player
    notify();
  }


  public void resumeAllPlayers() {
    for (Player player : players) {
      player.resume();
    }
  }

  // Returns the player with the given color
  public Player getPlayerByColor(Color color) {
    for (Player player : players) {
      if (player.color.equals(color)) {
        return player;
      }
    }
    return null;
  }
}

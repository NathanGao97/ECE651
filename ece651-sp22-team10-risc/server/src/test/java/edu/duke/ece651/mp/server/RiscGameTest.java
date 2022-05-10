package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import edu.duke.ece651.mp.common.*;  
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;


public class RiscGameTest {
  @Test
  public void test_constructor(){
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame game = new RiscGame(map, 10, unit_factory, lose_checker, place_checker);  
  }  

  @Test                                                                                                           
  public void test_placement() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame game = new RiscGame(map, 10, unit_factory, lose_checker, place_checker);
    Message mess = new Message(null, new Color("red"), null, null, new ArrayList<>());    
    
  }

  @Test                                                                              
  public void test_get_player() {
    V1MapFactory factory = new V1MapFactory();                                       
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame game = new RiscGame(map, 10, unit_factory, lose_checker, place_checker);
    assertEquals(null, game.getPlayerByColor(new Color("Green")));
  }

  @Test                                                                              
  public void test_join_game()  throws IOException, InterruptedException, ClassNotFoundException {
    V1MapFactory factory = new V1MapFactory();                                       
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame game = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    V1MapFactory mf = new V1MapFactory();
    AbstractUnitFactory uf = new V1UnitFactory();
    int port = 14567;
    
    RiscGameServer server = new RiscGameServer(port, mf, uf);
    ObjectOutputStream output = new ObjectOutputStream(new ByteArrayOutputStream());
    User u0 = server.createUser("asd", "qwe", null, output);
    User u1 = server.createUser("xyz", "zyx", null, output);
    User u2 = server.createUser("bgt", "tgb", null, output);
    game.joinGame(u0);
    game.joinGame(u1);
    game.joinGame(u2);
  }

  @Test                                                                              
  public void test_invalid_placement()  throws IOException, InterruptedException, ClassNotFoundException {
    V1MapFactory factory = new V1MapFactory();                                       
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame game = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    Message mess = new Message("", new Color("Red"));
    AbstractUnitFactory unitFactory = new V1UnitFactory();
    List<Unit> remainings = unitFactory.createUnitsByType("Chopper", new Color("Red"), 2); 
    assertEquals("Invalid placement type", game.checkPlacement(mess, remainings));
    assertEquals("Invalid order type", game.checkOrder(mess));
    game.tryExecutePlacement(mess, remainings);
    game.tryExecuteOrder(mess);
  }

  @Test                                                                              
  public void test_battle()  throws IOException, InterruptedException, ClassNotFoundException {
    V1MapFactory factory = new V1MapFactory();                                       
    RiscMap map = factory.createFivePlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame game = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    Message mess = new Message("", new Color("Red"));
    AbstractUnitFactory unitFactory = new V1UnitFactory();
    List<Unit> unit1 = unitFactory.createUnitsByType("Chopper", new Color("Red"), 20);
    List<Unit> unit2 = unitFactory.createUnitsByType("Nami", new Color("Blue"), 20);
    List<Unit> unit3 = unitFactory.createUnitsByType("Zoro", new Color("Green"), 20);
    List<Unit> unit4 = unitFactory.createUnitsByType("Zoro", new Color("Red"), 20);
    List<Unit> unit5 = unitFactory.createUnitsByType("Nami", new Color("Blue"), 1);
    List<Unit> unit6 = unitFactory.createUnitsByType("Chopper", new Color("Green"), 1);
    Territory Asshai = map.getTerritoryByName("Asshai");
    Territory Astapor = map.getTerritoryByName("Astapor");
    for (Unit unit : unit1) {
      Asshai.addUnit(unit);
    }
    for (Unit unit : unit2) {
      Asshai.addUnit(unit);
      
    }
    for (Unit unit : unit3) {
      Asshai.addUnit(unit);
    }
    for (Unit unit : unit4) {
      Astapor.addUnit(unit);
    }
    for (Unit unit : unit5) {
      Astapor.addUnit(unit);
    }
    for (Unit unit : unit6) {
      Astapor.addUnit(unit);
    }
    game.resolveBattle();
  }

  @Test                                                                              
  public void test_duo_fight()  throws IOException, InterruptedException, ClassNotFoundException {
    V1MapFactory factory = new V1MapFactory();                                       
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame game = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    Message mess = new Message("", new Color("Red"));
    AbstractUnitFactory unitFactory = new V1UnitFactory();
    List<Unit> unit1 = unitFactory.createUnitsByType("Chopper", new Color("Red"), 20);
    List<Unit> unit2 = unitFactory.createUnitsByType("Nami", new Color("Blue"), 20);
    Territory Asshai = map.getTerritoryByName("Asshai");
    for (Unit unit : unit1) {
      Asshai.addUnit(unit);
    }
    for (Unit unit : unit2) {
      Asshai.addUnit(unit); 
    }
    game.resolveBattle();
    game.updateGameAndPlayerStatuses();
    game.sendMapToAudiences();
  }

  @Test                                                                              
  public void test_lose()  throws IOException, InterruptedException, ClassNotFoundException {
    V1MapFactory factory = new V1MapFactory();                                       
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame game = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    Message mess = new Message("", new Color("Red"));

    V1MapFactory mf = new V1MapFactory();
    AbstractUnitFactory uf = new V1UnitFactory();
    int port = 14818;
    RiscGameServer server = new RiscGameServer(port, mf, uf);
    ObjectOutputStream output = new ObjectOutputStream(new ByteArrayOutputStream());
    User u0 = server.createUser("asd", "qwe", null, output);
    User u1 = server.createUser("xyz", "zyx", null, output);
    game.joinGame(u0);
    game.joinGame(u1);
    Territory Astapor = map.getTerritoryByName("Astapor");
    Territory Meereen = map.getTerritoryByName("Meereen");
    Territory Pentos = map.getTerritoryByName("Pentos");
    Astapor.transferOwnershipTo(new Color("Red"));
    Meereen.transferOwnershipTo(new Color("Red"));
    Pentos.transferOwnershipTo(new Color("Red"));
    game.updateGameAndPlayerStatuses();
    game.sendMapToAudiences();
  }

  @Test
  public void test_generateSpecialEvents() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createFivePlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame game = new RiscGame(map, 5, unit_factory, lose_checker, place_checker);
    game.generateSpecialEvents();
    System.out.println(game.currentEvents);
  }

  @Test
  public void test_updateVisibility() {
    V1MapFactory factory = new V1MapFactory();
    RiscMap map = factory.createFivePlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame game = new RiscGame(map, 5, unit_factory, lose_checker, place_checker);
    map.getTerritoryByName("Karhold").addUnit(unit_factory.createSpyUnit(new Color("Red")));
    map.getTerritoryByName("Rainwood").addUnit(unit_factory.createSpyUnit(new Color("Red")));
    map.getTerritoryByName("Rainwood").cloak();
    map.getTerritoryByName("Meereen").cloak();
    map.getTerritoryByName("Tyrosh").addUnit(unit_factory.createSpyUnit(new Color("Red")));
    map.getTerritoryByName("Tyrosh").cloak();
    game.updateGameContent();
  }
}

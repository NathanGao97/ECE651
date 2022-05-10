package edu.duke.ece651.mp.server;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import edu.duke.ece651.mp.common.*;
import org.junit.jupiter.api.Test;

public class ValidPathRuleCheckerTest {
  private List<Unit> addNBasicUnitToTerritory(Territory terr, Color unitColor, int n) {
    List<Unit> result = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      if (terr != null) {
        terr.addUnit(new BasicUnit(unitColor));
      }
      result.add(new BasicUnit(unitColor));
    }
    return result;
  }
  
  @Test
  public void test_vaild_path() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    OrderRuleChecker checker = new ValidPathRuleChecker(null);
    Color red = new Color("Red");
    for (Color c : map.getColorList()) {
      if (c.equals(red)) {
        red = c;
      }
    }
    Territory redTerr = map.getTerritoryByName("Asshai");
    Message message = new Message("move", red, "Asshai", "Meereen", addNBasicUnitToTerritory(redTerr, red, 4));
    String res = checker.checkMyRule(myGame, message);
    assertEquals(null, res);
    Message message2 = new Message("move", red, "Asshai", "Meereen", addNBasicUnitToTerritory(redTerr, red, 51));
    String res2 = checker.checkMyRule(myGame, message2);
    assertEquals("Invalid Move: no enough food resource!", res2);
    addNBasicUnitToTerritory(null, red, 50);
  }

  @Test
  public void test_vaild_long_path() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createFourPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 4, unit_factory, lose_checker, place_checker);
    List<Territory> territoryList = myGame.gameMap.getTerritoryList();
    for (Territory territory : territoryList) {
      if (territory.getName().equals("Rainwood")) {
        territory.transferOwnershipTo(new Color("Red"));
        assertEquals(territory.getOwner(), new Color("Red"));
      }
    }
    OrderRuleChecker checker = new ValidPathRuleChecker(null);
    Color red = new Color("Red");
    Territory redTerr = myGame.gameMap.getTerritoryByName("Asshai");
    Message message = new Message("move", red, "Asshai", "Rainwood", addNBasicUnitToTerritory(redTerr, red, 4));
    String res = checker.checkMyRule(myGame, message);
    assertEquals(null, res);
  }
  
  @Test
  public void test_invaild_path() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    OrderRuleChecker checker = new ValidPathRuleChecker(null);
    Color red = new Color("Red");
    Territory redTerr = map.getTerritoryByName("Asshai");
    Message message = new Message("move", red, "Asshai", "Sunspear", addNBasicUnitToTerritory(redTerr, red, 4));
    String res = checker.checkMyRule(myGame, message);
    String expected = "Invalid Move: Cannot reach Destination Territory from Source Territory!";
    assertEquals(expected, res);
  }

  @Test
  public void test_valid_move_spy_units() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    OrderRuleChecker checker = new ValidPathRuleChecker(null);
    Color red = new Color("Red");
    Territory Asshai = map.getTerritoryByName("Asshai");
    Unit Spy = unit_factory.createSpyUnit(red);
    Asshai.addUnit(Spy);
    Asshai.addUnit(Spy);
    Territory Astapor = map.getTerritoryByName("Astapor");
    Astapor.addUnit(Spy);
    Astapor.addUnit(Spy);
    Message message1 = new Message("move", red, "Asshai", "Astapor", unit_factory.createUnitsByType("Spy", red, 2));
    String res1 = checker.checkMyRule(myGame, message1);
    assertEquals(null, res1);
    Message message2 = new Message("move", red, "Asshai", "Meereen", unit_factory.createUnitsByType("Spy", red, 2));
    String res2 = checker.checkMyRule(myGame, message2);
    assertEquals(null, res2);
    Message message3 = new Message("move", red, "Asshai", "Braavos", unit_factory.createUnitsByType("Spy", red, 2));
    String res3 = checker.checkMyRule(myGame, message3);
    assertEquals(null, res3);
    Message message4 = new Message("move", red, "Astapor", "Meereen", unit_factory.createUnitsByType("Spy", red, 2));
    String res4 = checker.checkMyRule(myGame, message4);
    assertEquals(null, res4);

  }

  @Test
  public void test_invalid_move_spy_units() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createThreePlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    OrderRuleChecker checker = new ValidPathRuleChecker(null);
    Color red = new Color("Red");
    Territory Asshai = map.getTerritoryByName("Asshai");
    Unit Spy = unit_factory.createSpyUnit(red);
    Asshai.addUnit(Spy);
    Asshai.addUnit(Spy);
    Territory Norvos = map.getTerritoryByName("Astapor");
    Norvos.addUnit(Spy);
    Norvos.addUnit(Spy);
    Message message1 = new Message("move", red, "Asshai", "Pentos", unit_factory.createUnitsByType("Spy", red, 2));
    String res1 = checker.checkMyRule(myGame, message1);
    assertEquals("Invalid Move: Spy can not reach destination territory in one turn!", res1);
    Territory Pentos = map.getTerritoryByName("Pentos");
    Pentos.transferOwnershipTo(red);
    Message message2 = new Message("move", red, "Asshai", "Sunspear", unit_factory.createUnitsByType("Spy", red, 2));
    String res2 = checker.checkMyRule(myGame, message2);
    assertEquals("Invalid Move: Spy can not reach destination territory in one turn!", res2);
    Message message3 = new Message("move", red, "Norvos", "Rainwood", unit_factory.createUnitsByType("Spy", red, 2));
    String res3 = checker.checkMyRule(myGame, message3);
    assertEquals("Invalid Move: Spy in enemy territory can only move 1 territory at a time!", res3);
  }
}

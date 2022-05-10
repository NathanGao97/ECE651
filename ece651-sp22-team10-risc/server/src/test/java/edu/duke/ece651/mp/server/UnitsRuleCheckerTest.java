package edu.duke.ece651.mp.server;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import edu.duke.ece651.mp.common.*;
import org.junit.jupiter.api.Test;

public class UnitsRuleCheckerTest {
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
  public void test_vaild_move_units() { 
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    OrderRuleChecker checker = new UnitsRuleChecker(null);
    Color red = new Color("Red");
    Territory redTerr = map.getTerritoryByName("Asshai");
    Message message = new Message("move", red, "Asshai", "Braavos", addNBasicUnitToTerritory(redTerr, red, 4));
    String res = checker.checkMyRule(myGame, message);
    assertEquals(null, res);
  }

  @Test
  public void test_invaild_move_units() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    OrderRuleChecker checker = new UnitsRuleChecker(null);
    Color red = new Color("Red");
    Territory redTerr = map.getTerritoryByName("Asshai");
    addNBasicUnitToTerritory(redTerr, red, 4);
    Message message = new Message("move", red, "Asshai", "Braavos", addNBasicUnitToTerritory(null, red, 6));
    String res = checker.checkMyRule(myGame, message);
    String expected = "Invalid Operation: Allocated Units are not available in Source Territory!";
    assertEquals(expected, res);
  }

  @Test
  public void test_vaild_attack_units() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    OrderRuleChecker checker = new UnitsRuleChecker(null);
    Color red = new Color("Red");
    Territory redTerr = map.getTerritoryByName("Asshai");
    Message message = new Message("attack", red, "Asshai", "Astapor", addNBasicUnitToTerritory(redTerr, red, 4));
    String res = checker.checkMyRule(myGame, message);
    assertEquals(null, res);
  }

  @Test
  public void test_invaild_attack_units() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    OrderRuleChecker checker = new UnitsRuleChecker(null);
    Color red = new Color("Red");
    Territory redTerr = map.getTerritoryByName("Asshai");
    Message message = new Message("attack", red, "Astapor", "Astapor", addNBasicUnitToTerritory(redTerr, red, 8));
    String res = checker.checkMyRule(myGame, message);
    String expected = "Invalid Operation: Allocated Units are not available in Source Territory!";
    assertEquals(expected, res);
  }

  @Test
  public void test_attack_spy_units() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    OrderRuleChecker checker = new UnitsRuleChecker(null);
    Color red = new Color("Red");
    Territory redTerr = map.getTerritoryByName("Asshai");
    Message message = new Message("attack", red, "Astapor", "Astapor", unit_factory.createUnitsByType("Spy", red, 2));
    String res = checker.checkMyRule(myGame, message);
    String expected = "Invalid Operation: A Spy could not be allocated for attack!";
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
    OrderRuleChecker checker = new UnitsRuleChecker(null);
    Color red = new Color("Red");
    Territory redTerr = map.getTerritoryByName("Asshai");
    Unit Spy = unit_factory.createSpyUnit(red);
    redTerr.addUnit(Spy);
    redTerr.addUnit(Spy);
    Message message = new Message("move", red, "Asshai", "Astapor", unit_factory.createUnitsByType("Spy", red, 2));
    String res = checker.checkMyRule(myGame, message);
    assertEquals(null, res);
  }

  @Test
  public void test_invalid_move_spy_units() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    OrderRuleChecker checker = new UnitsRuleChecker(null);
    Color red = new Color("Red");
    Territory redTerr = map.getTerritoryByName("Asshai");
    Message message = new Message("move", red, "Asshai", "Astapor", unit_factory.createUnitsByType("Spy", red, 2));
    String res = checker.checkMyRule(myGame, message);
    String expected = "Invalid Operation: Allocated Units are not available in Source Territory!";
    assertEquals(expected, res);
  }
}

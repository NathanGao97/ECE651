package edu.duke.ece651.mp.server;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import edu.duke.ece651.mp.common.*;
import org.junit.jupiter.api.Test;

public class SourceTerritoryRuleCheckerTest {
  @Test
  public void test_vaild_source_territory() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    OrderRuleChecker checker = new SourceTerritoryRuleChecker(null);
    Color color = new Color("Red");
    Message message = new Message("move", color, "Asshai", "Braavos", unit_factory.createUnitsByType("Chopper", color, 1));
    String res = checker.checkMyRule(myGame, message);
    assertEquals(null, res);
  }

  @Test
  public void test_invaild_source_territory() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    OrderRuleChecker checker = new SourceTerritoryRuleChecker(null);
    Color color = new Color("Red");
    Message message = new Message("move", color, "Astapor", "Braavos", unit_factory.createUnitsByType("Chopper", color, 1));
    String res = checker.checkMyRule(myGame, message);
    String expected = "Invalid Move: The Source Territory does not belong to you!";
    assertEquals(expected, res);
  }

  @Test
  public void test_source_territory_for_spy_move() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    OrderRuleChecker checker = new SourceTerritoryRuleChecker(null);
    Color color = new Color("Red");
    Message message = new Message("move", color, "Astapor", "Braavos", unit_factory.createUnitsByType("Spy", color, 1));
    String res = checker.checkMyRule(myGame, message);
    assertEquals(null, res);
  }
}

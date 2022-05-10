package edu.duke.ece651.mp.server;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import edu.duke.ece651.mp.common.*;
import org.junit.jupiter.api.Test;

public class DestinationTerritoryMoveRuleCheckerTest {
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
  public void test_vaild_destination_territory() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    OrderRuleChecker checker = new DestinationTerritoryMoveRuleChecker(null);
    Color red = new Color("Red");
    Territory redTerr = map.getTerritoryByName("Asshai");
    Message message = new Message("move", red, "Asshai", "Braavos", addNBasicUnitToTerritory(redTerr, red, 4));
    String res = checker.checkMyRule(myGame, message);
    assertEquals(null, res);
    addNBasicUnitToTerritory(null, red, 4);
  }

  @Test
  public void test_invaild_destination_territory() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    OrderRuleChecker checker = new DestinationTerritoryMoveRuleChecker(null);
    Color red = new Color("Red");
    Territory redTerr = map.getTerritoryByName("Astapor");
    Message message = new Message("move", red, "Astapor", "Meereen", addNBasicUnitToTerritory(redTerr, red, 4));
    String res = checker.checkMyRule(myGame, message);
    String expected = "Invalid Move: The Destination Territory does not belong to you!";
    assertEquals(expected, res);
  }

  @Test
  public void test_destination_territory_for_spy_move() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    OrderRuleChecker checker = new DestinationTerritoryMoveRuleChecker(null);
    Color red = new Color("Red");
    Territory redTerr = map.getTerritoryByName("Astapor");
    Message message = new Message("move", red, "Astapor", "Meereen", unit_factory.createUnitsByType("Spy", red, 1));
    String res = checker.checkMyRule(myGame, message);
    assertEquals(null, res);
  }
}

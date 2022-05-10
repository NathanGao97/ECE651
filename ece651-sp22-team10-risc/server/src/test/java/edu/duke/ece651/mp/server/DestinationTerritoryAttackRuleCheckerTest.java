package edu.duke.ece651.mp.server;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.io.IOException;
import static org.junit.jupiter.api.Assertions.*;
import edu.duke.ece651.mp.common.*;
import org.junit.jupiter.api.Test;

public class DestinationTerritoryAttackRuleCheckerTest {
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
    OrderRuleChecker checker = new DestinationTerritoryAttackRuleChecker(null);
    Color red = new Color("Red");
    for (Color c : map.getColorList()) {
      if (c.equals(red)) {
        red = c;
      }
    }
    Territory redTerr = map.getTerritoryByName("Asshai");
    Message message = new Message("attack", red, "Asshai", "Astapor", addNBasicUnitToTerritory(redTerr, red, 4));
    //String res = checker.checkMyRule(myGame, message);
    String res = myGame.tryExecuteOrder(message);
    assertEquals(null, res);
    Message message2 = new Message("attack", red, "Asshai", "Astapor", addNBasicUnitToTerritory(redTerr, red, 500));
    //String res2 = checker.checkMyRule(myGame, message2);
    String res2 = myGame.tryExecuteOrder(message2);
    assertEquals("Invalid Attack: no enough food resource!", res2);
    assertEquals(red.getFoodResource(), 96);
    addNBasicUnitToTerritory(null, red, 50);
  }

  @Test
  public void test_invaild_destination_territory() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    OrderRuleChecker checker = new DestinationTerritoryAttackRuleChecker(null);
    Color red = new Color("Red");
    Territory redTerr = map.getTerritoryByName("Asshai");
    Message message = new Message("attack", red, "Asshai", "Braavos", addNBasicUnitToTerritory(redTerr, red, 4));
    String res = checker.checkMyRule(myGame, message);
    String expected = "Invalid Attack: The Destination Territory is not a target";
    assertEquals(expected, res);
  }
}

package edu.duke.ece651.mp.server;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import edu.duke.ece651.mp.common.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.io.IOException;
public class AdjacentTerritoryRuleCheckerTest {
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
  public void test_vaild_adjacent_territory() {
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);  
    OrderRuleChecker checker = new AdjacentTerritoryRuleChecker(null);
    Color red = new Color("Red");
    Territory redTerr = map.getTerritoryByName("Asshai");
    Message message = new Message("attack", red, "Asshai", "Astapor", addNBasicUnitToTerritory(redTerr, red, 4));
    String res = checker.checkMyRule(myGame, message);
    assertEquals(null, res);
    addNBasicUnitToTerritory(null, red, 4);
  }

  @Test
  public void test_invaild_adjacent_territory() { 
    V1MapFactory factory = new V1MapFactory();                                                 
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);  
    OrderRuleChecker checker = new AdjacentTerritoryRuleChecker(null);
    Color red = new Color("Red");
    Territory redTerr = map.getTerritoryByName("Asshai");
    Message message = new Message("attack", red, "Asshai", "Pentos", addNBasicUnitToTerritory(redTerr, red, 4));
    String res = checker.checkMyRule(myGame, message);
    String expected = "Invalid Attack: cannot reach Destination Territory!";
    assertEquals(expected, res);
  }

}

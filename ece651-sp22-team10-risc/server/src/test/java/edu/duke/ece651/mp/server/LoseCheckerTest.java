package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.*;
import edu.duke.ece651.mp.common.*;
import java.util.List;

import org.junit.jupiter.api.Test;

public class LoseCheckerTest {
 @Test
  public void test_invalid_lose() {
    V1MapFactory mapFactory = new V1MapFactory();
    RiscMap myMap = mapFactory.createTwoPlayerMap();
    Color myColor = new Color("Red");
    LoseChecker myChecker = new LoseChecker();
    boolean res = myChecker.resultCheck(myMap, myColor);
    assertEquals(false, res);
  }

  @Test
  public void test_valid_lose() {
    V1MapFactory mapFactory = new V1MapFactory();
    RiscMap myMap = mapFactory.createTwoPlayerMap();
    Color myColor = new Color("Red");
    Color enemyColor = new Color("Blue");
    List<Territory> territoryList = myMap.getTerritoryList();
    for (Territory territory : territoryList) {
      territory.transferOwnershipTo(enemyColor);
    }
    LoseChecker myChecker = new LoseChecker();
    boolean res = myChecker.resultCheck(myMap, myColor);
    assertEquals(true, res);

  } 
}

package edu.duke.ece651.mp.common;
import edu.duke.ece651.mp.common.*;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ColossusActionerTest {
  @Test
  public void test_colossus() {
    ColossusActioner myActioner = new ColossusActioner();
    V1MapFactory myFactory = new V1MapFactory();
    AbstractUnitFactory unitFactory = new V1UnitFactory();
    RiscMap myMap = myFactory.createTwoPlayerMap();
    Message order = new Message("colossus", new Color("Red"));
    List<Territory> myTerritories = myMap.getTerritoryListOwnedByColor(new Color("Red"));
    int kaidouNum = 0;
    for (Territory territory : myTerritories) {
      kaidouNum += territory.getAllyUnitSizeByType("Kaidou");
    }
    assertEquals(0, kaidouNum);
    myActioner.performAction(order, myMap);
    assertEquals(0, kaidouNum);
    kaidouNum = 0;
    for (Territory territory : myTerritories) {
      kaidouNum += territory.getAllyUnitSizeByType("Kaidou");
    }
    assertEquals(1, kaidouNum);
  }

}

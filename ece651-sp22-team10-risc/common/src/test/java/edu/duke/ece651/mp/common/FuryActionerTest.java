package edu.duke.ece651.mp.common;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class FuryActionerTest {
  @Test
  public void testFuryActioner() {
    FuryActioner myActioner = new FuryActioner();
    V1MapFactory myFactory = new V1MapFactory();
    AbstractUnitFactory unitFactory = new V1UnitFactory();
    RiscMap myMap = myFactory.createTwoPlayerMap();

    Message order = new Message("fury", new Color("Red"));
    Color red = new Color("Red");

    for (Territory territory : myMap.getTerritoryListOwnedByColor(red)) {
      Unit myUnit = unitFactory.createChopperUnit(red);
      territory.allyUnits.add(myUnit);
    }

    myActioner.performAction(order, myMap);

    for (Territory territory : myMap.getTerritoryListOwnedByColor(red)) {
      assertEquals(3, territory.allyUnits.get(0).getBonus());
    }
  }
}

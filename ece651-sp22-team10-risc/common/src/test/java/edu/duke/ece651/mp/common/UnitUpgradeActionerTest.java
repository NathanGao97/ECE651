package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class UnitUpgradeActionerTest {
  @Test
  public void test_unit_upgrade() {
    UnitUpgradeActioner myActioner = new UnitUpgradeActioner();
    V1MapFactory myFactory = new V1MapFactory();
    AbstractUnitFactory unitFactory = new V1UnitFactory();
    RiscMap myMap = myFactory.createTwoPlayerMap();
    List<Unit> allocatedUnits = unitFactory.createChopperUnit(new Color("Blue"), 2);
    Message order = new Message("unitUpgrade", new Color("Blue"), "Astapor", allocatedUnits, "Usopp");
    Territory Astapor = myMap.getTerritoryByName("Astapor");
    for (Unit unit : allocatedUnits) {
      Astapor.addUnit(unit);
    }
    List<Color> colorList = myMap.getColorList();
    Color myColor = null;
    for (Color color : colorList) {
      if (color.equals(order.getOwner())) {
        myColor = color;
      }
    }
    assertEquals(2, Astapor.getAllyUnitSizeByType("Chopper"));
    assertEquals(0, Astapor.getAllyUnitSizeByType("Usopp"));
    assertEquals(50, myColor.getSanityResource());
    myActioner.performAction(order, myMap);
    assertEquals(28, myColor.getSanityResource());
    assertEquals(0, Astapor.getAllyUnitSizeByType("Chopper"));
    assertEquals(2, Astapor.getAllyUnitSizeByType("Usopp"));
  }

}

package edu.duke.ece651.mp.common;
import edu.duke.ece651.mp.common.*;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class DegenerateActionerTest {
  @Test
  public void test_degenerate() {
    DegenerateActioner myActioner = new DegenerateActioner();
    V1MapFactory myFactory = new V1MapFactory();
    AbstractUnitFactory unitFactory = new V1UnitFactory();
    RiscMap myMap = myFactory.createTwoPlayerMap();
    Message order = new Message("degenerate", new Color("Red"));
    Territory Astapor = myMap.getTerritoryByName("Astapor");
    List<Unit> namis = unitFactory.createNamiUnit(new Color("Blue"), 2);
    for (Unit nami : namis) {
      Astapor.addUnit(nami);
    }
    int chopperNum = Astapor.getAllyUnitSizeByType("Chopper");
    int namiNum = Astapor.getAllyUnitSizeByType("Nami");
    assertEquals(0, chopperNum);
    assertEquals(2, namiNum);
    for (int i = 0; i < 100; i++) {
      myActioner.performAction(order, myMap);
    }
    chopperNum = Astapor.getAllyUnitSizeByType("Chopper");
    namiNum = Astapor.getAllyUnitSizeByType("Nami");
    assertEquals(2, chopperNum);
    assertEquals(0, namiNum);
  }

}

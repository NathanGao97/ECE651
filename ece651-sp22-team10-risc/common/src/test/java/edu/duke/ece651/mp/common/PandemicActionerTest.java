package edu.duke.ece651.mp.common;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PandemicActionerTest {
  @Test
  public void test_pandemic() {
    PandemicActioner myActioner = new PandemicActioner();
    V1MapFactory myFactory = new V1MapFactory();
    AbstractUnitFactory unitFactory = new V1UnitFactory();
    RiscMap myMap = myFactory.createTwoPlayerMap();
    List<Unit> allyUnits = unitFactory.createNamiUnit(new Color("Red"), 2);
    List<Unit> enemyUnits = unitFactory.createNamiUnit(new Color("Blue"), 2);
    Territory Asshai = myMap.getTerritoryByName("Asshai");
    for (Unit unit : allyUnits) {
      Asshai.addUnit(unit);
    }
    for (Unit unit : enemyUnits) {
      Asshai.addUnit(unit);
    }
    List<Unit> allyNamis = unitFactory.createChopperUnit(new Color("Red"), 3);
    List<Unit> enemyNamis = unitFactory.createChopperUnit(new Color("Blue"), 3);
    for (Unit unit : allyNamis) {
      Asshai.addUnit(unit);
    }
    for (Unit unit : enemyNamis) {
      Asshai.addUnit(unit);
    }
    Message order = new Message("pandemic", new Color("Red"));
    assertEquals(5, Asshai.allyUnits.size());
    assertEquals(5, Asshai.enemyUnits.get(new Color("Blue")).size());
    myActioner.performAction(order, myMap);
    assertEquals(3, Asshai.allyUnits.size());
    assertEquals(3, Asshai.enemyUnits.get(new Color("Blue")).size());
  }

}

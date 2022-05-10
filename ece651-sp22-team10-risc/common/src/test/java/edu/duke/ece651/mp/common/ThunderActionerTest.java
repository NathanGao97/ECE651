package edu.duke.ece651.mp.common;
import edu.duke.ece651.mp.common.*;

import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ThunderActionerTest {
  @Test
  public void testThunder() {
    ThunderActioner myActioner = new ThunderActioner();
    V1MapFactory myFactory = new V1MapFactory();
    AbstractUnitFactory unitFactory = new V1UnitFactory();
    RiscMap myMap = myFactory.createTwoPlayerMap();

    List<Territory> territory_list = myMap.getTerritoryList();
    Territory target = territory_list.get(0);
    Color red = new Color("red");
    Color blue = new Color("blue");

    Unit myUnit = unitFactory.createChopperUnit(red);
    Unit enemyUnit = unitFactory.createChopperUnit(blue);
    Unit spy = unitFactory.createSpyUnit(blue);

    target.addUnit(myUnit);
    target.addUnit(enemyUnit);
    target.addUnit(spy);

    Message order = new Message("thunder", new Color("Blue"));
    
    for (int i = 0; i < 1000; i++) {
      myActioner.performAction(order, myMap);
    }

    assertEquals(0, target.allyUnits.size());
    assertEquals(0, target.enemyUnits.size());
    assertEquals(0, target.spyUnits.size());
  }
}

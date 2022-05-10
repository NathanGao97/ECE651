package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import edu.duke.ece651.mp.common.*;

public class PlacementRuleCheckerTest {
  @Test
  public void test_placement() {  
    PlacementRuleChecker checker = new PlacementRuleChecker();
    V1MapFactory factory = new V1MapFactory();                                         
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unitFactory = new V1UnitFactory();
    List<Unit> allocatedUnits = unitFactory.createUnitsByType("Chopper", new Color("Red"), 2);
    List<Unit> remainingUnits = unitFactory.createUnitsByType("Chopper", new Color("Red"), 2);
    Message mess1 = new Message("placement", new Color("Red"), "Astapor", "", allocatedUnits);  
    String res1 = checker.checkMyRule(mess1, map, remainingUnits);
    assertEquals("The territory does not belong to you. Please re-enter.", res1);

    List<Unit> newAllocatedUnits = unitFactory.createUnitsByType("Chopper", new Color("Red"), 3);
    Message mess2 = new Message("placement", new Color("Red"), "Asshai", "", newAllocatedUnits);  
    String res2 = checker.checkMyRule(mess2, map, remainingUnits);
    assertEquals("Invalid placement: Allocated Units are not available!", res2);
  }
}

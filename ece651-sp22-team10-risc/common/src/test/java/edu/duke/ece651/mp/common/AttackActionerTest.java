package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

public class AttackActionerTest {
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
  public void test_move_actioner() {
    AttackActioner myActioner = new AttackActioner();
    V1MapFactory myFactory = new V1MapFactory();
    RiscMap myMap = myFactory.createThreePlayerMap();
    Territory redTerr = myMap.getTerritoryByName("Asshai");
    Territory blueTerr = myMap.getTerritoryByName("Norvos");
    Territory greenTerr = myMap.getTerritoryByName("Tarth");
    Color red = new Color("Red");
    Color blue = new Color("Blue");
    Color green = new Color("Green");
    List<Unit> redUnits = addNBasicUnitToTerritory(redTerr, red, 6);
    List<Unit> blueUnits = addNBasicUnitToTerritory(blueTerr, blue, 6);
    List<Unit> greenUnits = addNBasicUnitToTerritory(greenTerr, green, 6);
    Message order1 = new Message ("attack", red, redTerr.getName(), blueTerr.getName(), redUnits.subList(0, 4));
    Message order2 = new Message ("attack", blue, blueTerr.getName(), redTerr.getName(), blueUnits.subList(0, 2));
    Message order3 = new Message ("attack", green, greenTerr.getName(), redTerr.getName(), greenUnits.subList(0, 2));
    myActioner.performAction(order1, myMap);
    myActioner.performAction(order2, myMap);
    myActioner.performAction(order3, myMap);
    addNBasicUnitToTerritory(null, red, 4);
  }

}

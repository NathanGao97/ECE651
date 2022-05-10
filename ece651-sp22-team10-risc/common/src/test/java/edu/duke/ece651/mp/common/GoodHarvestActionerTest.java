package edu.duke.ece651.mp.common;
import edu.duke.ece651.mp.common.*;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class GoodHarvestActionerTest {
  @Test
  public void test_good_harvest() {
    GoodHarvestActioner myActioner = new GoodHarvestActioner();
    V1MapFactory myFactory = new V1MapFactory();
    AbstractUnitFactory unitFactory = new V1UnitFactory();
    RiscMap myMap = myFactory.createTwoPlayerMap();
    Message order = new Message("goodHarvest", new Color("Blue"));
    List<Color> colorList = myMap.getColorList();
    int foodSum = 0;
    for (Color color : colorList) {
      foodSum += color.getFoodResource();
    }
    assertEquals(200, foodSum);
    myActioner.performAction(order, myMap);
    foodSum = 0;
    for (Color color : colorList) {
      foodSum += color.getFoodResource();
    }
    assertEquals(250, foodSum);
  }

}

package edu.duke.ece651.mp.common;
import edu.duke.ece651.mp.common.*;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InfernoActionerTest {
  @Test
  public void test_inferno() {
    InfernoActioner myActioner = new InfernoActioner();
    V1MapFactory myFactory = new V1MapFactory();
    AbstractUnitFactory unitFactory = new V1UnitFactory();
    RiscMap myMap = myFactory.createTwoPlayerMap();
    Message order = new Message("inferno", new Color("Red"));
    List<Color> colorList = myMap.getColorList();
    for (Color color : colorList) {
      color.upgradeTechLevel();
      color.upgradeTechLevel();
    }
    int foodSum = 0;
    int sanitySum = 0;
    int techLevelSum = 0;
    for (Color color : colorList) {
      foodSum += color.getFoodResource();
      sanitySum += color.getSanityResource();
      techLevelSum += color.getTechLevel();
    }
    assertEquals(200, foodSum);
    assertEquals(100, sanitySum);
    assertEquals(6, techLevelSum);
    for (int i = 0; i < 100; i++) {
      myActioner.performAction(order, myMap);
    }
    foodSum = 0;
    sanitySum = 0;
    techLevelSum = 0;
    for (Color color : colorList) {
      foodSum += color.getFoodResource();
      sanitySum += color.getSanityResource();
      techLevelSum += color.getTechLevel();
    }
    assertEquals(101, foodSum);
    assertEquals(51, sanitySum);
    assertEquals(4, techLevelSum);
  }

}

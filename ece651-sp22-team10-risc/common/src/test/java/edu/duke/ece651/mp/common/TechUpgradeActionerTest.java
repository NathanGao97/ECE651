package edu.duke.ece651.mp.common;
import edu.duke.ece651.mp.common.*;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class TechUpgradeActionerTest {
  @Test
  public void test_tech_upgrade() {
    TechUpgradeActioner myActioner = new TechUpgradeActioner();
    V1MapFactory myFactory = new V1MapFactory();
    RiscMap myMap = myFactory.createTwoPlayerMap();
    Message order = new Message("techUpgrade", new Color("Blue"));
    List<Color> colorList = myMap.getColorList();
    Color myColor = null;
    for (Color color : colorList) {
      if (color.equals(order.getOwner())) {
        myColor = color;
      }
    }
    assertEquals(1, myColor.getTechLevel());
    assertEquals(50, myColor.getSanityResource());
    myActioner.performAction(order, myMap);
    assertEquals(2, myColor.getTechLevel());
    assertEquals(0, myColor.getSanityResource());
  }

}

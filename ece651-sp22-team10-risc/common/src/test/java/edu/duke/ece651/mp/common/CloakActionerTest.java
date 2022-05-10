package edu.duke.ece651.mp.common;
import edu.duke.ece651.mp.common.*;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class CloakActionerTest {
  @Test
  public void test_cloak_territory() {
    CloakActioner myActioner = new CloakActioner();
    V1MapFactory myFactory = new V1MapFactory();
    RiscMap myMap = myFactory.createTwoPlayerMap();
    Message order = new Message("cloak", new Color("Blue"), "Astapor");
    List<Color> colorList = myMap.getColorList();
    Color myColor = null;
    for (Color color : colorList) {
      if (color.equals(order.getOwner())) {
        myColor = color;
      }
    }
    myColor.upgradeTechLevel();
    myColor.upgradeTechLevel();
    myColor.upgradeTechLevel();
    myColor.updateSanityResource(50);
    Territory Astapor = myMap.getTerritoryByName("Astapor");
    assertEquals(0, Astapor.getCloakCount());
    assertEquals(100, myColor.getSanityResource());
    myActioner.performAction(order, myMap);
    assertEquals(3, Astapor.getCloakCount());
    assertEquals(80, myColor.getSanityResource());
  }

}

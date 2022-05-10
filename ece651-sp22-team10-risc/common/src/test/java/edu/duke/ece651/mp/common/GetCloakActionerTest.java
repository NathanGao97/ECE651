package edu.duke.ece651.mp.common;
import edu.duke.ece651.mp.common.*;

import java.util.List;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class GetCloakActionerTest {
  @Test
  public void test_research_cloak() {
    GetCloakActioner myActioner = new GetCloakActioner();
    V1MapFactory myFactory = new V1MapFactory();
    RiscMap myMap = myFactory.createTwoPlayerMap();
    Message order = new Message("researchCloak", new Color("Blue"));
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
    assertEquals(false, myColor.getCloakStatus());
    assertEquals(100, myColor.getSanityResource());
    myActioner.performAction(order, myMap);
    assertEquals(true, myColor.getCloakStatus());
    assertEquals(0, myColor.getSanityResource());
  }

}

package edu.duke.ece651.mp.common;

import edu.duke.ece651.mp.common.*;

import java.util.List;

/*
 * This class implements from Actioner interface to allow
 * operations on Research cloaking.
 */
public class GetCloakActioner implements Actioner {
  /*
   * This function takes an order list and do operations on
   * research cloaking orders.
   */
  public void performAction(Message researchCloakOrder, RiscMap map) {
    performResearchCloak(researchCloakOrder, map);
  }

  protected void performResearchCloak(Message researchCloakOrder, RiscMap map) {
    List<Color> colorList= map.getColorList();
    Color myColor = null;
    for (Color color : colorList) {
      if (color.equals(researchCloakOrder.getOwner())) {
        myColor = color;
        break;
      }
    }
    myColor.updateSanityResource(-100);
    myColor.updateCloakStatus(true);
  }
}

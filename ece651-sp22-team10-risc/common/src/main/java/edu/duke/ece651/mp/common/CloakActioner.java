package edu.duke.ece651.mp.common;

import edu.duke.ece651.mp.common.*;

import java.util.List;

/*
 * This class implements from Actioner interface to allow
 * operations on cloaking territories.
 */
public class CloakActioner implements Actioner {
  /*
   * This function takes an order list and do operations on
   * cloaking territory orders.
   */
  public void performAction(Message cloakTerritoryOrder, RiscMap map) {
    performCloak(cloakTerritoryOrder, map);
  }

  protected void performCloak(Message cloakTerritoryOrder, RiscMap map) {
    List<Color> colorList= map.getColorList();
    Color myColor = null;
    for (Color color : colorList) {
      if (color.equals(cloakTerritoryOrder.getOwner())) {
        myColor = color;
        break;
      }
    }
    
    myColor.updateSanityResource(-20);
    Territory target = map.getTerritoryByName(cloakTerritoryOrder.getSource());
    target.cloak();
  }
}

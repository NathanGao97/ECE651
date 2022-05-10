package edu.duke.ece651.mp.common;

import java.util.List;

public class FuryActioner implements Actioner {
     /*
     * This function takes an order list and do operations on
     * Fury orders.
     */
  public void performAction(Message infernoOrder, RiscMap map) {
     performFury(infernoOrder, map);
  }

  public void performFury(Message infernoOrder, RiscMap map) {
    List<Territory> territory_list = map.getTerritoryList();
    Color myColor = infernoOrder.getOwner();
    
    for (Territory territory : territory_list) {
      if (territory.getOwner().equals(myColor)) {
        for (Unit unit : territory.allyUnits) {
          unit.addBonus();
        }
      }
    }
  }  
}

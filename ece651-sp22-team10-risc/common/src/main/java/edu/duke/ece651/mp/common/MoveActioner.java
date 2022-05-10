package edu.duke.ece651.mp.common;

import edu.duke.ece651.mp.common.*;

import java.util.List;

public class MoveActioner implements Actioner {
    /*
     * This function takes an order list and do operations on
     * move orders.
     */
    public void performAction(Message moveOrder, RiscMap map) {
        performMove(moveOrder, map);
    }

  protected void performMove(Message moveOrder, RiscMap map) {
        Territory src = map.getTerritoryByName(moveOrder.getSource());
        Territory dest = map.getTerritoryByName(moveOrder.getDest());
        List<Unit> movingUnits = moveOrder.getAllocatedUnits();
        PathFinder finder = new PathFinder();
        int cost = finder.getMinimalCost(map, src, dest, movingUnits.size());
        System.out.println("Executing " + moveOrder + " costs " + cost);
        for (Color color : map.getColorList()) {
          if (color.equals(moveOrder.getOwner()) && !moveOrder.getAllocatedUnits().get(0).getType().equals("Spy")) {
            color.updateFoodResource(-cost);
            break;
          }
          
        }
        for(Unit unit : movingUnits) {
            src.removeUnit(unit);
            dest.addUnit(unit);
        }
    }
}

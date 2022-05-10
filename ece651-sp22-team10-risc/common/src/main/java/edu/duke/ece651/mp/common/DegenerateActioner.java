package edu.duke.ece651.mp.common;

import edu.duke.ece651.mp.common.*;

import java.util.List;
import java.util.Random;

public class DegenerateActioner implements Actioner {
  /*
     * This function takes an order list and do operations on
     * degenerate orders.
     */
    public void performAction(Message degenerateOrder, RiscMap map) {
        performDegenerate(degenerateOrder, map);
    }

    public void performDegenerate(Message degenerateOrder, RiscMap map) {
      List<Color> colorList = map.getColorList();
      int size = colorList.size();
      Color target = null;
      Random r = new Random();
      while (true) {
        int res = r.nextInt(size);
        target = colorList.get(res);
        if (target.equals(degenerateOrder.getOwner())) {
          continue;
        }
        else {
          break;
        }
      }
      List<Territory> territoryList = map.getTerritoryListOwnedByColor(target);
      AbstractUnitFactory unitFactory = new V1UnitFactory();
      for (Territory territory : territoryList) {
        int unitNum = territory.allyUnits.size();
        territory.allyUnits.clear();
        List<Unit> choppers = unitFactory.createChopperUnit(target, unitNum);
        for (Unit chopper : choppers) {
          territory.addUnit(chopper);
        }
      }
    }
}

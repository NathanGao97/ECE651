package edu.duke.ece651.mp.common;

import edu.duke.ece651.mp.common.*;

import java.util.List;
import java.util.Random;

public class ColossusActioner implements Actioner {
    /*
     * This function takes an order list and do operations on
     * degenerate orders.
     */
    public void performAction(Message colossusOrder, RiscMap map) {
        performColossus(colossusOrder, map);
    }

    public void performColossus(Message colossusOrder, RiscMap map) {
      List<Territory> territories = map.getTerritoryListOwnedByColor(colossusOrder.getOwner());
      int size = territories.size();
      Random r = new Random();
      int res = r.nextInt(size);
      Territory target = territories.get(res);
      AbstractUnitFactory unitFactory = new V1UnitFactory();
      List<Unit> kaidou = unitFactory.createUnitsByType("Kaidou", colossusOrder.getOwner(), 1);
      target.addUnit(kaidou.get(0));
    }
}

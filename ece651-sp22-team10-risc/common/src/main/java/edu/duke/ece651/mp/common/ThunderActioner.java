package edu.duke.ece651.mp.common;
import java.util.List;
import java.util.Random;

public class ThunderActioner implements Actioner {
    /*
     * This function takes an order list and do operations on
     * good harvest orders.
     */
  public void performAction(Message thunderOrder, RiscMap map) {
    performThunder(thunderOrder, map);
  }

  public void performThunder(Message thunderOrder, RiscMap map) {
    List<Territory> territory_list = map.getTerritoryList();
    int size = territory_list.size();
    Random r = new Random();
    Territory target = null;
    
    while (true) {
        int res = r.nextInt(size);
        target = territory_list.get(res);
        if (target.getOwner().equals(thunderOrder.getOwner())) {
          continue;
        }
        else {
          break;
        }
      }
    
    target.removeAllUnits();
  }
}

package edu.duke.ece651.mp.common;

import edu.duke.ece651.mp.common.*;

import java.util.List;
import java.util.Random;

public class InfernoActioner implements Actioner {
  /*
     * This function takes an order list and do operations on
     * inferno orders.
     */
    public void performAction(Message infernoOrder, RiscMap map) {
        performInferno(infernoOrder, map);
    }

    public void performInferno(Message infernoOrder, RiscMap map) {
      List<Color> colorList = map.getColorList();
      int size = colorList.size();
      Color target = null;
      Random r = new Random();
      while (true) {
        int res = r.nextInt(size);
        target = colorList.get(res);
        if (target.equals(infernoOrder.getOwner())) {
          continue;
        }
        else {
          break;
        }
      }
      target.destroyTechLevel();
      int foodResource = target.getFoodResource();
      target.updateFoodResource(-foodResource/2);
      int sanityResource = target.getSanityResource();
      target.updateSanityResource(-sanityResource/2);
    }
}

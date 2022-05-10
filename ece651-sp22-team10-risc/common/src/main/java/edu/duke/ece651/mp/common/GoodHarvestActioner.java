package edu.duke.ece651.mp.common;

import edu.duke.ece651.mp.common.*;

import java.util.List;
import java.util.Random;

public class GoodHarvestActioner implements Actioner {
  /*
     * This function takes an order list and do operations on
     * good harvest orders.
     */
    public void performAction(Message harvestOrder, RiscMap map) {
        performGoodHarvest(harvestOrder, map);
    }

    public void performGoodHarvest(Message harvestOrder, RiscMap map) {
      List<Color> colorList = map.getColorList();
      Color target = null;
      for (Color color: colorList) {
        if (color.equals(harvestOrder.getOwner())) {
          target = color;
          break;
        }
      }
      target.updateFoodResource(50);
    }
}

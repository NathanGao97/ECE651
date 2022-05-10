package edu.duke.ece651.mp.common;

import edu.duke.ece651.mp.common.*;

import java.util.List;

public class PandemicActioner implements Actioner {
    /*
     * This function takes an order list and do operations on
     * pandemic orders.
     */
    public void performAction(Message pandemicOrder, RiscMap map) {
        performPandemic(pandemicOrder, map);
    }

    public void performPandemic(Message pandemicOrder, RiscMap map) {
      List<Territory> territories = map.getTerritoryList();
      for (Territory territory : territories) {
        List<Unit> allyUnits = territory.allyUnits;
        for (int i = allyUnits.size() - 1; i >= 0; i--) {
          if(allyUnits.get(i).getType().equals("Nami")) {
            allyUnits.remove(allyUnits.get(i));
          }
        }
        for (Color color : territory.enemyUnits.keySet()) {
          List<Unit> enemyUnits = territory.enemyUnits.get(color);
          for (int i = enemyUnits.size() - 1; i >= 0; i--) {
            if(enemyUnits.get(i).getType().equals("Nami")) {
              enemyUnits.remove(enemyUnits.get(i));
            }
          }
        }
      }
    }
}

package edu.duke.ece651.mp.server;

import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import edu.duke.ece651.mp.common.*;

public class UnitsRuleChecker extends OrderRuleChecker{
  public UnitsRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  /*
   * This function checks if the input unit in Message is in the range
   * of the units owned by current player.
   */
  public String checkMyRule(RiscGame myGame, Message message) {
    if (!message.getAllocatedUnits().get(0).getType().equals("Spy")) {
      Territory src = myGame.gameMap.getTerritoryByName(message.getSource());
      List<Unit> temp = new ArrayList<>(src.allyUnits);
      for (Unit allocatedUnit : message.getAllocatedUnits()) {
        if (!temp.remove(allocatedUnit)) {
          return "Invalid Operation: Allocated Units are not available in Source Territory!";
        }
      }
      return null;
    }
    else {
      if (message.getType().equals("attack")) {
        return "Invalid Operation: A Spy could not be allocated for attack!";
      }
      else {
        Territory src = myGame.gameMap.getTerritoryByName(message.getSource());
        List<Unit> spyUnits = new ArrayList<>(src.spyUnits);
        for (Unit spy : message.getAllocatedUnits()) {
          if (!spyUnits.remove(spy)) {
            return "Invalid Operation: Allocated Units are not available in Source Territory!";
          }
        }
        return null;
      }
    }
  }
}

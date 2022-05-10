package edu.duke.ece651.mp.server;

import java.util.HashMap;
import java.util.List;
import edu.duke.ece651.mp.common.*;

public class DestinationTerritoryMoveRuleChecker extends OrderRuleChecker{
  public DestinationTerritoryMoveRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  /* This function checks if the destination in Message during moving phase
   * belongs to the current player's territory list.
   */
  public String checkMyRule(RiscGame myGame, Message message) {
    if (!message.getAllocatedUnits().get(0).getType().equals("Spy")) {
      List<Territory>territoryList = myGame.gameMap.getTerritoryList();
      for (Territory territory : territoryList) {
        if (territory.getName().equals(message.getDest())) {
          if (territory.getOwner().equals(message.getOwner())) {
            return null;
          } 
        }
        else {
          continue;
        }
      }
      return "Invalid Move: The Destination Territory does not belong to you!";
    }
    return null;
  }
}


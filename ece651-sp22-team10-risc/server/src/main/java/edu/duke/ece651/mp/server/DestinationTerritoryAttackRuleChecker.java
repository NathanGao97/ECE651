package edu.duke.ece651.mp.server;

import java.util.HashMap;
import java.util.List;
import edu.duke.ece651.mp.common.*;

public class DestinationTerritoryAttackRuleChecker extends OrderRuleChecker{
  public DestinationTerritoryAttackRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  /*
   * This function checks if the destination territory in Message during
   * attacking phase belongs to enemies' territory lists.
   */
  public String checkMyRule(RiscGame myGame, Message message) {
    List<Territory>territoryList = myGame.gameMap.getTerritoryList();
    for (Territory territory : territoryList) {
      if (territory.getName().equals(message.getDest())) {
        if (!territory.getOwner().equals(message.getOwner())) {
          if (message.getAllocatedUnits().size() > message.getOwner().getFoodResource()) {
            return "Invalid Attack: no enough food resource!";
          }
          return null;
        }
      }  
      else {
        continue;
      }
    }
    return "Invalid Attack: The Destination Territory is not a target";
  }
}

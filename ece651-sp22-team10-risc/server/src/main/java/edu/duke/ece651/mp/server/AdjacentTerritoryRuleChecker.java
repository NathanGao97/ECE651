package edu.duke.ece651.mp.server;

import java.util.HashMap;
import java.util.List;
import edu.duke.ece651.mp.common.*;

public class AdjacentTerritoryRuleChecker extends OrderRuleChecker {
  public AdjacentTerritoryRuleChecker(OrderRuleChecker next) {
    super(next);
  }
  @Override
  /*
   * This function checks if the destination enemy territory is in the 
   * neighbour list of owned territories in the attacking phase.
   */
  public String checkMyRule(RiscGame myGame, Message message) {
    List<Territory> territoryList = myGame.gameMap.getTerritoryList();
    for (Territory territory : territoryList) {
      if (territory.getName().equals(message.getSource())) {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (Territory neighbour : neighbourList) {
          if (neighbour.getName().equals(message.getDest())) {
            return null;
          }
        }
      }
    }
    return "Invalid Attack: cannot reach Destination Territory!";
  }
}

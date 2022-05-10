package edu.duke.ece651.mp.server;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import edu.duke.ece651.mp.common.*;

public class ValidPathRuleChecker extends OrderRuleChecker{
  public ValidPathRuleChecker(OrderRuleChecker next) {
    super(next);
  }

  /*
   * This is a helper for path check
   */
  public String checkPath(RiscGame myGame, Message message) {
    String source = message.getSource();
    String dest = message.getDest();
    List<Territory>territoryList = myGame.gameMap.getTerritoryList();
    HashMap<String, Boolean> territoryVisitedStatus = new HashMap<>();
    for (Territory territory : territoryList) {
      territoryVisitedStatus.put(territory.getName(), false);
    }
    
    for (Territory territory : territoryList) {
      if (territory.getName().equals(source)) {
        List<Territory> queue = new ArrayList<>();
        territoryVisitedStatus.put(territory.getName(), true);
        queue.add(territory);
        while (!queue.isEmpty()) {
          Territory curTerritory = queue.get(0);
          queue.remove(0);
          for (Territory neighbour_t : curTerritory.getNeighbourTerritories()) {
            if (neighbour_t.getName().equals(dest)) {
              PathFinder finder = new PathFinder();
              Territory src = myGame.gameMap.getTerritoryByName(source);
              Territory destination = myGame.gameMap.getTerritoryByName(dest);
              int cost = finder.getMinimalCost(myGame.gameMap, src, destination, message.getAllocatedUnits().size());
              if (cost > message.getOwner().getFoodResource()) {
                return "Invalid Move: no enough food resource!";
              }
              return null;
            }
            if (neighbour_t.getOwner().equals(message.getOwner()) && !territoryVisitedStatus.get(neighbour_t.getName())) {
              territoryVisitedStatus.put(neighbour_t.getName(), true);
              queue.add(neighbour_t);
            }
          }
        }
      }
   }

    return "Invalid Move: Cannot reach Destination Territory from Source Territory!";
  }

  /*
   * This function checks if the path from source to destination is valid.
   */
  public String checkMyRule(RiscGame myGame, Message message) {
    if (!message.getAllocatedUnits().get(0).getType().equals("Spy")) {
      return checkPath(myGame, message);
    }
    else {
      String source = message.getSource();
      String destination = message.getDest();
      Territory src = myGame.gameMap.getTerritoryByName(source);
      Territory dest  = myGame.gameMap.getTerritoryByName(destination);
      Color owner = message.getOwner();
      if (!src.getOwner().equals(owner)) {  // source territory is an enemy territory
        List<Territory> neighbours = src.getNeighbourTerritories();
        for (Territory neighbour : neighbours) {
          if (neighbour.getName().equals(destination)) {
            return null;
          }
        }
        return "Invalid Move: Spy in enemy territory can only move 1 territory at a time!";
      }
      else {  // source territory is an owned territory
        if (dest.getOwner().equals(owner)) {  // destination territory is an owned territory
          return checkPath(myGame, message);
        }
        else {  // destination territory is an enemy territory
          List<Territory> neighbours = dest.getNeighbourTerritories();
          for (Territory neighbour : neighbours) {
            if (neighbour.getOwner().equals(message.getOwner())) {
              Message tmp = new Message(message.getType(), message.getOwner(), source, neighbour.getName(), message.getAllocatedUnits());
              if (checkPath(myGame, tmp) == null) {
                return null;
              }
            }
          }
          return "Invalid Move: Spy can not reach destination territory in one turn!";
        }
      }
    }
  }
  
}

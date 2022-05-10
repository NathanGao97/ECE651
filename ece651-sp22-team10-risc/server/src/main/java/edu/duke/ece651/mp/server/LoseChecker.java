package edu.duke.ece651.mp.server;
import edu.duke.ece651.mp.common.*;
import java.util.List;

/*
 * This class implements from ResultChecker to check if 
 * the current player loses the game.
 */
public class LoseChecker implements ResultChecker{
  public boolean resultCheck(RiscMap myMap, Color myColor) {
    List<Territory> territoryList = myMap.getTerritoryList();
    int count = 0;

    for (Territory territory : territoryList) {
      if (territory.getOwner().equals(myColor)) {
        count++;
      }
    }

    if (count == 0) {
      return true;
    }
    return false;
  }
}

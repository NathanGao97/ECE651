package edu.duke.ece651.mp.server;
import edu.duke.ece651.mp.common.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class PlacementRuleChecker {
    /*  
        This function is mainly used for the units assignment phase
        This function will help to check whether the units in the mess can be placed
        in the terr including ownership and remaining units
        @param message: the player will send message object to us
        @param map: the map on the server which will be used to check ownership
        @param remainingUnits: how many remaining units left in the territory
        return null if the phacement is valid
            else return error message
    */
  public String checkMyRule(Message message, RiscMap map, List<Unit> remainingUnits){
    List<Unit> assignment = message.getAllocatedUnits();
    Territory src = map.getTerritoryByName(message.getSource());
    if (src == null || !src.getOwner().equals(message.getOwner())) {
      return "The territory does not belong to you. Please re-enter.";
    }
    List<Unit> temp = new ArrayList<>(remainingUnits);
    for (Unit allocatedUnit : message.getAllocatedUnits()) {
      if (!temp.remove(allocatedUnit)) {
        return "Invalid placement: Allocated Units are not available!";
      }
    }
    return null;        
  }
}

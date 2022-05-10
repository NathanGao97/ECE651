package edu.duke.ece651.mp.server;

import java.util.HashMap;
import java.util.List;
import edu.duke.ece651.mp.common.*;


public class UpgradeUnitChecker extends OrderRuleChecker {
  public UpgradeUnitChecker(OrderRuleChecker next) {
    super(next);
  }

  /*
   * This function checks if the chosen units of the player are able to upgrade.
   */
  public String checkMyRule(RiscGame myGame, Message message) { 
    String source = message.getSource();
    Territory sourceTerritory = myGame.gameMap.getTerritoryByName(source);
    if (!myGame.gameMap.getTerritoryByName(source).getOwner().equals(message.getOwner())) {
      return "Invalid Unit Upgrade: source territory does not belong to you!";
    }

    if (message.getAllocatedUnits().size() == 0) {
      return "Invalid Unit Upgrade: no units allocated!";
    }

    String currentUnit = message.getAllocatedUnits().get(0).getType();
    String targetUnit = message.getTarget();

    if (currentUnit.equals("Spy")) {
      return "Invalid Unit Upgrade: Spy could not be upgraded!";
    }
    if (currentUnit.equals("Kaidou")) {
      return "Invalid Unit Upgrade: Kaidou could not be upgraded!";
    }
    if (targetUnit.equals("Kaidou")) {
      return "Invalid Unit Upgrade: Kaidou could not be upgraded!";
    }
    
    if(message.getAllocatedUnits().size() > sourceTerritory.getAllyUnitSizeByType(currentUnit)) {
      return "Invalid Unit Upgrade: can't allocate enough units in the source territory!";
    }
    
    Color color = message.getOwner();
    Player player = myGame.getPlayerByColor(color);
   
    
    int technologyLevel = player.showIdentity().getTechLevel();
    int requiredLevel = myGame.gameMap.unitTechRelationTable.get(targetUnit);

    if (technologyLevel < requiredLevel) {
      return "Invalid Unit Upgrade: technology level does not meet the requirement for upgrade!";
    }
    
    int currentCost = myGame.gameMap.unitLevelUpTable.get(currentUnit);
    int targetCost = myGame.gameMap.unitLevelUpTable.get(targetUnit);
    int num = message.getAllocatedUnits().size();
    int sanity_resource = player.showIdentity().getSanityResource();

    int cost = (targetCost - currentCost) * num;
    
    if (sanity_resource < cost) {
      return "Invalid Unit Upgrade: no enough technology resource!";
    }
    return null;
  } 
}

package edu.duke.ece651.mp.server;

import java.util.HashMap;
import java.util.List;
import edu.duke.ece651.mp.common.*;

public class UpgradeTechLevelChecker extends OrderRuleChecker {
  public UpgradeTechLevelChecker(OrderRuleChecker next) {
    super(next);
  }

  /*
   * This function checks if the technology level of the current player is able to upgrade.
   */
  public String checkMyRule(RiscGame myGame, Message message) { 
    Color color = message.getOwner();
    Player player = myGame.getPlayerByColor(color);
    int technology_level = player.showIdentity().getTechLevel();
    if (technology_level > 5) {
      return "Invalid Technology Level Upgrade: already the heightest level!";
    }
    int sanity_resource = player.showIdentity().getSanityResource();
    int cost = myGame.gameMap.technologyLevelUpTable.get(technology_level);
    if (sanity_resource < cost) {
      return "Invalid Technology Level Upgrade: no enough technology resource!";
    }
    return null;
  }
}

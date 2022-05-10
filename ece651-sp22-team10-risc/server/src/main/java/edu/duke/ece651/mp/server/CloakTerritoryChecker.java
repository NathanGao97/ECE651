package edu.duke.ece651.mp.server;

import java.util.HashMap;
import java.util.List;
import edu.duke.ece651.mp.common.*;

public class CloakTerritoryChecker extends OrderRuleChecker {
  public CloakTerritoryChecker(OrderRuleChecker next) {
    super(next);
  }

  /*
   * This function checks if the territory could be cloaked.
   */
  public String checkMyRule(RiscGame myGame, Message message) {
    Color color = message.getOwner();
    Player player = myGame.getPlayerByColor(color);
    String source = message.getSource();
    Territory src = myGame.gameMap.getTerritoryByName(source);
    if (!src.getOwner().equals(color)) {
      return "Invalid Cloaking: the territory does not belong to you!";
    }
    if (!player.showIdentity().getCloakStatus()) {
      return "Invalid Cloaking: you do not have the ability of cloaking!";
    }
    int sanity_resource = player.showIdentity().getSanityResource();
    if (sanity_resource < 20) {
      return "Invalid Cloaking: no enough technology resource!";
    }
    return null;
  }
  
}

package edu.duke.ece651.mp.server;

import java.util.HashMap;
import java.util.List;
import edu.duke.ece651.mp.common.*;

public class GetCloakChecker extends OrderRuleChecker {
  public GetCloakChecker(OrderRuleChecker next) {
    super(next);
  }

  /*
   * This function checks if the palyer could research cloaking.
   */
  public String checkMyRule(RiscGame myGame, Message message) {
    Color color = message.getOwner();
    Player player = myGame.getPlayerByColor(color);
    int technology_level = player.showIdentity().getTechLevel();
    if (technology_level < 3) {
      return "Invalid Research: current technology level is not enough for researching cloaking!";
    }
    int sanity_resource = player.showIdentity().getSanityResource();
    if (sanity_resource < 100) {
      return "Invalid Research: no enough technology resource!";
    }
    return null;
  }

}

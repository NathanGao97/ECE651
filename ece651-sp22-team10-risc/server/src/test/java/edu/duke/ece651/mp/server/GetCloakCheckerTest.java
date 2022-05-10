package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import edu.duke.ece651.mp.common.*;

public class GetCloakCheckerTest {
  @Test
  public void test_get_cloak() {
    V1MapFactory factory = new V1MapFactory();                                        
    RiscMap map = factory.createTwoPlayerMap();
    AbstractUnitFactory unit_factory = new V1UnitFactory();
    LoseChecker lose_checker = new LoseChecker();
    PlacementRuleChecker place_checker = new PlacementRuleChecker();
    RiscGame myGame = new RiscGame(map, 2, unit_factory, lose_checker, place_checker);
    Player player1 = new Player(myGame, new Color("Red"), null, null);
    Player player2 = new Player(myGame, new Color("Blue"), null, null);
    myGame.players.add(player1);
    myGame.players.add(player2);
    OrderRuleChecker checker = new GetCloakChecker(null);
    Message message1 = new Message("researchCloak", new Color("Red"));
    String res1 = checker.checkMyRule(myGame, message1);
    assertEquals("Invalid Research: current technology level is not enough for researching cloaking!", res1);
    player1.showIdentity().upgradeTechLevel();
    player1.showIdentity().upgradeTechLevel();
    player1.showIdentity().upgradeTechLevel();
    String res2 = checker.checkMyRule(myGame, message1);
    assertEquals("Invalid Research: no enough technology resource!", res2);
    player1.showIdentity().updateSanityResource(100);
    String res3 = checker.checkMyRule(myGame, message1);
    assertEquals(null,res3);
  }

}

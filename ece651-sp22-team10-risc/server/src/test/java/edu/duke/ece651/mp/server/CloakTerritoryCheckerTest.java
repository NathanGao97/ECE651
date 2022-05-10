package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import edu.duke.ece651.mp.common.*;

public class CloakTerritoryCheckerTest {
  @Test
  public void test_cloak_territory() {
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
    OrderRuleChecker checker = new CloakTerritoryChecker(null);
    Message message1 = new Message("cloak", new Color("Red"), "Astapor");
    String res1 = checker.checkMyRule(myGame, message1);
    assertEquals("Invalid Cloaking: the territory does not belong to you!", res1);
    Message message2 = new Message("cloak", new Color("Red"), "Asshai");
    String res2 = checker.checkMyRule(myGame, message2);
    assertEquals("Invalid Cloaking: you do not have the ability of cloaking!", res2);
    player1.showIdentity().updateCloakStatus(true);
    player1.showIdentity().updateSanityResource(-50);
    String res3 = checker.checkMyRule(myGame, message2);
    assertEquals("Invalid Cloaking: no enough technology resource!", res3);
    player1.showIdentity().updateSanityResource(20);
    String res4 = checker.checkMyRule(myGame, message2);
    assertEquals(null, res4);
  }

}

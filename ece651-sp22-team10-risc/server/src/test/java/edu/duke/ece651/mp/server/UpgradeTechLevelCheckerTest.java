package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import edu.duke.ece651.mp.common.*;

public class UpgradeTechLevelCheckerTest {
  @Test
  public void test_technology_level_up() {
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
    player2.showIdentity().updateSanityResource(-10);
    OrderRuleChecker checker = new UpgradeTechLevelChecker(null);
    Message message1 = new Message("techUpgrade", new Color("Red"));
    Message message2 = new Message("techUpgrade", new Color("Blue"));
    String res1 = checker.checkMyRule(myGame, message1);
    String res2 = checker.checkMyRule(myGame, message2);
    assertEquals(null, res1);
    assertEquals("Invalid Technology Level Upgrade: no enough technology resource!", res2);
    player2.showIdentity().upgradeTechLevel();
    player2.showIdentity().upgradeTechLevel();
    player2.showIdentity().upgradeTechLevel();
    player2.showIdentity().upgradeTechLevel();
    player2.showIdentity().upgradeTechLevel();
    String res3 = checker.checkMyRule(myGame, message2);
    assertEquals("Invalid Technology Level Upgrade: already the heightest level!", res3);
  }
}

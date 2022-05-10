package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import edu.duke.ece651.mp.common.*;

import java.util.List;
import java.util.ArrayList;

public class UpgradeUnitCheckerTest {
  @Test
  public void test_unit_up() {
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
    Unit Chopper = unit_factory.createChopperUnit(new Color("Red"));
    Unit Spy = unit_factory.createSpyUnit(new Color("Red"));
    Territory Asshai = myGame.gameMap.getTerritoryByName("Asshai");
    Asshai.addUnit(Chopper);
    Asshai.addUnit(Chopper);
    Asshai.addUnit(Spy);
    List<Unit> emptyList = new ArrayList<> ();
    List<Unit> allocatedUnits = unit_factory.createChopperUnit(new Color("Red"), 2);
    OrderRuleChecker checker = new UpgradeUnitChecker(null);
    Message message1 = new Message("unitUpgrade", new Color("Red"), "Asshai", allocatedUnits, "Nami");
    Message message2 = new Message("unitUpgrade", new Color("Red"), "Asshai", allocatedUnits, "Zoro");
    Message message3 = new Message("unitUpgrade", new Color("Red"), "Asshai", emptyList, "Nami");
    String res1 = checker.checkMyRule(myGame, message1);
    String res2 = checker.checkMyRule(myGame, message2);
    String res3 = checker.checkMyRule(myGame, message3);
    assertEquals(null, res1);
    assertEquals("Invalid Unit Upgrade: technology level does not meet the requirement for upgrade!", res2);
    assertEquals("Invalid Unit Upgrade: no units allocated!", res3);
    player1.showIdentity().upgradeTechLevel();
    player1.showIdentity().upgradeTechLevel();
    player1.showIdentity().upgradeTechLevel();
    player1.showIdentity().upgradeTechLevel();
    String res4 = checker.checkMyRule(myGame, message2);
    assertEquals("Invalid Unit Upgrade: no enough technology resource!", res4);
    Message message5 = new Message("unitUpgrade", new Color("Red"), "Astapor", allocatedUnits, "Nami");
    String res5 = checker.checkMyRule(myGame, message5);
    assertEquals("Invalid Unit Upgrade: source territory does not belong to you!", res5);
    allocatedUnits.add(Chopper);
    Message message6 = new Message("unitUpgrade", new Color("Red"), "Asshai", allocatedUnits, "Nami");
    String res6 = checker.checkMyRule(myGame, message6);
    assertEquals("Invalid Unit Upgrade: can't allocate enough units in the source territory!", res6);
    List<Unit> spyUnits = unit_factory.createSpyUnit(new Color("Red"), 1);
    Message message7 = new Message("unitUpgrade", new Color("Red"), "Asshai", spyUnits, "Usopp");
    String res7 = checker.checkMyRule(myGame, message7);
    assertEquals("Invalid Unit Upgrade: Spy could not be upgraded!", res7);
  }

}

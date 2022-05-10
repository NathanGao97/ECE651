package edu.duke.zg79.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class NoCollisionRuleCheckerTest {

  /*
   * Test the validity of our rule to check ship placement with no collision
   */
  @Test
  public void test_no_collision_rule_checker() {
    V1ShipFactory factory = new V1ShipFactory();
    Placement p = new Placement(new Coordinate(1, 2),'V');

    Ship<Character> s = factory.makeSubmarine(p);
    Ship<Character> d = factory.makeDestroyer(p);

    NoCollisionRuleChecker<Character> checker = new NoCollisionRuleChecker<>(null);
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(4, 4, checker, 'X'); 

    assertEquals(null, checker.checkMyRule(s, board));
    board.tryAddShip(s);
    assertEquals("That placement is invalid: the ship overlaps another ship.", checker.checkMyRule(d, board));
  }

  /*
   * Test the validity of our rule to check ship placement with no collision
   */
  @Test
  public void test_in_bound_and_no_collision_rule_checker() {
    V1ShipFactory factory = new V1ShipFactory();
    Placement p = new Placement(new Coordinate(1, 2),'V');
    Ship<Character> s = factory.makeSubmarine(p);
    Ship<Character> d = factory.makeDestroyer(p);
    
    InBoundsRuleChecker<Character> checker_bound = new InBoundsRuleChecker<>(null);
    NoCollisionRuleChecker<Character> checker_collision = new NoCollisionRuleChecker<>(null);

    BattleShipBoard<Character> board_bound = new BattleShipBoard<Character>(4, 4, checker_bound, 'X');
    BattleShipBoard<Character> board_collision = new BattleShipBoard<Character>(4, 4, checker_collision, 'X');
    assertEquals(null, checker_bound.checkMyRule(s, board_bound));
    assertEquals(null, checker_collision.checkMyRule(s, board_collision));
    board_collision.tryAddShip(s);
    assertEquals("That placement is invalid: the ship overlaps another ship.", checker_collision.checkMyRule(d, board_collision));
    assertEquals(null, checker_bound.checkMyRule(d, board_bound));
  }
  
}

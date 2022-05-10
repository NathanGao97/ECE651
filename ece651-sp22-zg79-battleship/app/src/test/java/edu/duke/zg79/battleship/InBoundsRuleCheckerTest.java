package edu.duke.zg79.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class InBoundsRuleCheckerTest {

  /*
   * Test the validity of our rule to check ship placement in bound
   */
  @Test
  public void test_in_bound_rule_checker() {
    V1ShipFactory factory = new V1ShipFactory();

    Placement p_v = new Placement(new Coordinate(1, 2),'V');
    Placement p_h = new Placement(new Coordinate(1, 2),'H');
    Placement p_u = new Placement(new Coordinate(-1, 2),'H');
    Placement p_l = new Placement(new Coordinate(1, -2),'H');

    Ship<Character> c_v = factory.makeCarrier(p_v);
    Ship<Character> c_h = factory.makeCarrier(p_h);
    Ship<Character> c_u = factory.makeCarrier(p_u);
    Ship<Character> c_l = factory.makeCarrier(p_l);
    Ship<Character> d_v = factory.makeDestroyer(p_v);

    InBoundsRuleChecker<Character> checker = new InBoundsRuleChecker<>(null);
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(4, 4, checker, 'X'); 

    assertEquals("That placement is invalid: the ship goes off the bottom of the board.", checker.checkMyRule(c_v, board));
    assertEquals("That placement is invalid: the ship goes off the right of the board.", checker.checkMyRule(c_h, board));
    assertEquals("That placement is invalid: the ship goes off the top of the board.", checker.checkMyRule(c_u, board));
    assertEquals("That placement is invalid: the ship goes off the left of the board.", checker.checkMyRule(c_l, board));
    assertEquals(null, checker.checkMyRule(d_v, board));
    assertEquals("That placement is invalid: the ship goes off the bottom of the board.", checker.checkPlacement(c_v, board));
    assertEquals(null, checker.checkPlacement(d_v, board));
  }

}

package edu.duke.zg79.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SimpleCompletionRulesTest {

  /**
   * Test whether the lost could be correctly detected
   * when all the ships on the board are sunked
   */ 
  @Test
  public void test_check_lose() {
    Board<Character> board = new BattleShipBoard<Character>(4, 4, 'X');
    Placement p = new Placement(new Coordinate(0, 1), 'V');
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> s = factory.makeSubmarine(p);
    board.tryAddShip(s);

    SimpleCompletionRules<Character> status = new SimpleCompletionRules<Character>(board);
    assertEquals(false, status.checkCompletion());
    board.fireAt(new Coordinate(0, 1));
    assertEquals(false, status.checkCompletion());
    board.fireAt(new Coordinate(1, 1));
    assertEquals(true, status.checkCompletion());
  }

}

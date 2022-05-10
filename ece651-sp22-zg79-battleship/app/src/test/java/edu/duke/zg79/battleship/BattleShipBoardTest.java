package edu.duke.zg79.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BattleShipBoardTest {

  /**
   * Help to check whether the values of all coordinates in our BattleShipBoard
   * meet our expectation
   */
  private <T> void checkWhatIsAtBoard(BattleShipBoard<T> b, T[][] expected) {
    for (int i = 0; i < 10; i++) {
      for (int j = 0; j < 20; j++) {
        assertEquals(b.whatIsAtForSelf(new Coordinate(i, j)), expected[i][j]);
      }
    }
  }
  
  /**
   * Test whether we can get the correct width and height
   */
@Test
  public void test_width_and_height() {
  Board<Character> b1 = new BattleShipBoard<Character>(10, 20, 'X');
    assertEquals(10, b1.getWidth());
    assertEquals(20, b1.getHeight());
  }

  /**
   * Test whether invalid dimensions could be identified
   */
  @Test
  public void test_invalid_dimensions() {
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, 0, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(0, 20, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(10, -5, 'X'));
    assertThrows(IllegalArgumentException.class, () -> new BattleShipBoard<Character>(-8, 20, 'X'));
  }

  /**
   * Test whether the values of all coordinates in our BattleShipBoard are correct
   * and the function of adding ships to our BattleShipBoard
   */
  @Test
  public void test_add_and_what_is_at() {
    BattleShipBoard<Character> b = new BattleShipBoard<Character>(10, 20, 'X');
    Character[][] emptyBoard = new Character[10][20];
    checkWhatIsAtBoard(b, emptyBoard);    
  }

  /**
   * Test whether the placement rules are correctly checked 
   * when add a new ship to te board
   */
  @Test
  public void test_placement_checker() {
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(4, 4, 'X');
    V1ShipFactory factory = new V1ShipFactory();
    Placement p1 = new Placement(new Coordinate(1, 2), 'V');
    Ship<Character> c = factory.makeCarrier(p1);
    assertEquals("That placement is invalid: the ship goes off the bottom of the board.", board.tryAddShip(c));
    Placement p2 = new Placement(new Coordinate(1, 2), 'H');
    Ship<Character> s = factory.makeSubmarine(p2);
    assertEquals(null, board.tryAddShip(s));
  }
  
  /**
   * Test whether the hit and miss are both correctly recorded
   */
  @Test
  public void test_fire_at() {
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(4, 4, 'X');
    Placement p = new Placement(new Coordinate(0, 1), 'V');
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> d = factory.makeDestroyer(p);
    board.tryAddShip(d);

    Coordinate hit1 = new Coordinate(0, 0);
    assertSame(null, board.fireAt(hit1));
    assertEquals(false, d.isSunk());
    
    Coordinate hit2 = new Coordinate(0, 1);
    assertSame(d, board.fireAt(hit2));
    assertEquals(false, d.isSunk());

    Coordinate hit3 = new Coordinate(1, 1);
    assertSame(d, board.fireAt(hit3));
    assertEquals(false, d.isSunk());

    Coordinate hit4 = new Coordinate(2, 1);
    assertSame(d, board.fireAt(hit4));
    assertEquals(true, d.isSunk());
  }

  /**
   * Test whether we correctly set information on the enemy's board
   */
  @Test
  public void test_what_is_at_for_enemy() {
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(4, 4, 'X');
    Placement p = new Placement(new Coordinate(0, 1), 'V');
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> d = factory.makeDestroyer(p);
    board.tryAddShip(d);
    Coordinate miss = new Coordinate(0, 0);
    Coordinate hit = new Coordinate(0, 1);

    assertEquals(null, board.whatIsAtForEnemy(miss));
    assertEquals(null, board.whatIsAtForSelf(miss));
    board.fireAt(miss);
    assertEquals('X', board.whatIsAtForEnemy(miss));
    assertEquals(null, board.whatIsAtForSelf(miss));

    assertEquals(null, board.whatIsAtForEnemy(hit));
    assertEquals('d', board.whatIsAtForSelf(hit));
    board.fireAt(hit);
    assertEquals('d', board.whatIsAtForEnemy(hit));
    assertEquals('*', board.whatIsAtForSelf(hit));
  }

}

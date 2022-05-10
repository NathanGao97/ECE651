package edu.duke.zg79.battleship;

import static edu.duke.zg79.battleship.RectangleShip.makeCoords;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashSet;

import org.junit.jupiter.api.Test;

public class RectangleShipTest {

  /**
   * Test the constructor correctly make the coordinate set
   */
  @Test
  public void test_constructor() {
    Board<Character> myBoard = new BattleShipBoard<Character>(3,2, 'X');
    Coordinate c = new Coordinate(0,1);
    RectangleShip<Character> myShip = new RectangleShip<>(c, 's','*');
    myBoard.tryAddShip(myShip);
    BoardTextView view = new BoardTextView(myBoard);
    String header = "  0|1|2\n";
    String body = "A  |s|  A\n"+
                  "B  | |  B\n";   
    String expected  = header+body+header;
      
    assertEquals(expected, view.displayMyOwnBoard());
  }
  
  /**
   * Test whether the rectangle is counstructed correctly
   */
  @Test
  public void test_make_coords() {
    Coordinate upper = new Coordinate(1, 2);
    Coordinate middle = new Coordinate(2, 2);
    Coordinate lower = new Coordinate(3,2);
    HashSet<Coordinate> expected = new HashSet<Coordinate>();
    expected.add(upper);
    expected.add(middle);
    expected.add(lower);
    HashSet<Coordinate> coords = makeCoords(upper, 1, 3);
    assertEquals(expected, coords);
  } 

  /**
   * Test the identification of whether the coordinate is part of the ship
   */
  @Test
  public void test_coordinate_in_this_ship() {
    Coordinate upperLeft = new Coordinate(1, 2);
    RectangleShip<Character> ship = new RectangleShip<>(upperLeft,'s','*');
    Coordinate outsideCor = new Coordinate(2, 1);
    assertThrows(IllegalArgumentException.class, () ->ship.checkCoordinateInThisShip(outsideCor));
  }

  /**
   * Test whether hit on ship could be recorded and could be referred to 
   */
  @Test
  public void test_record_it_and_was_hit_at() {
    Coordinate upperLeft = new Coordinate(1, 2);
    Coordinate middleLeft = new Coordinate(2, 2);
    Coordinate lowerLeft = new Coordinate(3, 2);
    RectangleShip<Character> ship = new RectangleShip<>("submarine",upperLeft,1, 3, 's','*');
    assertEquals("submarine", ship.getName());
    assertEquals(false, ship.wasHitAt(upperLeft));
    assertEquals(false, ship.wasHitAt(middleLeft));
    assertEquals(false, ship.wasHitAt(lowerLeft));
    ship.recordHitAt(upperLeft);
    ship.recordHitAt(middleLeft);
    ship.recordHitAt(lowerLeft);
    assertEquals(true, ship.wasHitAt(upperLeft));
    assertEquals(true, ship.wasHitAt(middleLeft));
    assertEquals(true, ship.wasHitAt(lowerLeft));
  }

  /**
   * Test the identification of  whether a ship is sunk or not 
   */
  @Test
  public void test_is_sunk() {
    Coordinate upperLeft = new Coordinate(1, 2);
    Coordinate middleLeft = new Coordinate(2, 2);
    Coordinate lowerLeft = new Coordinate(3, 2);
    RectangleShip<Character> ship = new RectangleShip<>("submarine", upperLeft,1, 3, 's','*');
    assertEquals("submarine", ship.getName());
    assertEquals(false, ship.isSunk());
    ship.recordHitAt(upperLeft);
    ship.recordHitAt(middleLeft);
    ship.recordHitAt(lowerLeft);
    assertEquals(true, ship.isSunk());
  }

  /**
   * Test whether can get the correct display information of a coordinate on the ship
   */
  @Test
  public void test_get_display_info_at(){
    Coordinate upperLeft= new Coordinate(1,2);
    RectangleShip<Character> ship = new RectangleShip<>("submarine", upperLeft, 1, 3,'s','*');
    assertEquals("submarine", ship.getName());
    assertEquals('s',ship.getDisplayInfoAt(upperLeft, true));
    assertEquals(null,ship.getDisplayInfoAt(upperLeft, false));
    ship.recordHitAt(upperLeft); 
    assertEquals('*',ship.getDisplayInfoAt(upperLeft, true));        
    assertEquals('s',ship.getDisplayInfoAt(upperLeft, false));
  }

  /**
   * Test whether can get all the coordinates of a ship
   */
  @Test
  public void test_get_coordinates(){
    Coordinate upperLeft= new Coordinate(1,2);
    RectangleShip<Character> ship = new RectangleShip<>("submarine", upperLeft, 1, 2,'s','*');
    Iterable<Coordinate> shipSet = ship.getCoordinates();    
    HashSet<Coordinate> expected = new HashSet<>();
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(2, 2);
    expected.add(c1);
    expected.add(c2);
    assertEquals(expected, shipSet);
  }
  
}

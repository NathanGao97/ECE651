package edu.duke.zg79.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class V1ShipFactoryTest {

  /**
   * This help to check whether the various ship factory meet our expectation
   */
  private void checkShip(Ship<Character> testShip, String expectedName, char expectedLetter, Coordinate... expectedLocs) {
    for (Coordinate loc : expectedLocs) {
      assertEquals(expectedLetter, testShip.getDisplayInfoAt(loc, true));
      assertEquals(expectedName, testShip.getName());
    } 
  }

  /**
   * Test invalid orientation in ship creation
   */
  @Test
  public void test_create_ship() {
    Placement a = new Placement(new Coordinate(1, 2), 'A');
    V1ShipFactory submarine = new V1ShipFactory();
    assertThrows(IllegalArgumentException.class, () -> submarine.makeSubmarine(a));;

  }
  
  /**
   * Test the factory of submarine
   */
  @Test
  public void test_submarine() {
    Placement v = new Placement(new Coordinate(1, 2), 'V');
    Placement h = new Placement(new Coordinate(1, 2), 'H');
    V1ShipFactory submarine = new V1ShipFactory();
    Ship<Character> submarine_v = submarine.makeSubmarine(v);
    Ship<Character> submarine_h = submarine.makeSubmarine(h);
    checkShip(submarine_v, "Submarine", 's', new Coordinate(1, 2), new Coordinate(2, 2));
    checkShip(submarine_h, "Submarine", 's', new Coordinate(1, 2), new Coordinate(1, 3));
  }

  /**
   * Test the factory of battleship
   */
  @Test
  public void test_battleship() {
    Placement v = new Placement(new Coordinate(1, 2), 'V');
    Placement h = new Placement(new Coordinate(1, 2), 'H');
    V1ShipFactory battleship = new V1ShipFactory();
    Ship<Character> battleship_v = battleship.makeBattleship(v);
    Ship<Character> battleship_h = battleship.makeBattleship(h);
    checkShip(battleship_v, "Battleship", 'b', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2), new Coordinate(4, 2));
    checkShip(battleship_h, "Battleship", 'b', new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4), new Coordinate(1, 5));
  }

  /**
   * Test the factory of carrier
   */
  @Test
  public void test_carrier() {
    Placement v = new Placement(new Coordinate(1, 2), 'V');
    Placement h = new Placement(new Coordinate(1, 2), 'H');
    V1ShipFactory carrier = new V1ShipFactory();
    Ship<Character> carrier_v = carrier.makeCarrier(v);
    Ship<Character> carrier_h = carrier.makeCarrier(h);
    checkShip(carrier_v, "Carrier", 'c', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2), new Coordinate(4, 2), new Coordinate(5, 2), new Coordinate(6, 2));
    checkShip(carrier_h, "Carrier", 'c', new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4), new Coordinate(1, 5), new Coordinate(1, 6), new Coordinate(1, 7));
  }

  /**
   * Test the factory of destroyer
   */
  @Test
  public void test_destroyer() {
    Placement v = new Placement(new Coordinate(1, 2), 'V');
    Placement h = new Placement(new Coordinate(1, 2), 'H');
    V1ShipFactory destroyer = new V1ShipFactory();
    Ship<Character> destroyer_v = destroyer.makeDestroyer(v);
    Ship<Character> destroyer_h = destroyer.makeDestroyer(h);
    checkShip(destroyer_v, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(2, 2), new Coordinate(3, 2));
    checkShip(destroyer_h, "Destroyer", 'd', new Coordinate(1, 2), new Coordinate(1, 3), new Coordinate(1, 4));
  }

}

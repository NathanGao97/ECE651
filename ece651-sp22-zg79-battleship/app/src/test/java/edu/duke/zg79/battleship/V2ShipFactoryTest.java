package edu.duke.zg79.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class V2ShipFactoryTest {
  /**
   * Test invalid orientation in ship creation
   */
  @Test
  public void test_create_ship() {
    Placement a = new Placement(new Coordinate(1, 2), 'A');
    V2ShipFactory submarine = new V2ShipFactory();
    assertThrows(IllegalArgumentException.class, () -> submarine.makeSubmarine(a));
    assertThrows(IllegalArgumentException.class, () -> submarine.makeBattleship(a));;
    assertThrows(IllegalArgumentException.class, () -> submarine.makeCarrier(a));
  }

}

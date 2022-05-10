package edu.duke.zg79.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class SimpleShipDisplayInfoTest {

  /**
   * Test whether the dispaly information is correct
   */
  @Test
  public void test_get_info() {
    SimpleShipDisplayInfo<Integer> info = new SimpleShipDisplayInfo<Integer>(3, 2);
    Coordinate c = new Coordinate(3, 2);
    boolean hit = true;
    boolean miss = false;
    int res_hit = info.getInfo(c, hit);
    int res_miss = info.getInfo(c, miss);
    int expected_hit = 2;
    int expected_miss = 3;
    assertEquals(expected_hit, res_hit);
    assertEquals(expected_miss, res_miss);
  }

}

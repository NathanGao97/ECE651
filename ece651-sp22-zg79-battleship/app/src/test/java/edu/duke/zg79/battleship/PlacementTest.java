package edu.duke.zg79.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class PlacementTest {

  /** 
   * Test whether we can get the correct where and orientation
   */
  @Test
  public void test_where__and_orientation() {
    Coordinate c1 = new Coordinate(10, 20);
    Placement p1 = new Placement(c1, 'v');
    assertEquals(c1, p1.getWhere());
    assertEquals('V', p1.getOrientation());
  }
  
  /**
   * Test whether two same placements could be identified
   */
  @Test
  public void test_equals() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(1, 3);
    Coordinate c4 = new Coordinate(3, 2);
    Placement p1 = new Placement(c1, 'v');
    Placement p2 = new Placement(c2, 'V');
    Placement p3 = new Placement(c3, 'v');
    Placement p4 = new Placement(c4, 'H');
    assertEquals(p1, p1);   
    assertEquals(p1, p2);   
    assertNotEquals(p1, p3);  
    assertNotEquals(p1, p4);
    assertNotEquals(p3, p4);
    assertNotEquals(p1, "Coordinate: (1, 2), Orientation: 'V'");
    
  }

  /**
   * Test the overrided hashCode
   */
  @Test
  public void test_hashCode() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(0, 3);
    Coordinate c4 = new Coordinate(2, 1); 
    Placement p1 = new Placement(c1, 'v');
    Placement p2 = new Placement(c2, 'V');
    Placement p3 = new Placement(c3, 'v');
    Placement p4 = new Placement(c4, 'H');
    assertEquals(p1.hashCode(), p2.hashCode());
    assertNotEquals(p1.hashCode(), p3.hashCode());
    assertNotEquals(p1.hashCode(), p4.hashCode());
  }

  /**
   * Test the overrided toString
   */
  @Test
  public void test_toString() {
    Coordinate c1 = new Coordinate(1, 2);
    Coordinate c2 = new Coordinate(1, 2);
    Coordinate c3 = new Coordinate(0, 3);
    Coordinate c4 = new Coordinate(2, 1);
    Placement p1 = new Placement(c1, 'v');
    Placement p2 = new Placement(c2, 'V');
    Placement p3 = new Placement(c3, 'v');
    Placement p4 = new Placement(c4, 'H');
    assertEquals(p1.toString(), p2.toString());
    assertNotEquals(p1.toString(), p3.toString());
    assertNotEquals(p1.toString(), p4.toString());
  }

  /**
   * Test the vaild cases of the placement constructor which takes a described string
   */
  @Test
  public void test_string_constructor_valid_cases() {
    Placement p1 = new Placement("B3H");
    assertEquals(1, p1.getWhere().getRow());
    assertEquals(3, p1.getWhere().getColumn());
    assertEquals('H', p1.getOrientation());
    Placement p2 = new Placement("D5v");
    assertEquals(3, p2.getWhere().getRow());
    assertEquals(5, p2.getWhere().getColumn());
    assertEquals('V', p2.getOrientation());
    Placement p3 = new Placement("A9h");
    assertEquals(0, p3.getWhere().getRow());
    assertEquals(9, p3.getWhere().getColumn());
    assertEquals('H', p3.getOrientation());
    Placement p4 = new Placement("Z0V");
    assertEquals(25, p4.getWhere().getRow());
    assertEquals(0, p4.getWhere().getColumn());
    assertEquals('V', p4.getOrientation());
  }

  /**
   * Test the invalid cases of the placement constructor which takes a described string
   */
  @Test
  public void test_string_constructor_error_cases() {
    assertThrows(IllegalArgumentException.class, () -> new Placement("8ZH"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A0Q"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("@0H"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A01a"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("B55w"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("AAV"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("89V"));
    assertThrows(IllegalArgumentException.class, () -> new Placement("A12H"));
  }

}

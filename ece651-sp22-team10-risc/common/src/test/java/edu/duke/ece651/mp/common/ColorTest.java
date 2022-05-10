package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class ColorTest {
  @Test
  public void testEquals() {
    Color c1 = new Color("blue");
    Color c2 = new Color("red");

    assertEquals(c1, new Color("blue"));
    assertNotEquals(c1, c2);
    assertNotEquals(c1, "(blue)");
  }

  @Test
  public void testToString() {
    Color c1 = new Color("blue");
    assertEquals("(blue)", c1.toString());
  }

  @Test
  public void testHashCode() {
    Color c1 = new Color("green");
    Color c2 = new Color("green");
    Color c3 = new Color("red");
    assertEquals(c1.hashCode(), c2.hashCode());
    assertNotEquals(c1.hashCode(), c3.hashCode());
  }

  @Test
  public void test_resource() {
    Color color = new Color("Red");
    assertEquals(1, color.getTechLevel());
    assertEquals(100, color.getFoodResource());
    assertEquals(50, color.getSanityResource());
    color.upgradeTechLevel();
    assertEquals(2, color.getTechLevel());
    color.updateFoodResource(-40);
    assertEquals(60, color.getFoodResource());
    color.updateSanityResource(-20);
    assertEquals(30, color.getSanityResource());
  }

  @Test
  public void test_visibility() {
    Color color = new Color("Red");
    color.emptyVisibility();
    color.addVisibleTerritory("Astapor");
  }

}


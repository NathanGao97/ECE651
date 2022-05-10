package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.util.List;

public class V1UnitFactoryTest {
  @Test
  public void test_createBasicUnit() {
    AbstractUnitFactory f = new V1UnitFactory();
    Color red = new Color("Red");
    assertEquals(new BasicUnit(red), f.createBasicUnit(red));
  }

  @Test
  public void test_create_unit_list() {
    AbstractUnitFactory f = new V1UnitFactory();
    Color red = new Color("Red");
    f.createUnitsByType("Chopper", red, 2);
    f.createUnitsByType("Nami", red, 2);
    f.createUnitsByType("Usopp", red, 2);
    f.createUnitsByType("Franky", red, 2);
    f.createUnitsByType("Sanji", red, 2);
    f.createUnitsByType("Zoro", red, 2);
    f.createUnitsByType("Luffy", red, 2);
    f.createUnitsByType("Jinger", red, 2);
    f.createUnitsByType("Spy", red, 2);
    f.createBasicUnit(red, 2);
  }

}

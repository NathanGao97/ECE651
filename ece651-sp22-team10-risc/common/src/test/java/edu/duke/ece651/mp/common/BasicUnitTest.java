package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import java.util.Random;

public class BasicUnitTest {
  @Test
  public void test_getOwner() {
    Color owner = new Color("Red");
    Unit unit = new BasicUnit(owner);
    assertEquals(unit.getOwner(), owner);
  }

  @Test
  public void test_battle1() {
    Color owner = new Color("Red");
    Color owner1 = new Color("Blue");
    Unit myUnit = new BasicUnit(owner);
    Unit enemyUnit = new BasicUnit(owner1);
    myUnit.battle(enemyUnit, new Random());
    myUnit.battle(enemyUnit, new Random());
    myUnit.battle(enemyUnit, new Random());
  }

  @Test
  public void test_battle2() {
    Color owner = new Color("Red");
    Color owner1 = new Color("Blue");
    Unit myUnit = new BasicUnit(owner);
    Unit enemyUnit = new BasicUnit(owner1);
    myUnit.battle(enemyUnit, new Random());
    myUnit.battle(enemyUnit, new Random());
    myUnit.battle(enemyUnit, new Random());
  }
  @Test
  public void test_battle3() {
    Color owner = new Color("Red");
    Color owner1 = new Color("Blue");
    Unit myUnit = new BasicUnit(owner);
    Unit enemyUnit = new BasicUnit(owner1);
    myUnit.battle(enemyUnit, new Random());
    myUnit.battle(enemyUnit, new Random());
    myUnit.battle(enemyUnit, new Random());
  }
  @Test
  public void test_battle4() {
    Color owner = new Color("Red");
    Color owner1 = new Color("Blue");
    Unit myUnit = new BasicUnit(owner);
    Unit enemyUnit = new BasicUnit(owner1);
    myUnit.battle(enemyUnit, new Random());
    myUnit.battle(enemyUnit, new Random());
    myUnit.battle(enemyUnit, new Random());
  }
  
  @Test
  public void test_hashCode() {
    Color owner = new Color("Red");
    Color owner1 = new Color("Red");
    Unit myUnit = new BasicUnit(owner);
    Unit enemyUnit = new BasicUnit(owner1);
    assertEquals(myUnit, enemyUnit);
    assertEquals(false, myUnit.equals(null));
    assertEquals(myUnit.hashCode(), enemyUnit.hashCode());
  }
}

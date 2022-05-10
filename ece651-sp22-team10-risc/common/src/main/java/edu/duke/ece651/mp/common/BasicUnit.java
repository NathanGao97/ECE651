package edu.duke.ece651.mp.common;
import java.util.Random;
import java.io.Serializable;

/*
  BasicUnit class : implements unit interface to represent a basic 
  kind of units. It can call battle method to allow fights with enemy
  unit.
 */

public class BasicUnit implements Unit, Serializable {
  /*
    Parameter Owner records the name of owner. 
   */
  private Color owner;

  private int bonus;

  private String type;

  public BasicUnit(Color owner) {
    this(owner, "", 0);
  }

  public BasicUnit(Color _owner, String _type, int _bonus){
    this.owner = _owner;
    this.type = _type;
    this.bonus = _bonus;
  }

  /*
    This function realize battle between current unit and enemy unit.
    A random number is generated to determin the battle result.
   */
  @Override
  public Unit battle(Unit attacker, Random rand) {
    int defenderRand = rand.nextInt(20) + bonus;
    int attackerRand = rand.nextInt(20) + attacker.getBonus();
    if (defenderRand >= attackerRand) {
      return this;
    }
    return attacker;
  }

  /*
    This function returns the owner of the current unit.
   */
  @Override
  public Color getOwner() {
    return owner;
  }

  @Override
  public String getType() {
    return type;
  }

  @Override
  public int getBonus() {
    return bonus;
  }

  @Override
  public int compareTo(Unit other) {
    return this.getBonus() - other.getBonus();
  }

  /**
   * This compares whether two Colors are the same
   */
  @Override
  public boolean equals(Object o) {
    if (o != null && o.getClass().equals(getClass())) {
      BasicUnit u = (BasicUnit) o;
      return owner.equals(u.getOwner()) && type.equals(u.getType());
    }
    return false;
  }
  
  /**
   * This transforms Color to string
   */
  @Override
  public String toString() {
    return owner + " " + type;
  }

  /**
   * This transforms the Color string to hashcode
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }
  
  public void addBonus() {
    bonus += 3;
  }
}

package edu.duke.ece651.mp.common;
import java.util.Random;

/*
  Unit interface: Implemented by different kinds of units.
 */
public interface Unit extends Comparable<Unit> {
  /*
    Allow current units to battle with enemy units.
    Return a string to represent the result of the battle.
   */
  public Unit battle(Unit enemy, Random rand);

  /*
    Return the owner of current unit.
   */

  public Color getOwner();

  public int getBonus();
  public String getType();
  public void addBonus();
}

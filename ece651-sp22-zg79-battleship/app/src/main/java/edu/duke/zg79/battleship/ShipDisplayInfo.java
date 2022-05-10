package edu.duke.zg79.battleship;

/**
 * This class show the display information of a ship
 */
public interface ShipDisplayInfo<T> {

  /**
   * This specifies which display information to return
   */
  public T getInfo(Coordinate where, boolean hit);
  
}

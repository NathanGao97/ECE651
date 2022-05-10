package edu.duke.zg79.battleship;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * This class handles a real BattleShip that 
 * in the 'T' shape
 *
 * Besides, each coordinate on the ship is given a specific ID, which is
 * helpful to specify its position relative to the top-left corner,
 * as shown below:
 *
 *     1      2      432      4      
 *    234     31      1      13      
 *            4               2
 *
 *    Up    Right    Down    Left
 *
 *
 *
 */
public class TShapedShip<T> extends BasicShip<T> {

  /**
   * This is the name of the ship
   */
  private final String name;

  /**
   * This is the top-left corner of the ship
   */
  private final Coordinate upperLeft;

  /**
   * This is the orientation of the ship
   */
  private final char orientation;
  
  /**
   * This get the name of this Ship, which should be "battleship".
   *
   * @return the name of this ship
   */
  public String getName() {
        return name;
    }
    
  /**
   * This make the coordinate set, 
   * and pass them up to the BasicShip constructor
   */
  public TShapedShip(String name, Coordinate upperLeft, char orientation, ShipDisplayInfo<T> shipDisplayInfo, ShipDisplayInfo<T> enemyDisplayInfo) {
    super(makeCoords(upperLeft, orientation), shipDisplayInfo, enemyDisplayInfo);
    this.name = name;
    this.upperLeft = upperLeft;
    this.orientation = orientation;
  }

  /**
   * This refactor the BasicShip and TShapedShip 
   * 
   z*/
  public TShapedShip(String name, Coordinate upperLeft, char orientation, T data, T onHit) {
    this(name, upperLeft, orientation, new SimpleShipDisplayInfo<T>(data, onHit), new SimpleShipDisplayInfo<T>(null, data));
  }
  
  /**
   * This generate the set of coordinates for a 'T' shaped ship
   *
   * @param upperLeft is the upper left coordinate of the 'T' shaped ship 
   * @param orientation is the layout style of ship, which is 'U' or 'D' or 'L' or 'R'
   */
  public static HashSet<Coordinate> makeCoords(Coordinate upperLeft, char orientation) {
    HashSet<Coordinate> coords = new HashSet<Coordinate>();
    int upperLeftRow = upperLeft.getRow();
    int upperLeftColumn = upperLeft.getColumn();
    if (orientation == 'U') { // Up orientation
      coords.add(new Coordinate(upperLeftRow, upperLeftColumn + 1));
      coords.add(new Coordinate(upperLeftRow + 1, upperLeftColumn));
      coords.add(new Coordinate(upperLeftRow + 1, upperLeftColumn + 1));
      coords.add(new Coordinate(upperLeftRow + 1, upperLeftColumn + 2));
    }
    if (orientation == 'D') { // Down orientation
      coords.add(new Coordinate(upperLeftRow, upperLeftColumn));
      coords.add(new Coordinate(upperLeftRow, upperLeftColumn + 1));
      coords.add(new Coordinate(upperLeftRow, upperLeftColumn + 2));
      coords.add(new Coordinate(upperLeftRow + 1, upperLeftColumn + 1));
    }
    if (orientation == 'L') { // Left orientation
      coords.add(new Coordinate(upperLeftRow, upperLeftColumn + 1));
      coords.add(new Coordinate(upperLeftRow + 1, upperLeftColumn));
      coords.add(new Coordinate(upperLeftRow + 1, upperLeftColumn + 1));
      coords.add(new Coordinate(upperLeftRow + 2, upperLeftColumn + 1));
    }
    if (orientation == 'R') { // Right orientation
      coords.add(new Coordinate(upperLeftRow, upperLeftColumn));
      coords.add(new Coordinate(upperLeftRow + 1, upperLeftColumn));
      coords.add(new Coordinate(upperLeftRow + 1, upperLeftColumn + 1));
      coords.add(new Coordinate(upperLeftRow + 2, upperLeftColumn));
    }
    return coords;
  }

  /**
   * This get the ID-coordinate relationship by comparing coordinate's 
   * relative position with the top-left corner
   *
   * @param upperLeft is the upper left coordinate of the 'T' shaped ship 
   * @param c is the given coordinate to find its ID 
   * @param orientation is the layout style of ship, which is 'U' or 'D' or 'L' or 'R'
   */
  public HashMap<Integer, Coordinate> getidCoordinatePair(Coordinate upperLeft, char orientation) {
    HashMap<Integer, Coordinate> idCoordiantePair = new HashMap<Integer, Coordinate>();
    int upperLeftRow = upperLeft.getRow();
    int upperLeftColumn = upperLeft.getColumn();
    if (orientation == 'U') { // Up orientation
      idCoordiantePair.put(1, new Coordinate(upperLeftRow, upperLeftColumn + 1));
      idCoordiantePair.put(2, new Coordinate(upperLeftRow + 1, upperLeftColumn));
      idCoordiantePair.put(3, new Coordinate(upperLeftRow + 1, upperLeftColumn + 1));
      idCoordiantePair.put(4, new Coordinate(upperLeftRow + 1, upperLeftColumn + 2));
    }
    if (orientation == 'D') { // Down orientation
      idCoordiantePair.put(1, new Coordinate(upperLeftRow + 1, upperLeftColumn + 1));
      idCoordiantePair.put(2, new Coordinate(upperLeftRow, upperLeftColumn + 2));
      idCoordiantePair.put(3, new Coordinate(upperLeftRow, upperLeftColumn + 1));
      idCoordiantePair.put(4, new Coordinate(upperLeftRow, upperLeftColumn));
    }
    if (orientation == 'L') { // Left orientation
      idCoordiantePair.put(1, new Coordinate(upperLeftRow + 1, upperLeftColumn));
      idCoordiantePair.put(2, new Coordinate(upperLeftRow + 2, upperLeftColumn + 1));
      idCoordiantePair.put(3, new Coordinate(upperLeftRow + 1, upperLeftColumn + 1));
      idCoordiantePair.put(4, new Coordinate(upperLeftRow, upperLeftColumn + 1));
    }
    if (orientation == 'R') { // Right orientation
      idCoordiantePair.put(1, new Coordinate(upperLeftRow + 1, upperLeftColumn + 1));
      idCoordiantePair.put(2, new Coordinate(upperLeftRow, upperLeftColumn));
      idCoordiantePair.put(3, new Coordinate(upperLeftRow + 1, upperLeftColumn));
      idCoordiantePair.put(4, new Coordinate(upperLeftRow + 2, upperLeftColumn));
    }
    return idCoordiantePair;
  }

  /**
   * Move all hit coordinates on this ship to a new same-typed ship at the given placement
   *
   * @param p is the placement of the new ship
   * @return a list of coordinates on the new ship that are corresponding to be hit
   */
  @Override
  public ArrayList<Coordinate> moveHitCoordinate(Placement p) {
    ArrayList<Coordinate> hits = new ArrayList<Coordinate>(); // a list to store corresponding hit coordinates on the new ship

    Iterable<Coordinate> hitCoordinates = this.getCoordinates(); // coordinates of this ship
    Coordinate newCoordinate = p.getWhere();
    char newOrientation = p.getOrientation();

    HashMap<Integer, Coordinate> oldPair = this.getidCoordinatePair(upperLeft, orientation); // ID-coordinate relationship for this ship
    HashMap<Integer, Coordinate> newPair = this.getidCoordinatePair(newCoordinate, newOrientation); // ID-coordinate relationship for the new ship

    for (Coordinate c : hitCoordinates) { // check every coordinate on this ship
      if (this.wasHitAt(c)) { // find hit coordinates
        for (Integer id: oldPair.keySet()) {
          if (oldPair.get(id).equals(c)) { // get ID from coordinate in this ship
            hits.add(newPair.get(id)); // get coordinate from ID in the new ship and add to list
            break;
          }
        }
      }
    }
    return hits; // return a list of hit coordinates on the new ship
  }

}

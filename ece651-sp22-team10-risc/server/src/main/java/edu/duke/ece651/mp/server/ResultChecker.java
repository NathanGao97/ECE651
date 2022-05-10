package edu.duke.ece651.mp.server;
import edu.duke.ece651.mp.common.*;

/*
 * This interface is used to check the result of game.
 */
public interface ResultChecker {
  public boolean resultCheck(RiscMap myMap, Color myColor);
}

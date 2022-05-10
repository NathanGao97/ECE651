package edu.duke.zg79.battleship;

import java.util.ArrayList;

/**
 * This class show the completion status of a player
 */
public class SimpleCompletionRules<T> implements CompletionRules<T> {

  /**
   * The player's board, from which we could justify
   * whether the palyer has lost or not
   */
  private final Board<T> myBoard;
  
  /**
   * This initializes the palyer's board
   */
  public SimpleCompletionRules(Board<T> myBoard) {
    this.myBoard = myBoard;
  }
  
   /**
 * This check whether the player has lost the game
 */
  public boolean checkCompletion() {
    ArrayList<Ship<T>> myShips = myBoard.getShips(); // get all ships on the board
    for (Ship<T> ship : myShips) {
      if(!ship.isSunk()) { // if at least one ship on the borad has not sunked, the player is still alive
        return false;
      }
    }
    return true; // all ships on the board are sunked, the player has lost
  }
}

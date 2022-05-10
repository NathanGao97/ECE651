package edu.duke.zg79.battleship;

/**
 * This class show the completion status of a player
 */
public interface CompletionRules<T> {

  /**
 * This check whether the player has lost the game
 */
  public boolean checkCompletion();

}

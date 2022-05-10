package edu.duke.zg79.battleship;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class BoardTextViewTest {
  /**
   * This generate an empty sample board with width, height, header, body provided
   */
  private void emptyBoardHelper(int w, int h, String expectedHeader, String expectedBody){
    Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  /**
   * This generate a full sample board with width, height, header, body provided
   */
  private void BoardHelper(int w, int h, String expectedHeader, String expectedBody){
    Board<Character> b1 = new BattleShipBoard<Character>(w, h, 'X');
    for (int row = 0; row < h; row++) {
      for (int column = 0; column < w; column++) {
        RectangleShip<Character> ship = new RectangleShip<Character>(new Coordinate(row, column), 's', '*');
        b1.tryAddShip(ship);
      }
    }
    BoardTextView view = new BoardTextView(b1);
    assertEquals(expectedHeader, view.makeHeader());
    String expected = expectedHeader + expectedBody + expectedHeader;
    assertEquals(expected, view.displayMyOwnBoard());
  }

  /**
   * Check the 2 x 2 board
   */
  @Test
  public void test_display_empty_2by2() {   
    String expectedHeader= "  0|1\n";
    String expectedBody =  
      "A  |  A\n"+
      "B  |  B\n";
      emptyBoardHelper(2, 2, expectedHeader, expectedBody);
  }

  /** 
   * Check the 3 x 2 board
   */
  @Test
  public void test_display_empty_3by2() {
    String expectedHeader= "  0|1|2\n";
    String expectedBody =  
      "A  | |  A\n"+
      "B  | |  B\n";
      emptyBoardHelper(3, 2, expectedHeader, expectedBody);
  }

  /** 
   * Check the 3 x 5 board
   */
  @Test
  public void test_display_empty_3by5() {
    String expectedHeader= "  0|1|2\n";
    String expectedBody =  
      "A  | |  A\n"+
      "B  | |  B\n"+
      "C  | |  C\n"+
      "D  | |  D\n"+
      "E  | |  E\n";
      emptyBoardHelper(3, 5, expectedHeader, expectedBody);
  }

  /** 
   * Check the 4 x 3 board
   */
  @Test
  public void test_display_empty_4by3() {
    String expectedHeader= "  0|1|2|3\n";
    String expectedEmptyBody =  
      "A  | | |  A\n"+
      "B  | | |  B\n"+
      "C  | | |  C\n";
    String expectedBody =  
      "A s|s|s|s A\n"+
      "B s|s|s|s B\n"+
      "C s|s|s|s C\n";
      emptyBoardHelper(4, 3, expectedHeader, expectedEmptyBody);
      BoardHelper(4, 3, expectedHeader, expectedBody);
  }
  
  /**
   * Check whether invalid board size could be identified
   */
  @Test
  public void test_invalid_board_size() {
    Board<Character> wideBoard = new BattleShipBoard<Character>(11,20, 'X');
    Board<Character> tallBoard = new BattleShipBoard<Character>(10,27, 'X');
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(wideBoard));
    assertThrows(IllegalArgumentException.class, () -> new BoardTextView(tallBoard));
  }

  /** 
   * Check the display of the enemy's board
   */
  @Test
  public void test_display_enemy_board() {
    String expectedHeader= "  0|1|2|3\n";
    String expectedBodyOwn =  
      "A  |s| |  A\n"+
      "B  |s| |  B\n"+
      "C  | | |  C\n";
    String expectedBodyEnemy =  
      "A  | | |  A\n"+
      "B  | | |  B\n"+
      "C  | | |  C\n";
    String expectedOwn = expectedHeader + expectedBodyOwn + expectedHeader;
    String expectedEnemy = expectedHeader + expectedBodyEnemy + expectedHeader;
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(4, 3, 'X');
    Placement p = new Placement(new Coordinate(0, 1), 'V');
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> s = factory.makeSubmarine(p);
    board.tryAddShip(s);
    BoardTextView view_before = new BoardTextView(board);
    assertEquals(expectedOwn, view_before.displayMyOwnBoard());
    assertEquals(expectedEnemy, view_before.displayEnemyBoard());

    String expectedFireBodyOwn =  
      "A  |*| |  A\n"+
      "B  |s| |  B\n"+
      "C  | | |  C\n";
    String expectedFireBodyEnemy =  
      "A X|s| |  A\n"+
      "B  | | |  B\n"+
      "C  | | |  C\n";
    String expectedFireOwn = expectedHeader + expectedFireBodyOwn + expectedHeader;
    String expectedFireEnemy = expectedHeader + expectedFireBodyEnemy + expectedHeader;
    board.fireAt(new Coordinate(0, 0));
    board.fireAt(new Coordinate(0, 1));
    BoardTextView view_after = new BoardTextView(board);
    assertEquals(expectedFireOwn, view_after.displayMyOwnBoard());
    assertEquals(expectedFireEnemy, view_after.displayEnemyBoard());    
  }

  /** 
   * Check the display of two boards together   
   */
  @Test
  public void test_display_my_board_with_enemy_next_to_it() {
    BattleShipBoard<Character> boardOwn = new BattleShipBoard<Character>(4, 4, 'X');
    BattleShipBoard<Character> boardEnemy = new BattleShipBoard<Character>(4, 4, 'X');

    BoardTextView viewOwn = new BoardTextView(boardOwn);
    BoardTextView viewEnemy = new BoardTextView(boardEnemy);
    String myHeader = "Your ocean";
    String enemyHeader = "Player B's ocean";
    String expected_header = "     Your ocean               Player B's ocean\n";

    String expected_body = "  0|1|2|3                    0|1|2|3\n" +   
      "A  | | |  A                A  | | |  A\n" +
      "B  | | |  B                B  | | |  B\n" +
      "C  | | |  C                C  | | |  C\n" +
      "D  | | |  D                D  | | |  D\n" +
      "  0|1|2|3                    0|1|2|3\n";
      String expected = expected_header + expected_body;
    assertEquals(expected, viewOwn.displayMyBoardWithEnemyNextToIt(viewEnemy, myHeader, enemyHeader));
  }

}

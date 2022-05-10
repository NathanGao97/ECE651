package edu.duke.zg79.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class TextPlayerTest {
  /*
  /**
   * This help to create a text player
   */
  private TextPlayer createTextPlayer(String name, int w, int h, String inputData, OutputStream bytes) {
    BufferedReader input = new BufferedReader(new StringReader(inputData));
    PrintStream output = new PrintStream(bytes, true);
    Board<Character> board = new BattleShipBoard<Character>(w, h, 'X');
    V2ShipFactory shipFactory = new V2ShipFactory();
    return new TextPlayer(name, board, input, output, shipFactory);
  }


  /**
   * Test whether EOF expection would be thrown when get an empty input  
   */
  @Test
  void test_EOF_exception() throws IOException {
     ByteArrayOutputStream bytes = new ByteArrayOutputStream();
     BufferedReader input = new BufferedReader(new StringReader(""));
     PrintStream output = new PrintStream(bytes, true);
     Board<Character> board = new BattleShipBoard<Character>(10, 20, 'X');
     V2ShipFactory shipFactory = new V2ShipFactory();
     String prompt = "Player A where do you want to place a Submarine?";
     assertThrows(EOFException.class, () -> new TextPlayer("A", board, input, output, shipFactory).readPlacement(prompt));
     }
  
  /**
   * Test whether could get the correct input information from player or not  
   */
  @Test
  void test_read_placement() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer("A",10, 20, "B2V\nC8H\na4v\n", bytes);
    
    String prompt = "Please enter a location for a ship:";
    Placement[] expected = new Placement[3];
    expected[0] = new Placement(new Coordinate(1, 2), 'V');
    expected[1] = new Placement(new Coordinate(2, 8), 'H');
    expected[2] = new Placement(new Coordinate(0, 4), 'V');

    for (int i = 0; i < expected.length; i++) {
      Placement p = player.readPlacement(prompt);
      assertEquals(p, expected[i]); //did we get the right Placement back
      assertEquals(prompt + "\n", bytes.toString()); //should have printed prompt and newline
      bytes.reset(); //clear out bytes for next time around
    }
  }

  /**
   * Test whether the lost of a player could be correctly detected
   */
  @Test
  void test_check_lose() throws IOException {
    Board<Character> board = new BattleShipBoard<Character>(4, 4, 'X');
    Placement p = new Placement(new Coordinate(0, 1), 'V');
    V1ShipFactory factory = new V1ShipFactory();
    Ship<Character> s = factory.makeSubmarine(p);
    board.tryAddShip(s);

    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    BufferedReader input = new BufferedReader(new StringReader(""));
    PrintStream output = new PrintStream(bytes, true);
    
    V2ShipFactory shipFactory = new V2ShipFactory();
    TextPlayer player = new TextPlayer("A", board, input, output, shipFactory);
    assertEquals(false, player.checkLose());
    board.fireAt(new Coordinate(0, 1));
    assertEquals(false, player.checkLose());
    board.fireAt(new Coordinate(1, 1));
    assertEquals(true, player.checkLose());
    }

  /**
   * Test whether the attck could be correctly displayed
   */
  @Test
  void test_one_play_turn() throws IOException {
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(4, 4, 'X');
    BoardTextView view = new BoardTextView(board);
    V2ShipFactory f = new V2ShipFactory();
    Ship<Character> s = f.makeSubmarine(new Placement("A0V"));
    board.tryAddShip(s);
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    TextPlayer player = createTextPlayer("A", 4, 4, "f\naa1\n*1\nz1\naa\na8\nA0\nf\nB1\n", bytes);
    player.playOneTurn(board, view, "B");
    player.playOneTurn(board, view, "B");
    String expected = "Player A's turn:\n"+
         "     Your ocean               Player B's ocean\n"+
          "  0|1|2|3                    0|1|2|3\n"+
   "A  | | |  A                A  | | |  A\n"+
   "B  | | |  B                B  | | |  B\n"+
   "C  | | |  C                C  | | |  C\n"+
   "D  | | |  D                D  | | |  D\n"+   
        "  0|1|2|3                    0|1|2|3\n"+
      "\nPossible actions for Player A:\n\n\n" +
      "F Fire at a square\n" +
      "M Move a ship to another square (3 remaining)\n" +
      "S Sonar scan (3 remaining)\n\n\n" +
      "Player A, what would you like to do?\n" +
       "Please choose a coordinate to fire at:\n" +
      "That coordinate is invalid: it does not have the correct format.\n" +
      "Please choose a coordinate to fire at:\n" +
      "That coordinate is invalid: it does not have the correct format.\n" +
      "Please choose a coordinate to fire at:\n" +
      "That coordinate is invalid: it is out of the board.\n" +
      "Please choose a coordinate to fire at:\n" +
      "That coordinate is invalid: it does not have the correct format.\n" +
      "Please choose a coordinate to fire at:\n" +
      "That coordinate is invalid: it is out of the board.\n" +
      "Please choose a coordinate to fire at:\n" +
      "You hit a Submarine!\n"+
      "Player A's turn:\n"+
         "     Your ocean               Player B's ocean\n"+
          "  0|1|2|3                    0|1|2|3\n"+
   "A  | | |  A                A s| | |  A\n"+
   "B  | | |  B                B  | | |  B\n"+
   "C  | | |  C                C  | | |  C\n"+
   "D  | | |  D                D  | | |  D\n"+   
        "  0|1|2|3                    0|1|2|3\n"+
      "\nPossible actions for Player A:\n\n\n" +
      "F Fire at a square\n" +
      "M Move a ship to another square (3 remaining)\n" +
      "S Sonar scan (3 remaining)\n\n\n" +
      "Player A, what would you like to do?\n" +
      "Please choose a coordinate to fire at:\n" +
      "You missed!\n";
     assertEquals(expected, bytes.toString());

     }
     
}

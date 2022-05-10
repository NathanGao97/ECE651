package edu.duke.zg79.battleship;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

public class ComputerPlayerTest {

  /*
  /**
   * Test whether fire could be called correctly
   */
  @Test
  public void test_fire() throws IOException{
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(2, 2, 'X');
    BoardTextView view = new BoardTextView(board);
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader inputSource = new BufferedReader(input);
    PrintStream out = System.out;
    V2ShipFactory factory = new V2ShipFactory();
    ComputerPlayer player = new ComputerPlayer("A", board, inputSource, out, factory);
    player.doScan(board);
    player.playOneTurn(board, view, "B");
    player.playOneTurn(board, view, "B");
    player.playOneTurn(board, view, "B");
    player.playOneTurn(board, view, "B");
    player.playOneTurn(board, view, "B");
  }

  /*
  /**
   * Test whether move and scan could be called correctly
   */
  @Test
  public void test_move_and_scan() throws IOException{
    BattleShipBoard<Character> board = new BattleShipBoard<Character>(10, 20, 'X');
    BoardTextView view = new BoardTextView(board);
    InputStreamReader input = new InputStreamReader(System.in);
    BufferedReader inputSource = new BufferedReader(input);
    PrintStream out = System.out;
    V2ShipFactory factory = new V2ShipFactory();
    ComputerPlayer player = new ComputerPlayer("A", board, inputSource, out, factory);
    player.doPlacementPhase();
    player.doMove();
    player.doMove();
    player.doMove();
    player.doScan(board);
    for (int i = 0; i < 35; i++) {
      player.playOneTurn(board, view, "B");
    }
    player.doScan(board);
    for (int i = 0; i < 35; i++) {
      player.playOneTurn(board, view, "B");
    }
    player.doScan(board);
    player.doScan(board);
    player.doScan(board);
  }
  
}

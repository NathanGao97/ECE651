/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package edu.duke.zg79.battleship;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.StringReader;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;


class AppTest {         

  /**
   * Test whether the program takes inputs and displays outputs correctly for player1's win
   */
  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  void test_main_A_win() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    InputStream input = getClass().getClassLoader().getResourceAsStream("input1.txt");
    assertNotNull(input);
    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output1.txt");
    assertNotNull(expectedStream);
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;
     try {
      System.setIn(input);
      System.setOut(out);
      App.main(new String[0]);
    }
    finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }
    String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();
    assertEquals(expected, actual);
  } 

  /**
   * Test whether the program takes inputs and displays outputs correctly for player2's win
   */
  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  void test_main_B_win() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    InputStream input = getClass().getClassLoader().getResourceAsStream("input2.txt");
    assertNotNull(input);
    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output2.txt");
    assertNotNull(expectedStream);
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;
     try {
      System.setIn(input);
      System.setOut(out);
      App.main(new String[0]);
    }
    finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }
    String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();
    assertEquals(expected, actual);
  }
 
  /**
   * Test whether the program takes inputs and displays outputs correctly with corner cases
   */
  @Test
  @ResourceLock(value = Resources.SYSTEM_OUT, mode = ResourceAccessMode.READ_WRITE)
  void test_main_computer() throws IOException {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
    PrintStream out = new PrintStream(bytes, true);
    InputStream input = getClass().getClassLoader().getResourceAsStream("input4.txt");
    assertNotNull(input);
    InputStream expectedStream = getClass().getClassLoader().getResourceAsStream("output4.txt");
    assertNotNull(expectedStream);
    InputStream oldIn = System.in;
    PrintStream oldOut = System.out;
     try {
      System.setIn(input);
      System.setOut(out);
      App.main(new String[0]);
    }
    finally {
      System.setIn(oldIn);
      System.setOut(oldOut);
    }
    String expected = new String(expectedStream.readAllBytes());
    String actual = bytes.toString();
    assertEquals(expected, actual);
  }
}

package edu.duke.ece651.mp.client;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;   
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

import edu.duke.ece651.mp.common.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.parallel.ResourceAccessMode;
import org.junit.jupiter.api.parallel.ResourceLock;
import org.junit.jupiter.api.parallel.Resources;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;


//@ExtendWith(ApplicationExtension.class)
//public class ClientTest {
//  @Start
//  private void start(Stage stage) throws IOException, ClassNotFoundException {
//    Client client = new Client();
//    client.start(stage);
//  }
//
//  @Test
//  public void test_start(FxRobot robot){
//    assertTrue(Client.player != null);
//  }
//}

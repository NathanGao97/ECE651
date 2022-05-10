package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.ByteArrayInputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Disabled;

import edu.duke.ece651.mp.common.Color;
import edu.duke.ece651.mp.common.Message;
import edu.duke.ece651.mp.common.RiscMap;
import edu.duke.ece651.mp.common.V1MapFactory;
import edu.duke.ece651.mp.server.Player;
import edu.duke.ece651.mp.server.RiscGame;
import edu.duke.ece651.mp.server.RiscGameServer;
import edu.duke.ece651.mp.common.*;


public class UserTest {

    private class DummyClient {
    final Socket socket;
    final ObjectInputStream in;
    final ObjectOutputStream out;

    public DummyClient(Socket socket, ObjectInputStream in, ObjectOutputStream out) {
      this.socket = socket;
      this.in = in;
      this.out = out;
    }
  }

  
  private DummyClient createClientConnection(int port)
    throws IOException {
    Socket playerSocket = new Socket("localhost", port);
    ObjectOutputStream out = new ObjectOutputStream(playerSocket.getOutputStream());
    out.flush();
    ObjectInputStream in = new ObjectInputStream(playerSocket.getInputStream());
    DummyClient client = new DummyClient(playerSocket, in, out);
    return client;
  }
  
    
  private void assertUserLogin(DummyClient client, String username, String password) throws IOException, ClassNotFoundException {
    client.out.writeObject(new Message("login"));
    client.out.writeObject(new Message(username));
    client.out.writeObject(new Message(password));
    assertEquals(new Message("ok"), (Message) client.in.readObject());
  }


  @Test
  public void test_playNewGame() throws IOException, InterruptedException, ClassNotFoundException {
    V1MapFactory mf = new V1MapFactory();
    AbstractUnitFactory uf = new V1UnitFactory();
    int port = 12347;
    RiscGameServer server = new RiscGameServer(port, mf, uf);
    ObjectOutputStream output = new ObjectOutputStream(new ByteArrayOutputStream());
    User u0 = server.createUser("asd", "qwe", null, output);
    u0.sendObject(new Message("testSendObject"));
    User u1 = server.createUser("qwe", "asd", null, output);
    u0.loggedIn = false;
    u1.loggedIn = false;
    Thread th = new Thread() {
        @Override()
        public void run() {
          try {
            server.listenForConnection();
          } catch (Exception e) {}
        }
      };
    th.start();
    Thread c0 = new Thread() {
        @Override()
        public void run() {
          try {
            DummyClient client = createClientConnection(port);
            assertUserLogin(client, "asd", "qwe");
            client.out.writeObject(new Message("new"));
            client.out.writeObject(new Message("1"));
            client.socket.close();
            client = createClientConnection(port);
            Thread.sleep(100);
            assertUserLogin(client, "asd", "qwe");
            client.out.writeObject(new Message("new"));
            client.out.writeObject(new Message("2"));
            client.socket.close();
            client = createClientConnection(port);
            Thread.sleep(100);
            assertUserLogin(client, "asd", "qwe");
            client.out.writeObject(new Message("invalid request"));
            client.socket.close();
          } catch (Exception e) {}
        }
      };
    Thread c1 = new Thread() {
        @Override()
        public void run() {
          try {
            DummyClient client = createClientConnection(port);
            assertUserLogin(client, "qwe", "asd");
            client.out.writeObject(new Message("new"));
            client.out.writeObject(new Message("2"));
          } catch (Exception e) {}
        }
      };
    c0.start();
    c1.start();
    c0.join();
    c1.join();
    th.interrupt();
  }

  
  @Test
  public void test_resumeGame() throws IOException, InterruptedException, ClassNotFoundException {
    V1MapFactory mf = new V1MapFactory();
    AbstractUnitFactory uf = new V1UnitFactory();
    int port = 12348;
    RiscGameServer server = new RiscGameServer(port, mf, uf);
        ObjectOutputStream output = new ObjectOutputStream(new ByteArrayOutputStream());
    User u0 = server.createUser("asd", "qwe", null, output);
    User u1 = server.createUser("qwe", "asd", null, output);
    u0.loggedIn = false;
    u1.loggedIn = false;
    Thread th = new Thread() {
        @Override()
        public void run() {
          try {
            server.listenForConnection();
          } catch (Exception e) {}
        }
      };
    th.start();
    Thread c0 = new Thread() {
        @Override()
        public void run() {
          try {
            DummyClient client = createClientConnection(port);
            assertUserLogin(client, "asd", "qwe");
            client.out.writeObject(new Message("new"));
            client.out.writeObject(new Message("2"));
            client.socket.close();
            client = createClientConnection(port);
            Thread.sleep(100);
            assertUserLogin(client, "asd", "qwe");
            client.out.writeObject(new Message("continue"));
            assertEquals(new Message("continue"), (Message) client.in.readObject());
            assertEquals(u0.players.get(0).color, (Color) client.in.readObject());
            assertEquals(server.games.get(0).gameId, (Integer) client.in.readObject());
            assertEquals(2, (Integer) client.in.readObject());
            assertEquals(new Message("done"), (Message) client.in.readObject());
            client.out.writeObject(new Message("0"));
            assertEquals(new Message("ok"), (Message) client.in.readObject());
            assertEquals(new Message("order"), (Message) client.in.readObject());
            assertEquals(server.games.get(0).gameMap, (RiscMap) client.in.readObject());
            client.socket.close();
          } catch (Exception e) {}
        }
      };
    Thread c1 = new Thread() {
        @Override()
        public void run() {
          try {
            DummyClient client = createClientConnection(port);
            assertUserLogin(client, "qwe", "asd");
            client.out.writeObject(new Message("new"));
            client.out.writeObject(new Message("2"));
          } catch (Exception e) {}
        }
      };
    c1.start();
    Thread.sleep(100);
    c0.start();
    c0.join();
    c1.join();
    th.interrupt();
  }

}

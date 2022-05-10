package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.ByteArrayOutputStream;
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

public class RiscGameServerTest {
    
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

  private void assertCreateUser(DummyClient client, String username, String password) throws IOException, ClassNotFoundException {
    client.out.writeObject(new Message("create"));
    client.out.writeObject(new Message(username));
    client.out.writeObject(new Message(password));
    assertEquals(new Message("ok"), (Message) client.in.readObject());
  }


  @Test
  public void test_createUser() throws IOException, ClassNotFoundException, InterruptedException {
    V1MapFactory mf = new V1MapFactory();
    AbstractUnitFactory uf = new V1UnitFactory();
    int port = 12344;
    RiscGameServer server = new RiscGameServer(port, mf, uf);
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
            assertCreateUser(client, "asd", "qwe");
          } catch (Exception e) {}
        }
      };
    Thread c1 = new Thread() {
        @Override()
        public void run() {
          try {
            DummyClient client = createClientConnection(port);
            assertCreateUser(client, "qwe", "asd");
          } catch (Exception e) {}
        }
      };
    Thread c2 = new Thread() {
        @Override()
        public void run() {
          try {
            DummyClient client = createClientConnection(port);
            // create a user with already existed username
            client.out.writeObject(new Message("create"));
            client.out.writeObject(new Message("qwe"));
            client.out.writeObject(new Message("das"));
            assertEquals(new Message("Failed to login or create user"), (Message) client.in.readObject());
            client.out.writeObject(new Message("create"));
            client.out.writeObject(new Message("das"));
            client.out.writeObject(new Message("qwe"));
            assertEquals(new Message("ok"), (Message) client.in.readObject());
          } catch (Exception e) {}
        }
      };
    c0.start();
    c1.start();
    Thread.sleep(100);
    c2.start();
    c0.join();
    c1.join();
    c2.join();
    th.interrupt();
  }
  
  private void assertUserLogin(DummyClient client, String username, String password) throws IOException, ClassNotFoundException {
    client.out.writeObject(new Message("login"));
    client.out.writeObject(new Message(username));
    client.out.writeObject(new Message(password));
    assertEquals(new Message("ok"), (Message) client.in.readObject());
  }
  

  @Test
  public void test_userLogin() throws IOException, ClassNotFoundException, InterruptedException {
    V1MapFactory mf = new V1MapFactory();
    AbstractUnitFactory uf = new V1UnitFactory();
    int port = 12346;
    RiscGameServer server = new RiscGameServer(port, mf, uf);
    ObjectOutputStream output = new ObjectOutputStream(new ByteArrayOutputStream());
    User u0 = server.createUser("asd", "qwe", null, output);
    User u1 = server.createUser("qwe", "asd", null, output);
    User u2 = server.createUser("das", "qwe", null, output);
    u0.loggedIn = false;
    u1.loggedIn = false;
    u2.loggedIn = false;
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
          } catch (Exception e) {}
        }
      };
    Thread c1 = new Thread() {
        @Override()
        public void run() {
          try {
            DummyClient client = createClientConnection(port);
            assertUserLogin(client, "qwe", "asd");
          } catch (Exception e) {}
        }
      };
    Thread c2 = new Thread() {
        @Override()
        public void run() {
          try {
            DummyClient client = createClientConnection(port);
            // login with wrong username
            client.out.writeObject(new Message("login"));
            client.out.writeObject(new Message("???"));
            client.out.writeObject(new Message("???"));
            assertEquals(new Message("Failed to login or create user"), (Message) client.in.readObject());
            // login with wrong password
            client.out.writeObject(new Message("login"));
            client.out.writeObject(new Message("das"));
            client.out.writeObject(new Message("das"));
            client.out.flush();
            assertEquals(new Message("Failed to login or create user"), (Message) client.in.readObject());
            // login to a already logged in user
            client.out.writeObject(new Message("login"));
            client.out.writeObject(new Message("qwe"));
            client.out.writeObject(new Message("asd"));
            assertEquals(new Message("Failed to login or create user"), (Message) client.in.readObject());
            client.out.writeObject(new Message("login"));
            client.out.writeObject(new Message("das"));
            client.out.writeObject(new Message("qwe"));
            assertEquals(new Message("ok"), (Message) client.in.readObject());
          } catch (Exception e) {}
        }
      };
    c0.start();
    c1.start();
    Thread.sleep(100);
    c2.start();
    c0.join();
    c1.join();
    c2.join();
    th.interrupt();
  }

  @Test
  public void test_playGame() throws InterruptedException, IOException {
    V1MapFactory mf = new V1MapFactory();
    AbstractUnitFactory uf = new V1UnitFactory();
    int port = 8716;
    RiscGameServer server = new RiscGameServer(port, mf, uf);
    Thread th = new Thread() {
        @Override()
        public void run() {
          server.playGame(server.createNewGame(2));
        }
      };
    th.start();
    Thread.sleep(100);
    th.interrupt();
  }

}

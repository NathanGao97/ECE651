package edu.duke.ece651.mp.server;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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

import static org.mockito.Mockito.mock;

public class PlayerTest {
  
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

  private List<Unit> addNBasicUnitToTerritory(Territory terr, Color unitColor, int n) {
    List<Unit> result = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      if (terr != null) {
        terr.addUnit(new BasicUnit(unitColor));
      }
      result.add(new BasicUnit(unitColor));
    }
    return result;
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
  
  private void assertPlayerDoPlacement(DummyClient client, Color expectedColor, RiscMap expectedMap, List<Message> placements) throws IOException, ClassNotFoundException {
    assertEquals(expectedColor, (Color) client.in.readObject());
    assertEquals(expectedMap, (RiscMap) client.in.readObject());
    for (int i = 0; i < placements.size(); i++) {
      client.out.writeObject(placements.get(i));
      String expectedResult = null;
      if (placements.get(i).equals(new Message())) {
        expectedResult = "Invalid message owner";
      }
      if (i == placements.size() - 1) {
        expectedResult = "commit";
      }
      assertEquals(new Message(expectedResult, null, "", "", placements.get(i).getAllocatedUnits()), (Message) client.in.readObject());
    }
    assertEquals(expectedMap, (RiscMap) client.in.readObject());
  }


  @Test
  public void test_doPlacementPhase() throws IOException, InterruptedException, ClassNotFoundException {
    int port = 13579;
    RiscGameServer server = new RiscGameServer(port, new V1MapFactory(), new V1UnitFactory());
    RiscGame game = server.createNewGame(2);
    List<Color> colorList = game.gameMap.getColorList();
    Thread serverThread = new Thread() {
        @Override()
        public void run() {
          try {
            server.initializeNewGame(game);
            Thread gameThread = new Thread() {
                @Override()
                public void run() {
                  try {
                    game.doPlacementPhase();
                  } catch (Exception e) {
                  }
                }
              };
            Thread p0Thread = new Thread() {
                @Override()
                public void run() {
                  try {
                    game.players.get(0).doPlacementPhase();
                    game.players.get(0).sendObject(game.gameMap);
                  } catch (Exception e) {
                  }
                }
              };
            Thread p1Thread = new Thread() {
                @Override()
                public void run() {
                  try {
                    game.players.get(1).doPlacementPhase();
                    game.players.get(1).sendObject(game.gameMap);
                  } catch (Exception e) {
                  }
                }
              };
            gameThread.start();
            p0Thread.start();
            p1Thread.start();
            gameThread.join();
            p0Thread.join();
            p1Thread.join();
          } catch (Exception e) {
          }
        }
      };
    serverThread.start();
    Thread p0Thread = new Thread() {
        @Override()
        public void run() {
          try {
            DummyClient client0 = createClientConnection(port);
            AbstractUnitFactory f = new V1UnitFactory();
            List<Unit> units1 = new ArrayList<>();
            for (int i = 0; i < 10; i++) {
              units1.add(f.createChopperUnit(colorList.get(0)));
            }
          List<Message> placements = new ArrayList();
          placements.add(new Message());
          placements.add(new Message("placement", colorList.get(0), "Asshai", null, units1));
          placements.add(new Message("placement", colorList.get(0), "Braavos", null, units1));
          assertPlayerDoPlacement(client0, colorList.get(0), game.gameMap, placements);
          client0.socket.close();
          } catch (Exception e) {
          }
        }
      };
    Thread p1Thread = new Thread() {
        @Override()
        public void run() {
          try {
            DummyClient client1 = createClientConnection(port);
            client1.socket.close();
          } catch (Exception e) {
          }
        }
      };
    p0Thread.start();
    Thread.sleep(100);
    p1Thread.start();
    p0Thread.join();
    p1Thread.join();
    serverThread.join();
  }
  
  
  private void assertPlayerDoIssueOrder(DummyClient client, RiscMap expectedMap, List<Message> orders) throws IOException, ClassNotFoundException {
    assertEquals(expectedMap, (RiscMap) client.in.readObject());
    assertEquals(new Message("continue"), (Message) client.in.readObject());
    for (int i = 0; i < orders.size(); i++) {
      client.out.writeObject(orders.get(i));
      client.out.flush();
      String expectedResult = null;
      if (orders.get(i).getOwner() == null) {
        expectedResult = "Invalid message owner";
      } else if (orders.get(i).getType().equals("commit")) {
        break;
      } 
      Message result  = (Message) client.in.readObject();
      assertEquals(new Message(expectedResult), result);
    }
    assertEquals(new Message(null), (Message) client.in.readObject());
    RiscMap map = (RiscMap) client.in.readObject();
    //System.out.println(expectedMap.getTerritoryByName("Braavos"));
    //System.out.println(map.getTerritoryByName("Braavos"));
    assertEquals(expectedMap, map);
    assertEquals(new Message("continue"), (Message) client.in.readObject());
  }
  
  @Test
  public void test_doIssueOrderPhase() throws IOException, InterruptedException, ClassNotFoundException {
    int port = 13580;
    RiscGameServer server = new RiscGameServer(port, new V1MapFactory(), new V1UnitFactory());
    RiscGame game = server.createNewGame(2);
    List<Color> colorList = game.gameMap.getColorList();
    Thread serverThread = new Thread() {
      @Override()
      public void run() {
        try {
          server.initializeNewGame(game);          
          Thread gameThread = new Thread() {
              @Override()
              public void run() {
                try {
                  game.doIssueOrderPhase();
                } catch (Exception e) {
                }
              }
            };
          Thread p0Thread = new Thread() {
              @Override()
              public void run() {
                try {
                  game.players.get(0).doIssueOrderPhase();
                } catch (Exception e) {
                }
              }
            };
          Thread p1Thread = new Thread() {
              @Override()
              public void run() {
                try {
                  game.players.get(1).doIssueOrderPhase();
                } catch (Exception e) {
                }
              }
            };
          gameThread.start();
          p0Thread.start();
          p1Thread.start();
        } catch (Exception e) {
        }
      }
    };
    serverThread.start();
    Thread p0Thread = new Thread() {
      @Override()
      public void run() {
        try {
          DummyClient client0 = createClientConnection(port);
          Territory src = game.gameMap.getTerritoryByName("Asshai");
          Color color = colorList.get(0);
          addNBasicUnitToTerritory(src, color, 10);
          List<Message> orders = new ArrayList();
          orders.add(new Message("move"));
          orders.add(new Message("move", color, "Asshai", "Braavos", addNBasicUnitToTerritory(null, color, 5)));
          orders.add(new Message("commit", color, null, null, new ArrayList<>()));
          assertPlayerDoIssueOrder(client0, game.gameMap, orders);
          client0.socket.close();
        } catch (Exception e) {
        }
      }
    };
    Thread p1Thread = new Thread() {
      @Override()
      public void run() {
        try {
          DummyClient client1 = createClientConnection(port);
          client1.socket.close();
        } catch (Exception e) {
        }
      }
    };
    p0Thread.start();
    Thread.sleep(100);
    p1Thread.start();
    Thread.sleep(100);
    p0Thread.join();
    p1Thread.join();
    serverThread.interrupt();
    
  }
  

  @Test
  public void test_doPostGamePhase() throws IOException, InterruptedException, ClassNotFoundException {
    int port = 13581;
    RiscGameServer server = new RiscGameServer(port, new V1MapFactory(), new V1UnitFactory());
    RiscGame game = server.createNewGame(3);
    List<Color> colorList = game.gameMap.getColorList();
    Thread serverThread = new Thread() {
      @Override()
      public void run() {
        try {
          server.initializeNewGame(game);
          game.players.get(0).hasLost = true;
          game.players.get(1).hasLost = true;
          Thread p0Thread = new Thread() {
              @Override()
              public void run() {
                try {
                  game.players.get(0).doPostGamePhase();
                  game.gameOver = true;
                } catch (Exception e) {
                }
              }
            };
          Thread p1Thread = new Thread() {
              @Override()
              public void run() {
                try {
                  game.players.get(1).doPostGamePhase();
                } catch (Exception e) {
                }
              }
            };
          Thread p2Thread = new Thread() {
              @Override()
              public void run() {
                try {
                  game.players.get(2).doPostGamePhase();
                } catch (Exception e) {
                }
              }
            };
          p0Thread.start();
          p0Thread.join();
          p1Thread.start();
          p2Thread.start();
          
        } catch (Exception e) {
        }
      }
    };
    serverThread.start();
    Thread p0Thread = new Thread() {
      @Override()
      public void run() {
        try {
          DummyClient client0 = createClientConnection(port);
          assertEquals(game.gameMap, (RiscMap) client0.in.readObject());
          assertEquals(new Message("lose"), (Message) client0.in.readObject());
          client0.out.writeObject(new Message("stay"));
          client0.socket.close();
        } catch (Exception e) {
        }
      }
    };
    Thread p1Thread = new Thread() {
      @Override()
      public void run() {
        try {
          DummyClient client1 = createClientConnection(port);
          assertEquals(game.gameMap, (RiscMap) client1.in.readObject());
          assertEquals(new Message("done"), (Message) client1.in.readObject());
          client1.socket.close();
        } catch (Exception e) {
        }
      }
    };
    Thread p2Thread = new Thread() {
      @Override()
      public void run() {
        try {
          DummyClient client2 = createClientConnection(port);
          assertEquals(game.gameMap, (RiscMap) client2.in.readObject());
          assertEquals(new Message("win"), (Message) client2.in.readObject());
          client2.socket.close();
        } catch (Exception e) {
        }
      }
    };
    p0Thread.start();
    Thread.sleep(100);
    p1Thread.start();
    Thread.sleep(100);
    p2Thread.start();
    p0Thread.join();
    p1Thread.join();
    p2Thread.join();
    serverThread.interrupt();    
  }


}

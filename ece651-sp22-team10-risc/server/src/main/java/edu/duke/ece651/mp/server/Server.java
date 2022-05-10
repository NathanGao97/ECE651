package edu.duke.ece651.mp.server;

import edu.duke.ece651.mp.common.*;

import edu.duke.ece651.mp.common.Thing;
import java.io.IOException;

public class Server {
  static public void main(String[] args){
    V1MapFactory mapFactory = new V1MapFactory();
    AbstractUnitFactory unitFactory = new V1UnitFactory();
    try {
      RiscGameServer gameServer = new RiscGameServer(Integer.parseInt(args[0]), mapFactory, unitFactory);
      gameServer.listenForConnection();
    } catch (Exception e) {
      System.out.println("Failed to start game server");
    }
  }
}

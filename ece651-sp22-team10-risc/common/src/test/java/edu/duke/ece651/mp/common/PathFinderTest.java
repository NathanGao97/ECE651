package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.*;

import edu.duke.ece651.mp.common.*;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.ArrayList;

public class PathFinderTest {
  @Test
  public void test_path_finder() {
    V1MapFactory myFactory = new V1MapFactory();
    PathFinder myFinder = new PathFinder();
    Color myColor = new Color("Red");
    Color enemyColor = new Color("Blue");
    RiscMap myMap = myFactory.createFourPlayerMap();
    Territory Asshai = myMap.getTerritoryByName("Asshai");
    Territory Astapor = myMap.getTerritoryByName("Astapor");
    Territory Braavos = myMap.getTerritoryByName("Braavos");
    Territory Norvos = myMap.getTerritoryByName("Norvos");
    Territory Meereen = myMap.getTerritoryByName("Meereen");
    Territory Karhold = myMap.getTerritoryByName("Karhold");
    Territory Valyria = myMap.getTerritoryByName("Valyria");
    Territory Tarth = myMap.getTerritoryByName("Tarth");
    Territory Sunspear = myMap.getTerritoryByName("Sunspear");
    Norvos.transferOwnershipTo(myColor);
    Meereen.transferOwnershipTo(myColor);
    Karhold.transferOwnershipTo(myColor);
    Valyria.transferOwnershipTo(myColor);
    Tarth.transferOwnershipTo(myColor);
    Sunspear.transferOwnershipTo(myColor);
    int res1 = myFinder.getMinimalCost(myMap, Astapor, Karhold, 2);
    assertEquals(10, res1);
    int res2 = myFinder.getMinimalCost(myMap, Asshai, Sunspear, 4);
    assertEquals(32, res2);
  }
}

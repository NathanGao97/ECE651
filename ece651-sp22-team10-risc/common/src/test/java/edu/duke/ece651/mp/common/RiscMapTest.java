package edu.duke.ece651.mp.common;
 
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
 
public class RiscMapTest {
  @Test
  public void test_constructor() {
    Territory redTerritory = new Territory(new Color("Red"), "Asshai");
    Territory blueTerritory = new Territory(new Color("Blue"), "Astapor");
    List<Territory> territoryList = new ArrayList<>();
    territoryList.add(redTerritory);
    territoryList.add(blueTerritory);
    List<Color> colorList = new ArrayList<> ();
    colorList.add(new Color("Red"));
    colorList.add(new Color("Blue"));
    RiscMap myMap = new RiscMap(territoryList, colorList);
    assertEquals(myMap.getTerritoryList(), territoryList);
    assertEquals(myMap.getColorList(), colorList);
  }

  @Test
  public void testEquals() {
    Territory t1 = new Territory(new Color("Red"), "Asshai");
    Territory t2 = new Territory(new Color("Blue"), "Astapor");
    Territory t3 = new Territory(new Color("Green"), "Asshai");
    List<Territory> tL1 = new ArrayList<>();
    tL1.add(t1);
    tL1.add(t2);
    List<Territory> tL2 = new ArrayList<>();
    tL1.add(t1);
    tL1.add(t3);
    List<Color> cL1 = new ArrayList<> ();
    cL1.add(new Color("Red"));
    cL1.add(new Color("Blue"));
    RiscMap m1 = new RiscMap(tL1, cL1);
    RiscMap m2 = new RiscMap(tL2, cL1);
    assertEquals(m1, new RiscMap(tL1, cL1));
    assertNotEquals(m1, m2);
    assertNotEquals(m1, "Territory: AsshaiRed AstaporBlue Color: Red Blue ");
  }

  @Test
  public void testToString() {
    Territory redTerritory = new Territory(new Color("Red"), "Asshai");
    Territory blueTerritory = new Territory(new Color("Blue"), "Astapor");
    List<Territory> territoryList = new ArrayList<>();
    territoryList.add(redTerritory);
    territoryList.add(blueTerritory);
    List<Color> colorList1 = new ArrayList<> ();
    colorList1.add(new Color("Red"));
    colorList1.add(new Color("Blue"));
    RiscMap myMap = new RiscMap(territoryList, colorList1);
    assertEquals("Territory: AsshaiRed AstaporBlue Color: Red Blue ", myMap.toString());
  }
  
  @Test
  public void testHashCode() {
    Territory redTerritory = new Territory(new Color("Red"), "Asshai");
    Territory blueTerritory = new Territory(new Color("Blue"), "Astapor");
    List<Territory> territoryList = new ArrayList<>();
    territoryList.add(redTerritory);
    territoryList.add(blueTerritory);
    List<Color> colorList1 = new ArrayList<> ();
    colorList1.add(new Color("Red"));
    colorList1.add(new Color("Blue"));
    List<Color> colorList2 = new ArrayList<> ();
    colorList2.add(new Color("Red"));
    colorList2.add(new Color("Green"));
    RiscMap m1 = new RiscMap(territoryList, colorList1);
    RiscMap m2 = new RiscMap(territoryList, colorList1);
    RiscMap m3 = new RiscMap(territoryList, colorList2);
    assertEquals(m1.hashCode(), m2.hashCode());
    assertNotEquals(m1.hashCode(), m3.hashCode());
  }

  @Test
  public void testTerritoryRelatedFcns() {
    V1MapFactory myFactory = new V1MapFactory();
    RiscMap myMap = myFactory.createThreePlayerMap();
    Color red = new Color("Red");
    List<Territory> redList = new ArrayList<>();
    redList.add(myMap.getTerritoryByName("Asshai"));
    redList.add(myMap.getTerritoryByName("Astapor"));
    redList.add(myMap.getTerritoryByName("Braavos"));
    assertEquals(redList, myMap.getTerritoryListOwnedByColor(red));
    assertEquals(null, myMap.getTerritoryByName("LiuQiangQiang"));
  }
}

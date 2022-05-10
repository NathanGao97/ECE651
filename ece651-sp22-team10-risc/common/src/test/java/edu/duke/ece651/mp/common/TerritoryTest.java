package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TerritoryTest {
  @Test
  public void test_color() {
    Color red = new Color("Red");
    Color blue = new Color("Blue");
    Territory myTerritory = new Territory(red, "Asshai");    
    Territory neighbourTerritory = new Territory(blue, "Astapor");
    List<Territory> neighbourList= new ArrayList<>();
    neighbourList.add(neighbourTerritory);
    myTerritory.setNeighbourTerritories(neighbourList);
    assertEquals(myTerritory.getOwner().getName(), "Red");
    assertEquals(myTerritory.getName(), "Asshai");
    assertEquals(myTerritory.getNeighbourTerritories(), neighbourList);
  }

  @Test
  public void test_addUnit() {
    Color blue = new Color("Blue");
    AbstractUnitFactory f = new V1UnitFactory();
    Territory terr = new Territory(blue, "Asshai");
    List<Unit> allyUnits = new ArrayList<>();
    terr.addUnit(f.createZoroUnit(blue));
    allyUnits.add(0, f.createZoroUnit(blue));
    terr.addUnit(f.createSanjiUnit(blue));
    allyUnits.add(0, f.createSanjiUnit(blue));
    terr.addUnit(f.createFrankyUnit(blue));
    allyUnits.add(0, f.createFrankyUnit(blue));
    terr.addUnit(f.createUsoppUnit(blue));
    allyUnits.add(0, f.createUsoppUnit(blue));
    terr.addUnit(f.createNamiUnit(blue));
    allyUnits.add(0, f.createNamiUnit(blue));
    terr.addUnit(f.createChopperUnit(blue));
    allyUnits.add(0, f.createChopperUnit(blue));
    terr.addUnit(f.createLuffyUnit(blue));
    allyUnits.add(f.createLuffyUnit(blue));
    assertEquals(allyUnits, terr.allyUnits);    
    terr.addUnit(f.createSpyUnit(blue));
  }

  private List<Unit> addNBasicUnitToTerritory(Territory terr, Color unitColor, int n) {
    List<Unit> result = new ArrayList<>();
    for (int i = 0; i < n; i++) {
      terr.addUnit(new BasicUnit(unitColor));
      result.add(new BasicUnit(unitColor));
    }
    return result;
  }
 
  @Test
  public void test_transferOwnershipAndRemoveUnit() {
    Color blue = new Color("Blue");
    Color pink = new Color("Pink");
    Territory terr = new Territory(blue, "Asshai");
    terr.addUnit(new BasicUnit(pink, "Spy", 0));
    terr.removeUnit(new BasicUnit(pink, "Spy", 0));
    List<Unit> allyUnits = addNBasicUnitToTerritory(terr, blue, 5);
    terr.transferOwnershipTo(pink);
    assertEquals(allyUnits, terr.enemyUnits.get(blue));
    assertEquals(new ArrayList<Unit>(), terr.allyUnits);
    terr.transferOwnershipTo(blue);
    for (int i = 0; i < allyUnits.size(); i++) {
      terr.removeUnit(allyUnits.get(i));
    }
    List<Unit> enemyUnits = addNBasicUnitToTerritory(terr, pink, 8);
    assertEquals(new ArrayList<Unit>(), terr.allyUnits);
    assertEquals(enemyUnits, terr.enemyUnits.get(pink));
    terr.transferOwnershipTo(pink);
    assertEquals(enemyUnits, terr.allyUnits);
    assertEquals(new ArrayList<Unit>(), terr.enemyUnits.get(blue));
    List<Unit> allyUnits1 = addNBasicUnitToTerritory(terr, blue, 3);
    terr.transferOwnershipTo(blue);
    terr.transferOwnershipTo(blue);
    assertEquals(allyUnits1, terr.allyUnits);
    assertEquals(enemyUnits, terr.enemyUnits.get(pink));
    terr.transferOwnershipTo(pink);
    assertEquals(allyUnits1, terr.enemyUnits.get(blue));
    assertEquals(enemyUnits, terr.allyUnits);
    terr.transferOwnershipTo(blue);
    enemyUnits.remove(new BasicUnit(pink));
    terr.removeUnit(new BasicUnit(pink));
    assertEquals(enemyUnits, terr.enemyUnits.get(pink));
    
    // Testing Collections containsAll()
    allyUnits1.add(new BasicUnit(blue, "nami", 1));
    allyUnits1.add(new BasicUnit(blue));
    allyUnits1.add(new BasicUnit(blue));
    allyUnits1.add(new BasicUnit(blue, "Usopp", 3));
    assertTrue(allyUnits1.containsAll(allyUnits));
    allyUnits.add(new BasicUnit(blue, "Usopp", 3));
    assertTrue(allyUnits1.containsAll(allyUnits));
    allyUnits.add(new BasicUnit(blue, "Franky", 5));
    assertTrue(!allyUnits1.containsAll(allyUnits));
    
  }
  
  @Test
  public void testEquals() {
    Territory t1 = new Territory(new Color("Red"), "Asshai");
    Territory t2 = new Territory(new Color("Blue"), "Asshai");
    assertEquals(t1, new Territory(new Color("Red"), "Asshai"));
    assertNotEquals(t1, t2);
    assertNotEquals(t1, new Color("AsshaiRed"));
  }

  @Test
  public void testToString() {
    Territory t1 = new Territory(new Color("Red"), "Asshai");
    assertEquals("AsshaiRed", t1.toString());
  }

  @Test
  public void testHashCode() {
    Territory t1 = new Territory(new Color("Red"), "Asshai");
    Territory t2 = new Territory(new Color("Red"), "Asshai");
    Territory t3 = new Territory(new Color("Blue"), "Asshai");
    assertEquals(t1.hashCode(), t2.hashCode());
    assertNotEquals(t1.hashCode(), t3.hashCode());
  }

  @Test
  public void testGetSize() {
    Color blue = new Color("Blue");
    Territory myTerritory = new Territory(blue, "Asshai");
    int size = myTerritory.getSize();
    assertEquals(0, size);
  }
  
  @Test
  public void testNewFeatures() {
    AbstractUnitFactory f = new V1UnitFactory();
    Territory t1 = new Territory(new Color("Red"), "Asshai");
    t1.getEnemies();
    t1.hasEnemy();
    t1.cleanEnemies();
    Color color = new Color("Red");
    Color eColor = new Color("Blue");
    assertEquals(0, t1.getAllyUnitSize());
    assertEquals(0, t1.getEnemyUnitSize());
    assertEquals(0, t1.getAllyUnitSizeByType("Chopper"));
    List<Unit> choppers = f.createUnitsByType("Chopper", new Color("Red"), 2);
    List<Unit> namis = f.createUnitsByType("Nami", new Color("Red"), 2);
    t1.removeHighestBonusUnitByColor(color);
    t1.removeLowestBonusUnitByColor(color);
    for (Unit unit : choppers) {
      t1.addUnit(unit);
    }
    for (Unit unit : namis) {
      t1.addUnit(unit);
    }
    assertEquals(2, t1.getAllyUnitSizeByType("Chopper"));
    t1.removeHighestBonusUnitByColor(color);
    t1.removeLowestBonusUnitByColor(color);
    List<Unit> enemys = f.createUnitsByType("Usopp", new Color("Blue"), 2);
    for (Unit unit : enemys) {
      t1.addUnit(unit);
    }
    t1.removeHighestBonusUnitByColor(eColor);
    t1.removeLowestBonusUnitByColor(eColor);
    t1.removeHighestBonusUnitByColor(eColor);
    t1.removeLowestBonusUnitByColor(eColor);
  }

  @Test
  public void test_updates() {
    Territory t1 = new Territory(new Color("Red"), "Asshai");
    Territory t2 = new Territory(new Color("Blue"), "Meereen");
    List<Territory> neigh = new ArrayList<>();
    neigh.add(t2);
    t1.setNeighbourTerritories(neigh);
    assertEquals(true, t1.neighbourOfColor(new Color("Blue")));
    assertEquals(false, t1.neighbourOfColor(new Color("Pink")));
    t1.updateOwnerResource();
    t1.updateCloakCount();
    t1.cloak();
    t1.updateCloakCount();
    assertEquals(true, t1.isCloaked());
    t1.addUnit(new BasicUnit(new Color("Pink"), "Spy", 0));
    t1.addUnit(new BasicUnit(new Color("Red"), "Spy", 0));
    assertEquals(true, t1.hasSpyOfColor(new Color("Red")));
    assertEquals(true, t1.hasSpyOfColor(new Color("Pink")));
    assertEquals(false, t1.hasSpyOfColor(new Color("Blue")));
    
  }

}

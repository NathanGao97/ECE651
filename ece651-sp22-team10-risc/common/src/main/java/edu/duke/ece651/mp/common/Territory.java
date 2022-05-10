package edu.duke.ece651.mp.common;

import java.util.ArrayList;

import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.HashMap;

/*
  Territory Class: This class respresents territories owned by different
  player in a game. It holds unit objects owned by both enemy and owner,
  as well as a list of neighbour territories.
 */
public class Territory implements Serializable{
  private Color owner;
  private String name;
  private List<Territory> neighbourTerritories;
  public List<Unit> allyUnits;
  public HashMap<Color, List<Unit>> enemyUnits;
  public List<Unit> spyUnits;
  private int size;
  private int cloakCount;

  /**
   * Constructs a Territory with the specified color and name
   *
   * @param _color represents the identity of a player
   * @param _name  is the name of the territory
   */
  public Territory(Color _color, String _name) {
    this(_color, _name, 0);
  }

  public Territory(Color _color, String _name, int _size) {
    owner = _color;
    name = _name;
    size = _size;
    cloakCount = 0;
    neighbourTerritories = null;
    allyUnits = new ArrayList<>();
    enemyUnits = new HashMap<>();
    spyUnits = new ArrayList<>();
  }
  
  /**
   * This returns the color of a Territory
   */
  public Color getOwner() {
    return owner;
  }

  /**
   * This returns the color of a Territory
   */
  public String getName() {
    return name;
  }

  /**
   * This returns the size of a Territory
   */
  public int getSize() {
    return size;
  }

  /**
   * This returns the remaining cloak counts of a Territory
   */
  public int getCloakCount() {
    return cloakCount;
  }
  
  /**
   * This sets up the neighbour territories of this territory
   *
   * @param neighbours is a list of neighbour territories
   */
  public void setNeighbourTerritories(List<Territory> neighbours) {
    neighbourTerritories = neighbours;
  }

  /**
   * This returns the color of a Territory
   */
  public List<Territory> getNeighbourTerritories() {
    return neighbourTerritories;
  }

  public boolean neighbourOfColor(Color color) {
    for (Territory terr : neighbourTerritories) {
      if (terr.getOwner().equals(color)) {
        return true;
      }
    }
    return false;
  }

  public Set<Color> getEnemies() {
    return enemyUnits.keySet();
  }

  public boolean hasEnemy() {
    return !enemyUnits.isEmpty();
  }

  /**
   * This function changes the owner.
   */
  public void transferOwnershipTo(Color player) {
    if (!owner.equals(player)) {
      if (allyUnits.size() != 0) {
        enemyUnits.put(owner, allyUnits);
      }
      allyUnits = enemyUnits.getOrDefault(player, new ArrayList<>());
      owner = player;
    }
  }

  public void cleanEnemies() {
    enemyUnits = new HashMap();
  }
  
  /**
   * This function allows the reduction on players' ally units during attacking phase.
   */
  public Unit removeHighestBonusUnitByColor(Color color) {
    if (owner.equals(color)) {
      if (allyUnits.size() == 0) {
        return null;
      }
      return allyUnits.remove(allyUnits.size() - 1);
    }
    List<Unit> unitList = enemyUnits.get(color);
    if (unitList == null || unitList.size() == 0) {
      return null;
    }
    return unitList.remove(unitList.size() - 1);  
  }

  public Unit removeLowestBonusUnitByColor(Color color) {
    if (owner.equals(color)) {
      if (allyUnits.size() == 0) {
        return null;
      }
      return allyUnits.remove(0);
    }
    List<Unit> unitList = enemyUnits.get(color);
    if (unitList == null || unitList.size() == 0) {
      return null;
    }
    return unitList.remove(0);  
  }
  
  // Precondition: toRemove is present in this territory
  public void removeUnit(Unit toRemove) {
    if (toRemove.getType().equals("Spy")) {
      spyUnits.remove(toRemove);
      return;
    }
    if (owner.equals(toRemove.getOwner())) {
      allyUnits.remove(toRemove);
      return;
    } 
    enemyUnits.get(toRemove.getOwner()).remove(toRemove);
    
  }

  private void addUnitToAList(Unit toAdd, List<Unit> list) {
    boolean added = false;
    for (int i = 0; i < list.size(); i++) {
      if (toAdd.compareTo(list.get(i)) <= 0) {
        list.add(i, toAdd);
        added = true;
        break;
      }
    }
    if (!added) {
      list.add(toAdd);
    }
  }
  
  public void addUnit(Unit toAdd) {
    if (toAdd.getType().equals("Spy")) {
      addUnitToAList(toAdd, spyUnits);
    }
    else if (owner.equals(toAdd.getOwner())) {
      addUnitToAList(toAdd, allyUnits);
    }
    else {
      enemyUnits.putIfAbsent(toAdd.getOwner(), new ArrayList<>());
      addUnitToAList(toAdd, enemyUnits.get(toAdd.getOwner()));
    }
  }
  
   /**
   * This compares whether two Territories are the same
   */
  @Override
  public boolean equals(Object obj) {
    if (obj.getClass().equals(getClass())) {
      @SuppressWarnings("unchecked")
      Territory terr = (Territory) obj;
      if (this.getName().equals(terr.getName()) && this.getOwner().getName().equals(terr.getOwner().getName())) {
        return true;
      }

    }
    return false;
  }

   /**
   * This transforms the Territory to string
   */

  @Override
  public String toString() {
    return getName() + getOwner().getName();
  }

  /**
   * This transforms the Territory string to hashcode
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }


  /**
   * This returns the size of ally units list
   */
  public int getAllyUnitSize() {
    return allyUnits.size();
  }

  /**
   * This returns the size of enemy units list
   */
  public int getEnemyUnitSize() {
    return enemyUnits.size();
  }

  public int getAllyUnitSizeByType(String type) {
    int num = 0;
    for (Unit unit : allyUnits) {
      if (unit.getType().equals(type)) {
        num++;
      }
    }
    return num;
  }

  public void updateOwnerResource() {
    System.out.println(this.owner + " food: " + this.owner.getFoodResource());
    System.out.println(this.owner + " sanity: " + this.owner.getSanityResource());      
    this.owner.updateFoodResource(10*size);
    this.owner.updateSanityResource(10*size);
    System.out.println(this.owner + " food(after): " + this.owner.getFoodResource());
    System.out.println(this.owner + " sanity(after): " + this.owner.getSanityResource());
  }

  public void updateCloakCount() {
    if (cloakCount > 0) {
      cloakCount--;
    }
  }

  public void cloak() {
    cloakCount = 3;
  }

  public boolean isCloaked() {
    return !(cloakCount == 0);
  }

  public boolean hasSpyOfColor(Color color) {
    for (Unit unit : spyUnits) {
      if (unit.getOwner().equals(color)) {
        return true;
      }
    }
    return false;
  }

  public void removeAllUnits() {
    allyUnits.clear();
    enemyUnits.clear();
    spyUnits.clear();
  }
}

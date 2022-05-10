package edu.duke.ece651.mp.common;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * This class handles a RiscMap,
 * which containes the territories of a RISC game
 */
public class RiscMap implements Serializable{
  private List<Territory> territories;
  private List<Color> colors;
  public final HashMap<Integer, Integer> technologyLevelUpTable;
  public final HashMap<String, Integer> unitLevelUpTable;
  public final HashMap<String, Integer> unitTechRelationTable;
  
  /**
   * Constructs a RiscMap with the specified Territories
   *
   * @param territoryList contains the territories in this Map 
   */
  public RiscMap (List<Territory> territoryList, List<Color>colorList) {
    this.territories = territoryList;
    this.colors = colorList;
    this.technologyLevelUpTable = new HashMap<> ();
    technologyLevelUpTable.put(1, 50);
    technologyLevelUpTable.put(2, 75);
    technologyLevelUpTable.put(3, 125);
    technologyLevelUpTable.put(4, 200);
    technologyLevelUpTable.put(5, 300);
    this.unitLevelUpTable = new HashMap<> ();
    unitLevelUpTable.put("Chopper", 0);
    unitLevelUpTable.put("Nami", 3);
    unitLevelUpTable.put("Usopp", 11);
    unitLevelUpTable.put("Franky", 30);
    unitLevelUpTable.put("Sanji", 55);
    unitLevelUpTable.put("Zoro", 90);
    unitLevelUpTable.put("Luffy", 140);
    unitLevelUpTable.put("Spy", 20);
    unitLevelUpTable.put("Kaidou", 99999999);
    this.unitTechRelationTable = new HashMap<> ();
    unitTechRelationTable.put("Nami", 1);
    unitTechRelationTable.put("Usopp", 2);
    unitTechRelationTable.put("Franky", 3);
    unitTechRelationTable.put("Sanji", 4);
    unitTechRelationTable.put("Zoro", 5);
    unitTechRelationTable.put("Luffy", 6);
    unitTechRelationTable.put("Spy", 1);
    unitTechRelationTable.put("Kaidou", 7);
  }

  /**
   * This returns a list of Territories on the RiscMap.
   */
  public List<Territory> getTerritoryList() {
    return territories;
  }

  public List<Territory> getTerritoryListOwnedByColor(Color color) {
    List<Territory> result = new ArrayList<>();
    for (Territory terr : territories) {
      if (terr.getOwner().equals(color)) {
        result.add(terr);
      }
    }
    return result;
  }

  public Territory getTerritoryByName(String name) {
    for (Territory terr : territories) {
      if (terr.getName().equals(name)) {
        return terr;
      }
    }
    return null;
  }

  /**
   * This returns a list of all kind of Colors on the RiscMap.
   */
  public List<Color> getColorList() {
    return colors;
  }

  /**
   * This compares whether two RiscMaps are the same
   */
  @Override
  public boolean equals(Object obj) {
    if (obj.getClass().equals(getClass())) {
      @SuppressWarnings("unchecked")
      RiscMap terr = (RiscMap) obj;
      return this.getTerritoryList().equals(terr.getTerritoryList()) && this.getColorList().equals(terr.getColorList());
    }
    return false;
  }

  /**
   * This transforms the RiscMap to string
   */
  @Override
  public String toString() {
    String result = "";
    result += "Territory: ";
    for (Territory territory : territories) {
      result += territory.toString();
      result += " ";
    }
    result += "Color: ";
    for (Color color : colors) {
      result += color.getName();
      result += " ";
    }
    return result;
  }

  /**
   * This transforms the RiscMap string to hashcode
   */
  @Override
  public int hashCode() {
    return toString().hashCode();
  }

}

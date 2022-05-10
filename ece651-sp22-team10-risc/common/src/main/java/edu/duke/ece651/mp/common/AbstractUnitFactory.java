package edu.duke.ece651.mp.common;


import java.util.List;

/*
 * This abstract class is used to allow the creation of
 * different kinds of units.
 */
public interface AbstractUnitFactory {
  public Unit createBasicUnit(Color color);
  public Unit createChopperUnit(Color color);
  public Unit createNamiUnit(Color color);
  public Unit createUsoppUnit(Color color);
  public Unit createFrankyUnit(Color color);
  public Unit createSanjiUnit(Color color);
  public Unit createZoroUnit(Color color);
  public Unit createLuffyUnit(Color color);
  public Unit createSpyUnit(Color color);
  public Unit createKaidouUnit(Color color);

  public List<Unit> createBasicUnit(Color color, int num);
  public List<Unit> createChopperUnit(Color color, int num);
  public List<Unit> createNamiUnit(Color color, int num);
  public List<Unit> createUsoppUnit(Color color, int num);
  public List<Unit> createFrankyUnit(Color color, int num);
  public List<Unit> createSanjiUnit(Color color, int num);
  public List<Unit> createZoroUnit(Color color, int num);
  public List<Unit> createLuffyUnit(Color color, int num);
  public List<Unit> createSpyUnit(Color color, int num);
  public List<Unit> createKaidouUnit(Color color, int num);
  
  public List<Unit> createUnitsByType(String type, Color color, int num);

}

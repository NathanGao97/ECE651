package edu.duke.ece651.mp.common;

import java.util.ArrayList;
import java.util.List;

public class V1UnitFactory implements AbstractUnitFactory {

  // Constructs a list of basic units with the given amount of units
  @Override
  public Unit createBasicUnit(Color color) {
    return new BasicUnit(color);
  }
  
  @Override
  public Unit createChopperUnit(Color color) {
    return new BasicUnit(color, "Chopper", 0);
  }
  @Override
  public Unit createNamiUnit(Color color) {
    return new BasicUnit(color, "Nami", 1);
  }
  @Override
  public Unit createUsoppUnit(Color color) {
    return new BasicUnit(color, "Usopp", 3);
  }
  @Override
  public Unit createFrankyUnit(Color color) {
    return new BasicUnit(color, "Franky", 5);
  }
  @Override
  public Unit createSanjiUnit(Color color) {
    return new BasicUnit(color, "Sanji", 8);
  }
  @Override
  public Unit createZoroUnit(Color color) {
    return new BasicUnit(color, "Zoro", 11);
  }
  @Override
  public Unit createLuffyUnit(Color color) {
    return new BasicUnit(color, "Luffy", 15);
  }
  @Override
  public Unit createSpyUnit(Color color) {
    return new BasicUnit(color, "Spy", 0);
  }
  @Override
  public Unit createKaidouUnit(Color color) {
    return new BasicUnit(color, "Kaidou", 30);
  }

  @Override
  public List<Unit> createBasicUnit(Color color, int num) {
    List<Unit> createdUnits = new ArrayList<>();
    for(int i=0; i<num; i++){
      createdUnits.add(new BasicUnit(color));
    }

    return createdUnits;
  }

  @Override
  public List<Unit> createChopperUnit(Color color, int num) {
    List<Unit> createdUnits = new ArrayList<>();
    for(int i=0; i<num; i++){
      createdUnits.add(this.createChopperUnit(color));
    }

    return createdUnits;
  }

  @Override
  public List<Unit> createNamiUnit(Color color, int num) {
    List<Unit> createdUnits = new ArrayList<>();
    for(int i=0; i<num; i++){
      createdUnits.add(this.createNamiUnit(color));
    }
    return createdUnits;
  }

  @Override
  public List<Unit> createUsoppUnit(Color color, int num) {
    List<Unit> createdUnits = new ArrayList<>();
    for(int i=0; i<num; i++){
      createdUnits.add(this.createUsoppUnit(color));
    }

    return createdUnits;
  }

  @Override
  public List<Unit> createFrankyUnit(Color color, int num) {
    List<Unit> createdUnits = new ArrayList<>();
    for(int i=0; i<num; i++){
      createdUnits.add(this.createFrankyUnit(color));
    }

    return createdUnits;
  }

  @Override
  public List<Unit> createSanjiUnit(Color color, int num) {
    List<Unit> createdUnits = new ArrayList<>();
    for(int i=0; i<num; i++){
      createdUnits.add(this.createSanjiUnit(color));
    }

    return createdUnits;
  }

  @Override
  public List<Unit> createZoroUnit(Color color, int num) {
    List<Unit> createdUnits = new ArrayList<>();
    for(int i=0; i<num; i++){
      createdUnits.add(this.createZoroUnit(color));
    }

    return createdUnits;
  }

  @Override
  public List<Unit> createLuffyUnit(Color color, int num) {
    List<Unit> createdUnits = new ArrayList<>();
    for(int i=0; i<num; i++){
      createdUnits.add(this.createLuffyUnit(color));
    }

    return createdUnits;
  }

  @Override
  public List<Unit> createSpyUnit(Color color, int num) {
    List<Unit> createdUnits = new ArrayList<>();
    for(int i=0; i<num; i++){
      createdUnits.add(this.createSpyUnit(color));
    }

    return createdUnits;
  }

  @Override
  public List<Unit> createKaidouUnit(Color color, int num) {
    List<Unit> createdUnits = new ArrayList<>();
    for(int i=0; i<num; i++){
      createdUnits.add(this.createKaidouUnit(color));
    }

    return createdUnits;
  }

  @Override
  public List<Unit> createUnitsByType(String type, Color color, int num) {
      if (type.equals("Chopper")) {
      return createChopperUnit(color, num);
    }
    if (type.equals("Nami")) {
      return createNamiUnit(color, num);
    }
    if (type.equals("Usopp")) {
      return createUsoppUnit(color, num);
    }
    if (type.equals("Franky")) {
      return createFrankyUnit(color, num);
    }
    if (type.equals("Sanji")) {
      return createSanjiUnit(color, num);
    }
    if (type.equals("Zoro")) {
      return createZoroUnit(color, num);
    }
    if (type.equals("Luffy")) {
      return createLuffyUnit(color, num);
    }
    if (type.equals("Spy")) {
      return createSpyUnit(color, num);
    }
    if (type.equals("Kaidou")) {
      return createKaidouUnit(color, num);
    }
    return null;
  }  
}

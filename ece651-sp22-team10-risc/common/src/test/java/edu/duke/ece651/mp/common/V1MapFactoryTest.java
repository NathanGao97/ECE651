package edu.duke.ece651.mp.common;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

public class V1MapFactoryTest {
  @Test
  public void testTwoPlayerMap() {
    V1MapFactory myFactory = new V1MapFactory(); 
    RiscMap myMap = myFactory.createTwoPlayerMap();
    List<Territory> territoryList = myMap.getTerritoryList();
    assertEquals(territoryList.size(), 6);
    
    Territory Asshai = new Territory(new Color("Red"), "Asshai");
    Territory Astapor = new Territory(new Color("Blue"), "Astapor");
    Territory Braavos = new Territory(new Color("Red"), "Braavos");
    Territory Meereen = new Territory(new Color("Blue"), "Meereen");
    Territory Norvos = new Territory(new Color("Red"), "Norvos");
    Territory Pentos = new Territory(new Color("Blue"), "Pentos");

    
    List<Territory> AsshaiNeighbours = new ArrayList<> ();
    AsshaiNeighbours.add(Astapor);
    AsshaiNeighbours.add(Braavos);
    AsshaiNeighbours.add(Meereen);
    Asshai.setNeighbourTerritories(AsshaiNeighbours);

    List<Territory> AstaporNeighbours = new ArrayList<> ();
    AstaporNeighbours.add(Asshai);
    AstaporNeighbours.add(Meereen);
    Astapor.setNeighbourTerritories(AstaporNeighbours);

    List<Territory> BraavosNeighbours = new ArrayList<> ();
    BraavosNeighbours.add(Asshai);
    BraavosNeighbours.add(Meereen);
    BraavosNeighbours.add(Norvos);
    Braavos.setNeighbourTerritories(BraavosNeighbours);

    List<Territory> MeereenNeighbours = new ArrayList<> ();
    MeereenNeighbours.add(Asshai);
    MeereenNeighbours.add(Astapor);
    MeereenNeighbours.add(Braavos);
    MeereenNeighbours.add(Norvos);
    MeereenNeighbours.add(Pentos);
    Meereen.setNeighbourTerritories(MeereenNeighbours);

    List<Territory> NorvosNeighbours = new ArrayList<> ();
    NorvosNeighbours.add(Braavos);
    NorvosNeighbours.add(Meereen);
    NorvosNeighbours.add(Pentos);
    Norvos.setNeighbourTerritories(NorvosNeighbours);

    List<Territory> PentosNeighbours = new ArrayList<> ();
    PentosNeighbours.add(Meereen);
    PentosNeighbours.add(Norvos);
    Pentos.setNeighbourTerritories(PentosNeighbours);

    for (Territory territory : territoryList) {
      if (territory.getName() == "Asshai") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < AsshaiNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), AsshaiNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Astapor") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < AstaporNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), AstaporNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Braavos") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < BraavosNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), BraavosNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Meereen") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < MeereenNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), MeereenNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Norvos") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < NorvosNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), NorvosNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Pentos") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < PentosNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), PentosNeighbours.get(i).getName());
        }
      }
    }
  }

  @Test
  public void testThreePlayerMap() {
    V1MapFactory myFactory = new V1MapFactory(); 
    RiscMap myMap = myFactory.createThreePlayerMap();
    List<Territory> territoryList = myMap.getTerritoryList();
    assertEquals(territoryList.size(), 9);
    
    Territory Asshai = new Territory(new Color("Red"), "Asshai");
    Territory Astapor = new Territory(new Color("Red"), "Astapor");
    Territory Braavos = new Territory(new Color("Red"), "Braavos");
    Territory Meereen = new Territory(new Color("Blue"), "Meereen");
    Territory Norvos = new Territory(new Color("Blue"), "Norvos");
    Territory Pentos = new Territory(new Color("Blue"), "Pentos");
    Territory Rainwood = new Territory(new Color("Green"), "Rainwood");
    Territory Tarth = new Territory(new Color("Green"), "Tarth");
    Territory Sunspear = new Territory(new Color("Green"), "Sunspear");
    
    List<Territory> AsshaiNeighbours = new ArrayList<> ();
    AsshaiNeighbours.add(Astapor);
    AsshaiNeighbours.add(Meereen);
    Asshai.setNeighbourTerritories(AsshaiNeighbours);

    List<Territory> AstaporNeighbours = new ArrayList<> ();
    AstaporNeighbours.add(Asshai);
    AstaporNeighbours.add(Braavos);
    AstaporNeighbours.add(Meereen);
    AstaporNeighbours.add(Norvos);
    Astapor.setNeighbourTerritories(AstaporNeighbours);

    List<Territory> BraavosNeighbours = new ArrayList<> ();
    BraavosNeighbours.add(Astapor);
    BraavosNeighbours.add(Norvos);
    BraavosNeighbours.add(Rainwood);
    BraavosNeighbours.add(Tarth);
    Braavos.setNeighbourTerritories(BraavosNeighbours);

    List<Territory> MeereenNeighbours = new ArrayList<> ();
    MeereenNeighbours.add(Asshai);
    MeereenNeighbours.add(Astapor);
    MeereenNeighbours.add(Norvos);
    MeereenNeighbours.add(Pentos);
    Meereen.setNeighbourTerritories(MeereenNeighbours);

    List<Territory> NorvosNeighbours = new ArrayList<> ();
    NorvosNeighbours.add(Astapor);
    NorvosNeighbours.add(Braavos);
    NorvosNeighbours.add(Meereen);
    NorvosNeighbours.add(Pentos);
    NorvosNeighbours.add(Tarth);
    NorvosNeighbours.add(Sunspear);
    Norvos.setNeighbourTerritories(NorvosNeighbours);

    List<Territory> PentosNeighbours = new ArrayList<> ();
    PentosNeighbours.add(Meereen);
    PentosNeighbours.add(Norvos);
    PentosNeighbours.add(Sunspear);
    Pentos.setNeighbourTerritories(PentosNeighbours);

    List<Territory> RainwoodNeighbours = new ArrayList<> ();
    RainwoodNeighbours.add(Braavos);
    RainwoodNeighbours.add(Tarth);
    Rainwood.setNeighbourTerritories(RainwoodNeighbours);

    List<Territory> TarthNeighbours = new ArrayList<> ();
    TarthNeighbours.add(Braavos);
    TarthNeighbours.add(Norvos);
    TarthNeighbours.add(Rainwood);
    TarthNeighbours.add(Sunspear);
    Tarth.setNeighbourTerritories(TarthNeighbours);

    List<Territory> SunspearNeighbours = new ArrayList<> ();
    SunspearNeighbours.add(Norvos);
    SunspearNeighbours.add(Pentos);
    SunspearNeighbours.add(Tarth);
    Sunspear.setNeighbourTerritories(SunspearNeighbours);
    
    for (Territory territory : territoryList) {
      if (territory.getName() == "Asshai") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < AsshaiNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), AsshaiNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Astapor") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < AstaporNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), AstaporNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Braavos") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < BraavosNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), BraavosNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Meereen") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < MeereenNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), MeereenNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Norvos") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < NorvosNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), NorvosNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Pentos") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < PentosNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), PentosNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Rainwood") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < RainwoodNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), RainwoodNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Tarth") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < TarthNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), TarthNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Sunspear") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < SunspearNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), SunspearNeighbours.get(i).getName());
        }
      }
    }
  }

  @Test
  public void testFourPlayerMap() {
    V1MapFactory myFactory = new V1MapFactory(); 
    RiscMap myMap = myFactory.createFourPlayerMap();
    List<Territory> territoryList = myMap.getTerritoryList();
    assertEquals(territoryList.size(), 12);
    
    Territory Asshai = new Territory(new Color("Red"), "Asshai");
    Territory Astapor = new Territory(new Color("Red"), "Astapor");
    Territory Braavos = new Territory(new Color("Red"), "Braavos");
    Territory Norvos = new Territory(new Color("Blue"), "Norvos");
    Territory Pentos = new Territory(new Color("Blue"), "Pentos");
    Territory Meereen = new Territory(new Color("Blue"), "Meereen");
    Territory Rainwood = new Territory(new Color("Green"), "Rainwood");
    Territory Tarth = new Territory(new Color("Green"), "Tarth");
    Territory Sunspear = new Territory(new Color("Green"), "Sunspear");
    Territory Valyria = new Territory(new Color("Yellow"), "Valyria");
    Territory Karhold = new Territory(new Color("Yellow"), "Karhold");
    Territory Tyrosh = new Territory(new Color("Yellow"), "Tyrosh");
    
    List<Territory> AsshaiNeighbours = new ArrayList<> ();
    AsshaiNeighbours.add(Astapor);
    AsshaiNeighbours.add(Braavos);
    Asshai.setNeighbourTerritories(AsshaiNeighbours);

    List<Territory> AstaporNeighbours = new ArrayList<> ();
    AstaporNeighbours.add(Asshai);
    AstaporNeighbours.add(Braavos);
    AstaporNeighbours.add(Meereen);
    AstaporNeighbours.add(Norvos);
    Astapor.setNeighbourTerritories(AstaporNeighbours);

    List<Territory> BraavosNeighbours = new ArrayList<> ();
    BraavosNeighbours.add(Asshai);
    BraavosNeighbours.add(Astapor);
    BraavosNeighbours.add(Norvos);
    BraavosNeighbours.add(Pentos);
    BraavosNeighbours.add(Rainwood);
    BraavosNeighbours.add(Valyria);
    Braavos.setNeighbourTerritories(BraavosNeighbours);

    List<Territory> MeereenNeighbours = new ArrayList<> ();
    MeereenNeighbours.add(Astapor);
    MeereenNeighbours.add(Norvos);
    MeereenNeighbours.add(Karhold); 
    MeereenNeighbours.add(Tyrosh);
    Meereen.setNeighbourTerritories(MeereenNeighbours);

    List<Territory> NorvosNeighbours = new ArrayList<> ();
    NorvosNeighbours.add(Astapor);
    NorvosNeighbours.add(Braavos);
    NorvosNeighbours.add(Meereen);
    NorvosNeighbours.add(Pentos);
    NorvosNeighbours.add(Karhold);
    Norvos.setNeighbourTerritories(NorvosNeighbours);

    List<Territory> PentosNeighbours = new ArrayList<> ();
    PentosNeighbours.add(Braavos);
    PentosNeighbours.add(Norvos);
    PentosNeighbours.add(Valyria);
    PentosNeighbours.add(Karhold);
    Pentos.setNeighbourTerritories(PentosNeighbours);

    List<Territory> RainwoodNeighbours = new ArrayList<> ();
    RainwoodNeighbours.add(Braavos);
    RainwoodNeighbours.add(Tarth);
    RainwoodNeighbours.add(Valyria);
    Rainwood.setNeighbourTerritories(RainwoodNeighbours);

    List<Territory> TarthNeighbours = new ArrayList<> ();
    TarthNeighbours.add(Rainwood);
    TarthNeighbours.add(Sunspear);
    TarthNeighbours.add(Valyria);
    Tarth.setNeighbourTerritories(TarthNeighbours);

    List<Territory> SunspearNeighbours = new ArrayList<> ();
    SunspearNeighbours.add(Tarth);
    SunspearNeighbours.add(Valyria);
    SunspearNeighbours.add(Karhold);
    Sunspear.setNeighbourTerritories(SunspearNeighbours);

    List<Territory> ValyriaNeighbours = new ArrayList<> ();
    ValyriaNeighbours.add(Braavos);
    ValyriaNeighbours.add(Pentos);
    ValyriaNeighbours.add(Rainwood);
    ValyriaNeighbours.add(Tarth);
    ValyriaNeighbours.add(Sunspear);
    ValyriaNeighbours.add(Karhold);
    Valyria.setNeighbourTerritories(ValyriaNeighbours);

    List<Territory> KarholdNeighbours = new ArrayList<> ();
    KarholdNeighbours.add(Meereen);
    KarholdNeighbours.add(Norvos);
    KarholdNeighbours.add(Pentos);
    KarholdNeighbours.add(Sunspear);
    KarholdNeighbours.add(Valyria);
    KarholdNeighbours.add(Tyrosh);
    Karhold.setNeighbourTerritories(KarholdNeighbours);

    List<Territory> TyroshNeighbours = new ArrayList<> ();
    TyroshNeighbours.add(Meereen);
    TyroshNeighbours.add(Karhold);
    Tyrosh.setNeighbourTerritories(TyroshNeighbours);
    
    for (Territory territory : territoryList) {
      if (territory.getName() == "Asshai") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < AsshaiNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), AsshaiNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Astapor") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < AstaporNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), AstaporNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Braavos") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < BraavosNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), BraavosNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Meereen") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < MeereenNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), MeereenNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Norvos") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < NorvosNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), NorvosNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Pentos") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < PentosNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), PentosNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Rainwood") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < RainwoodNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), RainwoodNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Tarth") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < TarthNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), TarthNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Sunspear") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < SunspearNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), SunspearNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Valyria") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < ValyriaNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), ValyriaNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Karhold") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < KarholdNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), KarholdNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Tyrosh") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < TyroshNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), TyroshNeighbours.get(i).getName());
        }
      }
    }
  }

  @Test
  public void testFivePlayerMap() {
    V1MapFactory myFactory = new V1MapFactory(); 
    RiscMap myMap = myFactory.createFivePlayerMap();
    List<Territory> territoryList = myMap.getTerritoryList();
    assertEquals(territoryList.size(), 15);
    
    Territory Asshai = new Territory(new Color("Red"), "Asshai");
    Territory Astapor = new Territory(new Color("Red"), "Astapor");
    Territory Braavos = new Territory(new Color("Red"), "Braavos");
    Territory Norvos = new Territory(new Color("Blue"), "Norvos");
    Territory Pentos = new Territory(new Color("Blue"), "Pentos");
    Territory Meereen = new Territory(new Color("Blue"), "Meereen");
    Territory Rainwood = new Territory(new Color("Green"), "Rainwood");
    Territory Tarth = new Territory(new Color("Green"), "Tarth");
    Territory Sunspear = new Territory(new Color("Green"), "Sunspear");
    Territory Valyria = new Territory(new Color("Yellow"), "Valyria");
    Territory Karhold = new Territory(new Color("Yellow"), "Karhold");
    Territory Tyrosh = new Territory(new Color("Yellow"), "Tyrosh");
    Territory Lannisport = new Territory(new Color("Purple"), "Lannisport");
    Territory Eyrie = new Territory(new Color("Purple"), "Eyrie");
    Territory Ashemark = new Territory(new Color("Purple"), "Ashemark");

    List<Territory> AsshaiNeighbours = new ArrayList<> ();
    AsshaiNeighbours.add(Astapor);
    AsshaiNeighbours.add(Braavos);
    Asshai.setNeighbourTerritories(AsshaiNeighbours);

    List<Territory> AstaporNeighbours = new ArrayList<> ();
    AstaporNeighbours.add(Asshai);
    AstaporNeighbours.add(Braavos);
    AstaporNeighbours.add(Meereen);
    AstaporNeighbours.add(Rainwood);
    AstaporNeighbours.add(Valyria);
    Astapor.setNeighbourTerritories(AstaporNeighbours);

    List<Territory> BraavosNeighbours = new ArrayList<> ();
    BraavosNeighbours.add(Asshai);
    BraavosNeighbours.add(Astapor);
    BraavosNeighbours.add(Meereen);
    BraavosNeighbours.add(Norvos);
    Braavos.setNeighbourTerritories(BraavosNeighbours);

    List<Territory> MeereenNeighbours = new ArrayList<> ();
    MeereenNeighbours.add(Astapor);
    MeereenNeighbours.add(Braavos);
    MeereenNeighbours.add(Norvos); 
    MeereenNeighbours.add(Valyria);
    MeereenNeighbours.add(Karhold);
    Meereen.setNeighbourTerritories(MeereenNeighbours);

    List<Territory> NorvosNeighbours = new ArrayList<> ();
    NorvosNeighbours.add(Braavos);
    NorvosNeighbours.add(Meereen);
    NorvosNeighbours.add(Pentos);
    NorvosNeighbours.add(Karhold);
    Norvos.setNeighbourTerritories(NorvosNeighbours);

    List<Territory> PentosNeighbours = new ArrayList<> ();
    PentosNeighbours.add(Norvos);
    PentosNeighbours.add(Karhold);
    PentosNeighbours.add(Tyrosh);
    PentosNeighbours.add(Eyrie);
    Pentos.setNeighbourTerritories(PentosNeighbours);

    List<Territory> RainwoodNeighbours = new ArrayList<> ();
    RainwoodNeighbours.add(Astapor);
    RainwoodNeighbours.add(Tarth);
    RainwoodNeighbours.add(Sunspear);
    RainwoodNeighbours.add(Valyria);
    Rainwood.setNeighbourTerritories(RainwoodNeighbours);

    List<Territory> TarthNeighbours = new ArrayList<> ();
    TarthNeighbours.add(Rainwood);
    TarthNeighbours.add(Sunspear);
    TarthNeighbours.add(Lannisport);
    TarthNeighbours.add(Ashemark);
    Tarth.setNeighbourTerritories(TarthNeighbours);

    List<Territory> SunspearNeighbours = new ArrayList<> ();
    SunspearNeighbours.add(Rainwood);
    SunspearNeighbours.add(Tarth);
    SunspearNeighbours.add(Valyria);
    SunspearNeighbours.add(Tyrosh);
    SunspearNeighbours.add(Lannisport);
    Sunspear.setNeighbourTerritories(SunspearNeighbours);

    List<Territory> ValyriaNeighbours = new ArrayList<> ();
    ValyriaNeighbours.add(Astapor);
    ValyriaNeighbours.add(Meereen);
    ValyriaNeighbours.add(Rainwood);
    ValyriaNeighbours.add(Sunspear);
    ValyriaNeighbours.add(Karhold);
    ValyriaNeighbours.add(Tyrosh);
    Valyria.setNeighbourTerritories(ValyriaNeighbours);

    List<Territory> KarholdNeighbours = new ArrayList<> ();
    KarholdNeighbours.add(Meereen);
    KarholdNeighbours.add(Norvos);
    KarholdNeighbours.add(Pentos);
    KarholdNeighbours.add(Valyria);
    KarholdNeighbours.add(Tyrosh);
    Karhold.setNeighbourTerritories(KarholdNeighbours);

    List<Territory> TyroshNeighbours = new ArrayList<> ();
    TyroshNeighbours.add(Pentos);
    TyroshNeighbours.add(Sunspear);
    TyroshNeighbours.add(Valyria);
    TyroshNeighbours.add(Karhold);
    TyroshNeighbours.add(Lannisport);
    TyroshNeighbours.add(Eyrie);
    Tyrosh.setNeighbourTerritories(TyroshNeighbours);

    List<Territory> LannisportNeighbours = new ArrayList<> ();
    LannisportNeighbours.add(Tarth);
    LannisportNeighbours.add(Sunspear);
    LannisportNeighbours.add(Tyrosh);
    LannisportNeighbours.add(Eyrie);
    LannisportNeighbours.add(Ashemark);
    Lannisport.setNeighbourTerritories(LannisportNeighbours);

    List<Territory> EyrieNeighbours = new ArrayList<> ();
    EyrieNeighbours.add(Pentos);
    EyrieNeighbours.add(Tyrosh);
    EyrieNeighbours.add(Lannisport);
    EyrieNeighbours.add(Ashemark);
    Eyrie.setNeighbourTerritories(EyrieNeighbours);

    List<Territory> AshemarkNeighbours = new ArrayList<> ();
    AshemarkNeighbours.add(Tarth);
    AshemarkNeighbours.add(Lannisport);
    AshemarkNeighbours.add(Eyrie);
    Ashemark.setNeighbourTerritories(AshemarkNeighbours);
    
    
    for (Territory territory : territoryList) {
      if (territory.getName() == "Asshai") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < AsshaiNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), AsshaiNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Astapor") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < AstaporNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), AstaporNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Braavos") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < BraavosNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), BraavosNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Meereen") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < MeereenNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), MeereenNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Norvos") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < NorvosNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), NorvosNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Pentos") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < PentosNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), PentosNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Rainwood") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < RainwoodNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), RainwoodNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Tarth") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < TarthNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), TarthNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Sunspear") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < SunspearNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), SunspearNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Valyria") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < ValyriaNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), ValyriaNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Karhold") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < KarholdNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), KarholdNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Tyrosh") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < TyroshNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), TyroshNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Lannisport") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < LannisportNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), LannisportNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Eyrie") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < EyrieNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), EyrieNeighbours.get(i).getName());
        }
      }
      if (territory.getName() == "Ashemark") {
        List<Territory> neighbourList = territory.getNeighbourTerritories();
        for (int i = 0; i < AshemarkNeighbours.size(); i++) {
          assertEquals(neighbourList.get(i).getName(), AshemarkNeighbours.get(i).getName());
        }
      }
    }
  }

}

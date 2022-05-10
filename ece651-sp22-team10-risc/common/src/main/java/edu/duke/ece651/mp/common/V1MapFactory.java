package edu.duke.ece651.mp.common;
import java.util.ArrayList;
import java.util.List;


/**
 * This class represents an Abstract Factory pattern for RiscMap creation in Version1
 */
public class V1MapFactory {

  /**
   * This makes a RisMap for two players
   */
  public RiscMap createTwoPlayerMap() {
    List<String> rVis = new ArrayList<>();
    rVis.add("Asshai");
    rVis.add("Astapor");
    rVis.add("Braavos");
    rVis.add("Meereen");
    rVis.add("Norvos");
    rVis.add("Pentos");
    Color red = new Color("Red", rVis);
    List<String> bVis = new ArrayList<>();
    bVis.add("Asshai");
    bVis.add("Astapor");
    bVis.add("Braavos");
    bVis.add("Meereen");
    bVis.add("Norvos");
    bVis.add("Pentos");
    Color blue = new Color("Blue", bVis);
    Territory Asshai = new Territory(red, "Asshai", 1);
    Territory Astapor = new Territory(blue, "Astapor", 1);
    Territory Braavos = new Territory(red, "Braavos", 1);
    Territory Meereen = new Territory(blue, "Meereen", 2);
    Territory Norvos = new Territory(red, "Norvos", 2);
    Territory Pentos = new Territory(blue, "Pentos", 1);

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

    List<Territory> territoryList = new ArrayList<> ();
    territoryList.add(Asshai);
    territoryList.add(Astapor);
    territoryList.add(Braavos);
    territoryList.add(Meereen);
    territoryList.add(Norvos);
    territoryList.add(Pentos);

    List<Color> colorList = new ArrayList<> ();
    colorList.add(red);
    colorList.add(blue);
    RiscMap twoPlayerMap = new RiscMap(territoryList, colorList);
    return twoPlayerMap;
  }
  
  /**
   * This makes a RisMap for three players
   */
  public RiscMap createThreePlayerMap() {
    List<String> rVis = new ArrayList<>();
    rVis.add("Asshai");
    rVis.add("Astapor");
    rVis.add("Braavos");
    rVis.add("Meereen");
    rVis.add("Norvos");
    rVis.add("Tarth");
    rVis.add("Rainwood");
    Color red = new Color("Red", rVis);

    List<String> bVis = new ArrayList<>();
    bVis.add("Asshai");
    bVis.add("Astapor");
    bVis.add("Braavos");
    bVis.add("Meereen");
    bVis.add("Norvos");
    bVis.add("Pentos");
    bVis.add("Tarth");
    bVis.add("Sunspear");
    Color blue = new Color("Blue", bVis);

    List<String> gVis = new ArrayList<>();
    gVis.add("Braavos");
    gVis.add("Norvos");
    gVis.add("Pentos");
    gVis.add("Rainwood");
    gVis.add("Tarth");
    gVis.add("Sunspear");
    Color green = new Color("Green", gVis);

    Territory Asshai = new Territory(red, "Asshai", 2);
    Territory Astapor = new Territory(red, "Astapor", 1);
    Territory Braavos = new Territory(red, "Braavos", 2);
    Territory Meereen = new Territory(blue, "Meereen", 2);
    Territory Norvos = new Territory(blue, "Norvos", 1);
    Territory Pentos = new Territory(blue, "Pentos", 2);
    Territory Rainwood = new Territory(green, "Rainwood", 2);
    Territory Tarth = new Territory(green, "Tarth", 2);
    Territory Sunspear = new Territory(green, "Sunspear", 1);

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

    List<Territory> territoryList = new ArrayList<> ();
    territoryList.add(Asshai);
    territoryList.add(Astapor);
    territoryList.add(Braavos);
    territoryList.add(Meereen);
    territoryList.add(Norvos);
    territoryList.add(Pentos);
    territoryList.add(Rainwood);
    territoryList.add(Tarth);
    territoryList.add(Sunspear);

    List<Color> colorList = new ArrayList<> ();
    colorList.add(red);
    colorList.add(blue);
    colorList.add(green);
    RiscMap threePlayerMap = new RiscMap(territoryList, colorList);
    return threePlayerMap;
  }

  /**
   * This makes a RisMap for three players
   */
  public RiscMap createFourPlayerMap() {
    List<String> rVis = new ArrayList<>();
    rVis.add("Asshai");
    rVis.add("Astapor");
    rVis.add("Braavos");
    rVis.add("Meereen");
    rVis.add("Norvos");
    rVis.add("Pentos");
    rVis.add("Valyria");
    rVis.add("Rainwood");
    Color red = new Color("Red", rVis);

    List<String> bVis = new ArrayList<>();
    bVis.add("Astapor");
    bVis.add("Braavos");
    bVis.add("Meereen");
    bVis.add("Norvos");
    bVis.add("Pentos");
    bVis.add("Valyria");
    bVis.add("Karhold");
    bVis.add("Tyrosh");
    Color blue = new Color("Blue", bVis);

    List<String> gVis = new ArrayList<>();
    gVis.add("Braavos");
    gVis.add("Rainwood");
    gVis.add("Tarth");
    gVis.add("Sunspear");
    gVis.add("Valyria");
    gVis.add("Karhold");
    Color green = new Color("Green", gVis);

    List<String> yVis = new ArrayList<>();
    yVis.add("Braavos");
    yVis.add("Rainwood");
    yVis.add("Tarth");
    yVis.add("Sunspear");
    yVis.add("Pentos");
    yVis.add("Norvos");
    yVis.add("Meereen");
    yVis.add("Valyria");
    yVis.add("Karhold");
    yVis.add("Tyrosh");
    Color yellow = new Color("Yellow", yVis);
    
    Territory Asshai = new Territory(red, "Asshai", 1);
    Territory Astapor = new Territory(red, "Astapor", 2);
    Territory Braavos = new Territory(red, "Braavos", 3);
    Territory Meereen = new Territory(blue, "Meereen", 3);
    Territory Norvos = new Territory(blue, "Norvos", 2);
    Territory Pentos = new Territory(blue, "Pentos", 1);
    Territory Rainwood = new Territory(green, "Rainwood", 2);
    Territory Tarth = new Territory(green, "Tarth", 1);
    Territory Sunspear = new Territory(green, "Sunspear", 3);
    Territory Valyria = new Territory(yellow, "Valyria", 2);
    Territory Karhold = new Territory(yellow, "Karhold", 3);
    Territory Tyrosh = new Territory(yellow, "Tyrosh", 1);

    
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
    
    List<Territory> territoryList = new ArrayList<> ();
    territoryList.add(Asshai);
    territoryList.add(Astapor);
    territoryList.add(Braavos);
    territoryList.add(Meereen);
    territoryList.add(Norvos);
    territoryList.add(Pentos);
    territoryList.add(Rainwood);
    territoryList.add(Tarth);
    territoryList.add(Sunspear);
    territoryList.add(Valyria);
    territoryList.add(Karhold);
    territoryList.add(Tyrosh);

    List<Color> colorList = new ArrayList<> ();
    colorList.add(red);
    colorList.add(blue);
    colorList.add(green);
    colorList.add(yellow);
    RiscMap fourPlayerMap = new RiscMap(territoryList, colorList);
    return fourPlayerMap;
  }

  /**
   * This makes a RisMap for three players
   */
  public RiscMap createFivePlayerMap() {
    List<String> rVis = new ArrayList<>();
    rVis.add("Asshai");
    rVis.add("Astapor");
    rVis.add("Braavos");
    rVis.add("Rainwood");
    rVis.add("Norvos");
    rVis.add("Meereen");
    rVis.add("Valyria");
    Color red = new Color("Red", rVis);

    List<String> bVis = new ArrayList<>();
    bVis.add("Astapor");
    bVis.add("Braavos");
    bVis.add("Norvos");
    bVis.add("Meereen");
    bVis.add("Pentos");
    bVis.add("Valyria");
    bVis.add("Karhold");
    bVis.add("Tyrosh");
    bVis.add("Eyrie");
    Color blue = new Color("Blue", bVis);

    List<String> gVis = new ArrayList<>();
    gVis.add("Astapor");
    gVis.add("Valyria");
    gVis.add("Tyrosh");
    gVis.add("Lannisport");
    gVis.add("Ashemark");
    gVis.add("Rainwood");
    gVis.add("Sunspear");
    gVis.add("Tarth");
    Color green = new Color("Green", gVis);

    List<String> yVis = new ArrayList<>();
    yVis.add("Rainwood");
    yVis.add("Sunspear");
    yVis.add("Lannisport");
    yVis.add("Eyrie");
    yVis.add("Pentos");
    yVis.add("Norvos");
    yVis.add("Meereen");
    yVis.add("Astapor");
    yVis.add("Valyria");
    yVis.add("Karhold");
    yVis.add("Tyrosh");
    Color yellow = new Color("Yellow", yVis);

    List<String> pVis = new ArrayList<>();
    pVis.add("Sunspear");
    pVis.add("Tarth");
    pVis.add("Lannisport");
    pVis.add("Eyrie");
    pVis.add("Ashemark");
    pVis.add("Pentos");
    pVis.add("Tyrosh");
    Color purple = new Color("Purple", pVis);
    
    Territory Asshai = new Territory(red, "Asshai", 1);
    Territory Astapor = new Territory(red, "Astapor", 2);
    Territory Braavos = new Territory(red, "Braavos", 3);
    Territory Meereen = new Territory(blue, "Meereen", 1);
    Territory Norvos = new Territory(blue, "Norvos", 2);
    Territory Pentos = new Territory(blue, "Pentos", 3);
    Territory Tarth = new Territory(green, "Tarth", 1);
    Territory Rainwood = new Territory(green, "Rainwood", 2);
    Territory Sunspear = new Territory(green, "Sunspear", 3);
    Territory Valyria = new Territory(yellow, "Valyria", 1);
    Territory Karhold = new Territory(yellow, "Karhold", 2);
    Territory Tyrosh = new Territory(yellow, "Tyrosh", 3);
    Territory Lannisport = new Territory(purple, "Lannisport", 1);
    Territory Eyrie = new Territory(purple, "Eyrie", 2);
    Territory Ashemark = new Territory(purple, "Ashemark", 3);

    
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
    
    List<Territory> territoryList = new ArrayList<> ();
    territoryList.add(Asshai);
    territoryList.add(Astapor);
    territoryList.add(Braavos);
    territoryList.add(Meereen);
    territoryList.add(Norvos);
    territoryList.add(Pentos);
    territoryList.add(Rainwood);
    territoryList.add(Tarth);
    territoryList.add(Sunspear);
    territoryList.add(Valyria);
    territoryList.add(Karhold);
    territoryList.add(Tyrosh);
    territoryList.add(Lannisport);
    territoryList.add(Eyrie);
    territoryList.add(Ashemark);

    List<Color> colorList = new ArrayList<> ();
    colorList.add(red);
    colorList.add(blue);
    colorList.add(green);
    colorList.add(yellow);
    colorList.add(purple);
    RiscMap fivePlayerMap = new RiscMap(territoryList, colorList);
    return fivePlayerMap;
  }
  
}

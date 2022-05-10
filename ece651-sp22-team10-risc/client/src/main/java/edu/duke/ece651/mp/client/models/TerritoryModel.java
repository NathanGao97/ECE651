package edu.duke.ece651.mp.client.models;

import edu.duke.ece651.mp.client.beans.RGBColorProperty;
import edu.duke.ece651.mp.common.*;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/*
* Singleton design pattern:
*   The whole program only maintain one List of Territory Models
*
* */
public class TerritoryModel {

    private static ObservableList<TerritoryModel> territoryModelList = null;

    private final HashMap<String, RGBColor> rgbColorMap = RGBColor.getRgbColorMap();
    private final RGBColorProperty rgbColor;

    private final StringProperty displayName;
    private final String realName;
    private final IntegerProperty size;

    private final List<Unit> unitsOnTerritoryModel;


    private TerritoryModel(Territory territory, boolean visible){
        rgbColor = new RGBColorProperty(rgbColorMap.get(territory.getOwner().getName()));
        realName = territory.getName();
        //fog of war, if territory is invisible, then set the name to ????
        displayName = new SimpleStringProperty(visible ? territory.getName() : "????");
        size = new SimpleIntegerProperty(territory.getSize());

        unitsOnTerritoryModel = new ArrayList<>();
        if(visible){
            unitsOnTerritoryModel.addAll(territory.allyUnits);
            rgbColor.pProperty().set(0);
        }
        else {
            rgbColor.pProperty().set(1);
        }
    }

    /*
    * Get territory model list given the RiscMap at the start
    * Singelton Design Pattern
    * */
    public static List<TerritoryModel> getTerritoryModelList(List<Territory> territoryList, List<String> visibleTerritories){
        System.out.println("getTerritoryModelList calling times");
        if(territoryModelList == null){
            territoryModelList = FXCollections.observableArrayList();
            for(Territory territory : territoryList){
                if(visibleTerritories.contains(territory.getName())){
                    // territory is visible
                    territoryModelList.add(new TerritoryModel(territory, true));
                }
                else {
                    //territory is invisible
                    territoryModelList.add(new TerritoryModel(territory, false));
                }
            }
        }
        return territoryModelList;
    }

//    public static List<TerritoryModel> getTerritoryModelList()  {
//        if(territoryModelList == null){
//            // create two territory by default, later we get the map, we can use updateTerritoryModels(List<Territory> territoryList) to update
//            V1MapFactory mapFactory = new V1MapFactory();
//            return getTerritoryModelList(mapFactory.createTwoPlayerMap().getTerritoryList());
//        }
//        return territoryModelList;
//    }

    public static TerritoryModel getTerritoryModelByName(String name) throws IllegalArgumentException {
        //use real name to get territory model
        for(TerritoryModel tm : territoryModelList){
            if(name != null && name.equals(tm.realName)){
                return tm;
            }
        }

        throw new IllegalArgumentException("Should not get one null territory model");
    }

    public static void updateTerritoryModels(List<Territory> territoryList, List<String> visibleTerritories){
        if(territoryList == null || territoryList.size() == 0){
            System.out.println("The territory list can not be null or empty");

            throw new IllegalArgumentException("The territory list can not be null or empty");
        }
        getTerritoryModelList(territoryList, visibleTerritories);

        for(Territory territory : territoryList){
            //only update territory model that is visible
            TerritoryModel territoryModel = getTerritoryModelByName(territory.getName());
            boolean visible = visibleTerritories.contains(territory.getName());
            territoryModel.updateTerritoryModel(territory, visible);
        }
    }

    //mainly about update the color
    private void updateTerritoryModel(Territory territory, boolean visible){
//        System.out.println("Update current terrtory model");
//        System.out.println("Territory name: " + territory.getName());
//        System.out.println("Territory owner(color): " + territory.getOwner());

        //territory color will be ???? if not visible
        RGBColor territoryColor = rgbColorMap.get(visible ? territory.getOwner().getName() : "????");

        //mainly about update the color and name of the territory model
        rgbColor.updateRGBColorProperty(territoryColor);
        displayName.set(territory.getName());

        //update units of territory
        if(visible){
            unitsOnTerritoryModel.clear();
            unitsOnTerritoryModel.addAll(territory.allyUnits);
            unitsOnTerritoryModel.addAll(territory.spyUnits);
            rgbColor.pProperty().set(0);
        }
        else{
            rgbColor.pProperty().set(1);
        }

    }


    public StringProperty nameProperty() {
        return displayName;
    }

    public IntegerProperty sizeProperty() {
        return size;
    }

//    public UnitTableModel getUnitListModel() {
//        return unitTableModel;
//    }

    public RGBColorProperty getRgbColor() {
        return rgbColor;
    }


    public String getRealName() {
        return realName;
    }

    public List<Unit> getUnitsOnTerritoryModel() {
        return unitsOnTerritoryModel;
    }
}




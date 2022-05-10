package edu.duke.ece651.mp.client.models;


import edu.duke.ece651.mp.common.Color;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/*
 * Singleton design pattern
 * There should be one model in the whole program
 * */
public class ColorModel {
    private static ColorModel colorModel = null;
    private StringProperty name;
    private IntegerProperty techLevel;
    private IntegerProperty foodResource;
    private IntegerProperty sanityResource;

    //hide the constructor
    private ColorModel(){
        name = new SimpleStringProperty();
        techLevel = new SimpleIntegerProperty();
        foodResource = new SimpleIntegerProperty();
        sanityResource = new SimpleIntegerProperty();
        name.set("Red");
        techLevel.set(1);
        foodResource.set(400);
        sanityResource.set(50);
    }
    /*
     * Get color model
     * */
    public static ColorModel getColorModel(){
        if(colorModel == null){
            colorModel = new ColorModel();
        }
        return colorModel;
    }

    public void updateColorModel(Color color){
        if(color != null){
            colorModel.name.set(color.getName());
            colorModel.techLevel.set(color.getTechLevel());
            colorModel.foodResource.set(color.getFoodResource());
            colorModel.sanityResource.set(color.getSanityResource());
        }
    }

    public StringProperty nameProperty() {
        return name;
    }

    public IntegerProperty techLevelProperty() {
        return techLevel;
    }

    public IntegerProperty foodResourceProperty() {
        return foodResource;
    }

    public IntegerProperty sanityResourceProperty() {
        return sanityResource;
    }
}


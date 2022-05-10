package edu.duke.ece651.mp.client.beans;

import edu.duke.ece651.mp.client.models.RGBColor;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class RGBColorProperty {
    private IntegerProperty R;
    private IntegerProperty G;
    private IntegerProperty B;
    private IntegerProperty P;

    public RGBColorProperty(int r, int g, int b){
        R = new SimpleIntegerProperty(r);
        G = new SimpleIntegerProperty(g);
        B = new SimpleIntegerProperty(b);
        P = new SimpleIntegerProperty(0);
    }

    public RGBColorProperty(RGBColor rgbColor){
        R = new SimpleIntegerProperty(rgbColor.getR());
        G = new SimpleIntegerProperty(rgbColor.getG());
        B = new SimpleIntegerProperty(rgbColor.getB());
        P = new SimpleIntegerProperty(0);
    }

    public void updateRGBColorProperty(RGBColor rgbColor){
        R.set(rgbColor.getR());
        G.set(rgbColor.getG());
        B.set(rgbColor.getB());
    }

    public IntegerProperty rProperty() {
        return R;
    }

    public IntegerProperty gProperty() {
        return G;
    }

    public IntegerProperty bProperty() {
        return B;
    }

    public IntegerProperty pProperty() {
        return P;
    }
}

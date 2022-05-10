package edu.duke.ece651.mp.client.models;

import java.util.HashMap;
import java.util.List;

public class RGBColor {

    private static HashMap<String, RGBColor> RGB_COLOR_MAP = null;

    Integer R, G, B;

    private RGBColor(int r, int g, int b) {
        R = r;
        G = g;
        B = b;
    }

    public static HashMap<String, RGBColor> getRgbColorMap(){
        if(RGB_COLOR_MAP == null){
            RGB_COLOR_MAP = new HashMap<>();

            RGB_COLOR_MAP.put("Red", new RGBColor(255, 109, 109));
            RGB_COLOR_MAP.put("Green", new RGBColor(134, 208, 136));
            RGB_COLOR_MAP.put("Purple", new RGBColor(132, 71, 255));
            RGB_COLOR_MAP.put("Blue", new RGBColor(112, 209, 255));
            RGB_COLOR_MAP.put("Yellow", new RGBColor(255, 255, 71));
            RGB_COLOR_MAP.put("Grey", new RGBColor(208, 192, 192));
            RGB_COLOR_MAP.put("????", new RGBColor(208, 192, 192));
        }

        return RGB_COLOR_MAP;
    }

    public Integer getR() {
        return R;
    }

    public Integer getG() {
        return G;
    }

    public Integer getB() {
        return B;
    }
}

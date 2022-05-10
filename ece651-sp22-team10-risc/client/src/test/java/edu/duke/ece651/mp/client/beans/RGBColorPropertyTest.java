package edu.duke.ece651.mp.client.beans;

import edu.duke.ece651.mp.client.models.RGBColor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RGBColorPropertyTest {

    @Test
    void updateRGBColorProperty() {
        RGBColorProperty rgbColorProperty = new RGBColorProperty(1, 2, 3);
        RGBColor rgbColor = RGBColor.getRgbColorMap().get("Red");
        rgbColorProperty.updateRGBColorProperty(rgbColor);
    }

    @Test
    void rProperty() {
        RGBColorProperty rgbColorProperty = new RGBColorProperty(1, 2, 3);
        assertEquals(rgbColorProperty.rProperty().get(), 1);
    }

    @Test
    void gProperty() {
        RGBColorProperty rgbColorProperty = new RGBColorProperty(1, 2, 3);
        assertEquals(rgbColorProperty.gProperty().get(), 2);
    }

    @Test
    void bProperty() {
        RGBColorProperty rgbColorProperty = new RGBColorProperty(1, 2, 3);
        assertEquals(rgbColorProperty.bProperty().get(), 3);
    }
}
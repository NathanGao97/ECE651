package edu.duke.ece651.mp.client.controller;

import javafx.application.Platform;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class UnitTypePopUpHelperTest {

    @Test
    void popUpUnitSelect()  {

        Platform.runLater(()->{

            UnitTypePopUpHelper unitTypePopUpHelper = new UnitTypePopUpHelper();
            try {
                unitTypePopUpHelper.popUpUnitSelect("Asahai", "Astapor");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        WaitForAsyncUtils.waitForFxEvents();

    }
}
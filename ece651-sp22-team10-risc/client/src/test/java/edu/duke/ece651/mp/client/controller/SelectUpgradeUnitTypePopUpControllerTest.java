package edu.duke.ece651.mp.client.controller;

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;



@ExtendWith(ApplicationExtension.class)
class SelectUpgradeUnitTypePopUpControllerTest {

    @Test
    void onUnitClicked() {
        Platform.runLater(()->{
            SelectUpgradeUnitTypePopUpController selectUpgradeUnitTypePopUpController = new SelectUpgradeUnitTypePopUpController();

            ImageView imageView1 = new ImageView();
            imageView1.setId("Chopper");

            ImageView imageView2 = new ImageView();
            imageView2.setId("Nami");

            MouseEvent mouseEvent1 = new MouseEvent(imageView1, null, MouseEvent.MOUSE_CLICKED,
                    0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                    true, true, true, true, true, true, null);

            MouseEvent mouseEvent2 = new MouseEvent(imageView2, null, MouseEvent.MOUSE_CLICKED,
                    0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                    true, true, true, true, true, true, null);


            try {
                selectUpgradeUnitTypePopUpController.onUnitClicked(mouseEvent1);
                selectUpgradeUnitTypePopUpController.onUnitClicked(mouseEvent2);
            } catch (IOException e) {
            }

        });

        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void onUnitClickedSecond() {
        Platform.runLater(()->{
            SelectUpgradeUnitTypePopUpController selectUpgradeUnitTypePopUpController = new SelectUpgradeUnitTypePopUpController();

            ImageView imageView1 = new ImageView();
            imageView1.setId("Chopper");

            MouseEvent mouseEvent1 = new MouseEvent(imageView1, null, MouseEvent.MOUSE_CLICKED,
                    0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                    true, true, true, true, true, true, null);

            MouseEvent mouseEvent2 = new MouseEvent(imageView1, null, MouseEvent.MOUSE_CLICKED,
                    0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                    true, true, true, true, true, true, null);


            try {
                selectUpgradeUnitTypePopUpController.onUnitClicked(mouseEvent1);
                selectUpgradeUnitTypePopUpController.onUnitClicked(mouseEvent2);
            } catch (IOException e) {
            }

        });

        WaitForAsyncUtils.waitForFxEvents();
    }


}
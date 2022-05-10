package edu.duke.ece651.mp.client.controller;

import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.Player;
import edu.duke.ece651.mp.common.Color;
import javafx.application.Platform;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(ApplicationExtension.class)
class UnitUpgradePopUpControllerTest {

    @Start
    public void start(Stage stage) throws IOException {
        UnitUpgradePopUpHelper unitUpgradePopUpHelper = new UnitUpgradePopUpHelper();
        stage = unitUpgradePopUpHelper.popUpUpgrade("Chopper", "Nami");

        Client.player = mock(Player.class);

    }

    @Test
    void onUpgradeButton(FxRobot robot) {

        Platform.runLater(()->{
            when(Client.player.getCurrentColor()).thenReturn(new Color("Red"));
            robot.clickOn("#num_of_units").type(KeyCode.getKeyCode("1"));
            robot.clickOn("#upgradeButton");
        });

        WaitForAsyncUtils.waitForFxEvents();
    }
}
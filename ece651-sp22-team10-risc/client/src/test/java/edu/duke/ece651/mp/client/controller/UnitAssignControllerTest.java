package edu.duke.ece651.mp.client.controller;

import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.Player;
import edu.duke.ece651.mp.common.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class UnitAssignControllerTest {

    private UnitAssignController unitAssignController;
    private Client client = mock(Client.class);
    private Player player = mock(Player.class);
    private V1MapFactory mapFactory = new V1MapFactory();
    private V1UnitFactory unitFactory = new V1UnitFactory();
    private TextField amount;
    private Text remaining;
    private Text error;

    @Start
    private void start(Stage stage){
        unitAssignController = new UnitAssignController();
        Client.player = player;
        amount = new TextField();
        unitAssignController.amount = amount;
        player.currentMap = mapFactory.createTwoPlayerMap();
        player.updateCurrentColor(new Color("Red"));
        player.territoryIndex = 0;
        remaining = new Text();
        unitAssignController.remaining = remaining;
        error = new Text();
        unitAssignController.error = error;
    }

    @Test
    void onAssignButton() {
        amount.setText("3");
        when(player.getCurrentColor()).thenReturn(new Color("Red"));
        Platform.runLater(()->{
            try {
                unitAssignController.onAssignButton(new ActionEvent(new Button(), null));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(player.territoryIndex == 1);
        assertEquals(remaining.getText(), "17");
    }

    @Test
    void onAssignButtonTwoStep() {

        Platform.runLater(()->{
            try {
                amount.setText("3");
                when(player.getCurrentColor()).thenReturn(new Color("Red"));
                unitAssignController.onAssignButton(new ActionEvent(new Button(), null));
                amount.setText("6");
                unitAssignController.init = new Group();
                unitAssignController.wait = new Text();
                unitAssignController.promptLast = new Text();
                Button b = new Button();
                Scene scene = new Scene(b);
                Stage stage = new Stage();
                stage.setScene(scene);
                unitAssignController.onAssignButton(new ActionEvent(b, null));

            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(player.territoryIndex == 1);
        assertEquals(remaining.getText(), "17");
    }


    @Test
    void onAssignButtonException() {
        amount.setText("xml");
        when(player.getCurrentColor()).thenReturn(new Color("Red"));
        Platform.runLater(()->{
            try {
                unitAssignController.onAssignButton(new ActionEvent(new Button(), null));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        WaitForAsyncUtils.waitForFxEvents();

        assertEquals("The input of unit assigment can only be a number.", error.getText());
    }

    @Test
    void onAssignButtonAssignAll() throws IOException {
        amount.setText("20");
        unitAssignController.init = new Group();
        unitAssignController.wait = new Text();
        unitAssignController.promptRemain = new Text();
        when(player.getCurrentColor()).thenReturn(new Color("Red"));
        Platform.runLater(()->{
            try {
                Button b = new Button();
                Scene scene = new Scene(b);
                Stage stage = new Stage();
                stage.setScene(scene);
                unitAssignController.onAssignButton(new ActionEvent(b, null));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
    }


    @Test
    void parseToInt() {
        int res = unitAssignController.parseToInt("3");
        assertTrue(res == 3);
    }

    @Test
    void parseToIntError() {
        assertThrows(IllegalArgumentException.class, ()->unitAssignController.parseToInt("-1"));
    }

    @Test
    void exchangeWithServer() throws IOException, ClassNotFoundException {
        RiscMap map = mapFactory.createTwoPlayerMap();
        player.updateCurrentColor(new Color("Red"));
        when(player.getCurrentColor()).thenReturn(new Color("Red")).thenReturn(new Color("Red")).thenReturn(new Color("Red"));
        unitAssignController.exchangeWithServer(3, map.getTerritoryByName("Asshai"));

        verify(player, times(1)).sendMessage(new Message("placement", new Color("Red"), "Asshai", null, unitFactory.createChopperUnit(new Color("Red"), 3)));

        verify(player, times(1)).recvMessage();
    }
}

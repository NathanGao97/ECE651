package edu.duke.ece651.mp.client.controller;

import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.Player;
import edu.duke.ece651.mp.common.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextInputControlMatchers;
import org.testfx.util.WaitForAsyncUtils;

import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.URL;

import static org.junit.jupiter.api.Assertions.*;

// by extends this class, we can have access to FxRobot
@ExtendWith(ApplicationExtension.class)
class GameStartMenuControllerTest {

    private Stage test_stage;

    public GameStartMenuController gameStartMenuController;

    public Client client = mock(Client.class);

    public Player player = mock(Player.class);

    @Start
    private void start(Stage stage) {
        test_stage = new Stage();
        gameStartMenuController = new GameStartMenuController();
        Client.player = player;
    }

    @Test
    public void newGameButtonTest() {

        Platform.runLater(() -> {

            //mock a button
            Button b = new Button();
            //mock the scene
            Scene scene = new Scene(b);
            //mock the stage
            Stage s = new Stage();
            s.setScene(scene);

            try {
                gameStartMenuController.onNewGameButton(new ActionEvent(b, null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void continueGameButtonTest() throws IOException, ClassNotFoundException {


        when(player.recvMessage()).thenReturn(new Message("continue")).thenReturn(new Message("over"));
        when(player.recvColor()).thenReturn(new Color("red"));
        when(player.recvInteger()).thenReturn(1);
        when(player.recvInteger()).thenReturn(1);

        Platform.runLater(() -> {
            Button b = new Button();

            Pane p = new Pane();

            p.getChildren().add(b);

            Scene scene = new Scene(p);

            test_stage.setScene(scene);
            try {
                gameStartMenuController.onContinueGameButton(new ActionEvent(b, null));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        WaitForAsyncUtils.waitForFxEvents();

        verify(player, times(1)).sendMessage(new Message("continue"));

    }

    /*
    @Test
    void lastlastlast() {
        Platform.runLater(() -> {
            Button b = new Button();

            try {
                gameStartMenuController.onExitGameButton(new ActionEvent(b, null));
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }
     */


}
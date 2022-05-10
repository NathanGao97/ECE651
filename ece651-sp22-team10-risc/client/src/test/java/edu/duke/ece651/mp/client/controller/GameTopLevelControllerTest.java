package edu.duke.ece651.mp.client.controller;

import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.Player;
import edu.duke.ece651.mp.common.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.*;

// by extends this class, we can have access to FxRobot
@ExtendWith(ApplicationExtension.class)
class GameTopLevelControllerTest {

    public GameTopLevelController gameTopLevelController;

    public Client client = mock(Client.class);

    public Player player = mock(Player.class);

    public ComboBox ma_source, ma_dest, ma_unit_type;

    public TextField ma_units;

    public AbstractUnitFactory unitFactory;

    public Text gameTextDisplay;

    public ComboBox up_unit_type;

    public TextField up_units;

    public V1MapFactory mapFactory;

    public ComboBox up_target_type;

    @Start
    private void start(Stage stage) throws IOException {


        gameTopLevelController = new GameTopLevelController();

        Client.player = player;
        player.updateCurrentColor(new Color("Red"));

        ma_source = new ComboBox();
        gameTopLevelController.ma_source = ma_source;

        ma_dest = new ComboBox();
        gameTopLevelController.ma_dest = ma_dest;

        ma_unit_type = new ComboBox();
        gameTopLevelController.ma_unit_type = ma_unit_type;

        ma_units = new TextField();
        gameTopLevelController.ma_units = ma_units;

        unitFactory = new V1UnitFactory();
        gameTopLevelController.unitFactory = unitFactory;

        gameTextDisplay = new Text();
        gameTopLevelController.gameTextDisplay = gameTextDisplay;

        up_unit_type = new ComboBox();
        gameTopLevelController.up_unit_type = up_unit_type;

        up_units = new TextField();
        gameTopLevelController.up_units = up_units;

        mapFactory = new V1MapFactory();

        up_target_type = new ComboBox();

        gameTopLevelController.up_target_type = up_target_type;

        Client.player.currentMap = mapFactory.createTwoPlayerMap();

    }

    @Test
    public void test_sendOnAttackOrMove() throws IOException, ClassNotFoundException {
        ma_source.setValue("Asshai");
        ma_dest.setValue("Meereen");
        ma_unit_type.setValue("Nami");
        ma_units.setText("3");

        when(player.recvMessage()).thenReturn(new Message(null));
        when(player.getCurrentColor()).thenReturn(new Color("Red"));
        player.currentMap = mapFactory.createTwoPlayerMap();

        Platform.runLater(()->{
            try {
                gameTopLevelController.sendOnAttackOrMove("attack");
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        WaitForAsyncUtils.waitForFxEvents();

    }


    @Test
    public void test_onAttackButton() throws IOException, ClassNotFoundException {
        ma_source.setValue("Asshai");
        ma_dest.setValue("Meereen");
        ma_unit_type.setValue("Nami");
        ma_units.setText("3");

        when(player.recvMessage()).thenReturn(new Message(null));
        when(player.getCurrentColor()).thenReturn(new Color("Red"));
        player.currentMap = mapFactory.createTwoPlayerMap();

        Platform.runLater(()->{
            try {
                gameTopLevelController.onAttackButton(new ActionEvent(new Button(), null));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    public void test_onMoveButton() throws IOException, ClassNotFoundException{
        ma_source.setValue("Asshai");
        ma_dest.setValue("Braavos");
        ma_unit_type.setValue("Chopper");
        ma_units.setText("1");
        when(player.getCurrentColor()).thenReturn(new Color("Red"));

        when(player.recvMessage()).thenReturn(new Message(null));
        player.currentMap = mapFactory.createTwoPlayerMap();

        Platform.runLater(()->{
            try {
                gameTopLevelController.onMoveButton(new ActionEvent(new Button(), null));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        WaitForAsyncUtils.waitForFxEvents();
    }


    @Test
    public void test_onUnitUpgrade() throws IOException, ClassNotFoundException {
        up_unit_type.setValue("Chopper");
        up_units.setText("1");
        up_target_type.setValue("Nami");
        gameTextDisplay.setText("Asshai");
        player.updateCurrentColor(new Color("Red"));
        player.currentMap = mapFactory.createTwoPlayerMap();

        when(player.recvMessage()).thenReturn(new Message(null));
        when(player.getCurrentColor()).thenReturn(new Color("Red"));
        Platform.runLater(()->{
            try {
                gameTopLevelController.onUnitUpgrade(new ActionEvent(new Button(), null));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
    }
    @Test
    public void test_onTechUpgradeButton() throws IOException, ClassNotFoundException {
        when(player.getCurrentColor()).thenReturn(new Color("Red"));
        when(Client.player.recvMessage()).thenReturn(new Message(null));
        Platform.runLater(()->{
            try {
                gameTopLevelController.onTechUpgradeButton(new ActionEvent(new Button(), null));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
        verify(Client.player, times(1)).sendMessage(new Message("techUpgrade", new Color("Red")));
    }

//    @Test
//    public void test_onCommitButton() throws IOException, ClassNotFoundException {
//        Client.player.currentColor = new Color("Red");
//        Platform.runLater(()->{
//            try {
//                Button b = new Button();
//                Stage test_stage = new Stage();
//                Pane p = new Pane(b);
//                test_stage.setScene(new Scene(p));
//                gameTopLevelController.onCommitButton(new ActionEvent(b, null));
//            } catch (IOException e) {
//                e.printStackTrace();
//            } catch (ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        });
//        WaitForAsyncUtils.waitForFxEvents();
//        verify(Client.player, times(1)).sendMessage(new Message("commit", new Color("Red")));
//        verify(Client.player, times(1)).recvMessage();
//    }

    @Test
    public void test_onBackButton(){
        Platform.runLater(()->{
            Button b = new Button();
            Scene scene = new Scene(b);
            Stage stage = new Stage();
            stage.setScene(scene);

            try {
                gameTopLevelController.onBackButton(new ActionEvent(b, null));
            } catch (IOException e) {
                e.printStackTrace();
            }

        });
    }

}

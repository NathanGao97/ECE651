package edu.duke.ece651.mp.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import edu.duke.ece651.mp.client.controller.GameTopLevelController;
import edu.duke.ece651.mp.client.controller.PolygonController;
import edu.duke.ece651.mp.client.controller.UnitAssignController;
import edu.duke.ece651.mp.client.controller.loader.GameTopLevelControllerLoader;
import edu.duke.ece651.mp.client.controller.loader.UnitAssignControllerLoader;
import edu.duke.ece651.mp.client.models.TerritoryModel;
import edu.duke.ece651.mp.common.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringReader;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

import javafx.application.Platform;
import javafx.beans.binding.When;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

@ExtendWith(ApplicationExtension.class)
class PlayerTest {

    V1MapFactory mapFactory = new V1MapFactory();
    V1UnitFactory unitFactory = new V1UnitFactory();

    ObjectSenderReceiver objectSenderReceiver;

    Stage test_stage;


    @Start
    private void start(Stage stage) throws IOException {
        objectSenderReceiver = mock(ObjectSenderReceiver.class);
        Client.player = new Player(objectSenderReceiver);
        Client.player.updateCurrentColor(new Color("Red"));
        Client.player.currentMap = mapFactory.createTwoPlayerMap();
        this.test_stage = new Stage();
    }

    @Test
    void performAttackAction() {
        Color c = Client.player.getCurrentColor();
        Message attackMessage = new Message("move", c, "Asshai", "Astapor", unitFactory.createChopperUnit(c, 3));
        Client.player.performAttackAction(attackMessage);
    }

    @Test
    void performMoveAction() {
        Color c = Client.player.getCurrentColor();
        Message moveMessage = new Message("move", c, "Asshai", "Braavos", unitFactory.createChopperUnit(c, 3));
        Client.player.performMoveAction(moveMessage);
    }

    @Test
    void performTechUpgradeAction() {
        Message techUpgradeMessage = new Message("tech upgrade", Client.player.getCurrentColor());
        Client.player.performTechUpgradeAction(techUpgradeMessage);
    }

    @Test
    void performUnitUpgradeActionRecvOK() {

        Platform.runLater(() -> {

            try {
                when(objectSenderReceiver.receiveObject()).thenReturn(new Message(null));
                Color c = Client.player.getCurrentColor();
                Message unitUpgradeMessage = new Message("unit upgrade", c, "Asshai", unitFactory.createChopperUnit(c, 3), "Nami");
                Client.player.performUnitUpgradeAction(unitUpgradeMessage);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void performUnitUpgradeActionRecvError() {

        Platform.runLater(() -> {

            try {
                when(objectSenderReceiver.receiveObject()).thenReturn(new Message("Can not invoke tech upgrade"));
                Color c = Client.player.getCurrentColor();
                Message unitUpgradeMessage = new Message("unit upgrade", c, "Asshai", unitFactory.createChopperUnit(c, 3), "Nami");
                Client.player.performUnitUpgradeAction(unitUpgradeMessage);
            } catch (Exception e) {

                System.out.println(e.getMessage());
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

    @Test
    void onGameLosePhase() {
        //simulate stay there in one round then goame done
        Platform.runLater(() -> {
            //this method is wrong test it later
//            Client.player.onGameLosePhase();
        });

    }

    @Test
    void doSelectGameMapPhase() {
        Platform.runLater(() -> {
            try {
                this.test_stage = new Stage();
                when(objectSenderReceiver.receiveObject()).thenReturn(new Color("Red")).thenReturn(mapFactory.createTwoPlayerMap());
                Client.player.doSelectGameMapPhase(this.test_stage, "map2");
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        WaitForAsyncUtils.waitForFxEvents();
    }

//    @Test
//    void doUnitAssignmentPhase() {
//        Platform.runLater(()->{
//            try {
//                Client.player.doUnitAssignmentPhase(this.test_stage);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
//
//        WaitForAsyncUtils.waitForFxEvents();
//    }

//    @Test
//    void loadGameBaseAfterSelectMap() {
//        Platform.runLater(()->{
//            Client.player.loadGameBaseAfterSelectMap(this.test_stage, mapFactory.createTwoPlayerMap());
//        });
//        WaitForAsyncUtils.waitForFxEvents();
//    }

    @Test
    void getCurrentColor() {
        Color c = Client.player.getCurrentColor();
        assertEquals(c, new Color("Red"));
    }

    @Test
    void updateCurrentColor() {
        Client.player.updateCurrentColor(new Color("Blue"));
        assertEquals(new Color("Blue"), Client.player.getCurrentColor());
    }

    @Test
    void updateCurrentMap() {
        Client.player.updateCurrentMap(mapFactory.createTwoPlayerMap());
    }


    @Test
    void updateTableModel() {



    }

    @Test
    void getSocket() throws IOException {
        ObjectSenderReceiver objectSenderReceiver = mock(ObjectSenderReceiver.class);
        Player p = new Player(objectSenderReceiver);
        assert (p.getSocket() == null);

    }

    @Test
    void sendMessage() throws IOException {
        ObjectSenderReceiver objectSenderReceiver = mock(ObjectSenderReceiver.class);
        Player p = new Player(objectSenderReceiver);
        Message mess = new Message();
        p.sendMessage(mess);
        verify(objectSenderReceiver, times(1)).sendObject(mess);
    }

    @Test
    void recvMessage() throws IOException, ClassNotFoundException {
        ObjectSenderReceiver objectSenderReceiver = mock(ObjectSenderReceiver.class);
        Player p = new Player(objectSenderReceiver);
        Message mess = p.recvMessage();

        verify(objectSenderReceiver, times(1)).receiveObject();
    }

    @Test
    void recvInteger() throws IOException, ClassNotFoundException {
        ObjectSenderReceiver objectSenderReceiver = mock(ObjectSenderReceiver.class);
        Player p = new Player(objectSenderReceiver);
        Integer mess = p.recvInteger();
        verify(objectSenderReceiver, times(1)).receiveObject();
    }

    @Test
    void recvColor() throws IOException, ClassNotFoundException {
        ObjectSenderReceiver objectSenderReceiver = mock(ObjectSenderReceiver.class);
        Player p = new Player(objectSenderReceiver);

        Color mess = p.recvColor();
        verify(objectSenderReceiver, times(1)).receiveObject();
    }

    @Test
    void recvMap() throws IOException, ClassNotFoundException {

        ObjectSenderReceiver objectSenderReceiver = mock(ObjectSenderReceiver.class);
        when(objectSenderReceiver.receiveObject()).thenReturn(mapFactory.createTwoPlayerMap());

        Player p = new Player(objectSenderReceiver);

        Client.player = p;
        p.updateCurrentColor(new Color("Red"));

        RiscMap map = p.recvMap();
        verify(objectSenderReceiver, times(1)).receiveObject();
    }

    @Test
    void sendColor() throws IOException {
        ObjectSenderReceiver objectSenderReceiver = mock(ObjectSenderReceiver.class);
        Player p = new Player(objectSenderReceiver);
        Color c = new Color("Red");
        p.sendColor(c);
        verify(objectSenderReceiver, times(1)).sendObject(c);
    }

    @Test
    void sendMap() throws IOException {
        ObjectSenderReceiver objectSenderReceiver = mock(ObjectSenderReceiver.class);
        Player p = new Player(objectSenderReceiver);
        RiscMap map = mapFactory.createTwoPlayerMap();
        p.sendMap(map);
        verify(objectSenderReceiver, times(1)).sendObject(map);
    }


}


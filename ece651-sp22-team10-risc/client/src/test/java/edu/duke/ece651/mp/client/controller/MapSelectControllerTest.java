package edu.duke.ece651.mp.client.controller;
import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.Player;
import edu.duke.ece651.mp.common.Color;
import edu.duke.ece651.mp.common.Message;
import edu.duke.ece651.mp.common.V1MapFactory;
import javafx.application.Platform;
import javafx.beans.binding.When;
import javafx.scene.Scene;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;


import java.io.IOException;

import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class MapSelectControllerTest {

    private MapSelectController mapSelectController;
    private Client client;

    private Stage my_stage;
    private V1MapFactory mapFactory;


    @Start
    private void start(Stage stage){
        mapFactory = new V1MapFactory();
        mapSelectController = new MapSelectController();

        //mock the client
        client = mock(Client.class);
        //and mock the player in the client
        Client.player = mock(Player.class);
        Client.player.updateCurrentColor(new Color("Red"));
        Client.player.currentMap = mapFactory.createTwoPlayerMap();

        //Client.player.currentColor = new Color("Red");
        try {
            when(Client.player.recvColor()).thenReturn(new Color("Red"));
            when(Client.player.recvMap()).thenReturn(mapFactory.createTwoPlayerMap());
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        my_stage = new Stage();
    }


    @Test
    void onSelectMapRectangle() throws IOException, ClassNotFoundException {
        // mock client receive message and currentMap from server
        when(Client.player.recvMessage()).thenReturn(new Message("selected currentMap"));
        when(Client.player.recvMap()).thenReturn(mapFactory.createTwoPlayerMap());
        when(Client.player.getCurrentColor()).thenReturn(new Color("Red"));


        Platform.runLater(()->{
            Rectangle rectangle = new Rectangle();
            rectangle.setId("map2");

            AnchorPane p =new AnchorPane();
            p.getChildren().add(rectangle);
            Scene scene = new Scene(p, 100, 100);
            my_stage= new Stage();
            my_stage.setScene(scene);
            MouseEvent me = new MouseEvent(rectangle, null, MouseEvent.MOUSE_CLICKED,
                    0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                    true, true, true, true, true, true, null);
            try {
                mapSelectController.onSelectMapRectangle(me);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        WaitForAsyncUtils.waitForFxEvents();

        verify(Client.player).doSelectGameMapPhase(my_stage, "map2");
        AnchorPane anchorPane = (AnchorPane) my_stage.getScene().getRoot();
    }

}
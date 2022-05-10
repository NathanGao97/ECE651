package edu.duke.ece651.mp.client.controller;

import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.Player;
import edu.duke.ece651.mp.client.controller.loader.GameTopLevelControllerLoader;
import edu.duke.ece651.mp.client.controller.loader.PolygonControllerLoader;
import edu.duke.ece651.mp.client.models.RGBColor;
import edu.duke.ece651.mp.client.models.TerritoryModel;
import edu.duke.ece651.mp.client.models.UnitTableModel;
import edu.duke.ece651.mp.common.*;
import javafx.application.Platform;
import javafx.beans.binding.When;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.scene.Group;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TableViewMatchers;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;


@ExtendWith(ApplicationExtension.class)
class PolygonControllerTest {

    private PolygonController polygonController;
    private Text gameTextDisplay;
    private V1MapFactory factory;

    private Stage test_stage;
    private Group territory_group;

    @Start
    private void start(Stage stage) {
        /* factory used to create the currentMap */
        factory = new V1MapFactory();

        /* mock the player and give the mocked player the currentMap */
        Client.player = mock(Player.class);
        Client.player.currentMap = factory.createTwoPlayerMap();

        /* controller needed for this test */
        polygonController = new PolygonController();
        gameTextDisplay = new Text("Asshai");
        polygonController.gameTextDisplay = gameTextDisplay;
        List<String> visibleTerr = new ArrayList<>();
        List<Territory> territoryList = factory.createTwoPlayerMap().getTerritoryList();
        for (Territory territory : territoryList) {
            visibleTerr.add(territory.getName());
        }
        TerritoryModel.getTerritoryModelList(factory.createTwoPlayerMap().getTerritoryList(), visibleTerr);

    }

//    @Test
//    public void test_intialize() {
//        when(Client.player.getCurrentColor()).thenReturn(new Color("Red"));
//
//        polygonController.initialize(null, null);
//    }

    @Test
    public void test_mouseMovedTerritory() {

        Platform.runLater(() -> {
            Polygon p = new Polygon();
            p.setId("Asshai");
            when(Client.player.getCurrentColor()).thenReturn(new Color("Red"));
            polygonController.mouseMovedTerritory(new MouseEvent(p, null, MouseEvent.MOUSE_CLICKED,
                    0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                    true, true, true, true, true, true, null)
            );
        });
    }

    @Test
    public void test_mouseLeavedTerritory() {

        RGBColor.getRgbColorMap();

        Platform.runLater(() -> {
            Polygon p = new Polygon();
            Client.player.currentMap = factory.createTwoPlayerMap();
            when(Client.player.getCurrentColor()).thenReturn(new Color("Red"));
            PolygonControllerLoader polygonControllerLoader = PolygonControllerLoader.getPolygonControllerLoader(2);
            polygonControllerLoader.getController();
            p.setId("Asshai");
            when(Client.player.getCurrentColor()).thenReturn(new Color("Red"));
            polygonController.mouseLeavedTerritory(new MouseEvent(p, null, MouseEvent.MOUSE_CLICKED,
                    0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                    true, true, true, true, true, true, null)
            );
        });
    }


    @Test
    public void mouseClickerTerrTest() {

        Client.player.currentMap.getTerritoryByName("Asshai").allyUnits.add(new BasicUnit(new Color("Red"), "Nami", 10));
        Client.player.currentMap.getTerritoryByName("Asshai").allyUnits.add(new BasicUnit(new Color("Red"), "Nami", 10));
        Client.player.currentMap.getTerritoryByName("Asshai").allyUnits.add(new BasicUnit(new Color("Red"), "Lufu", 100));
        Client.player.currentMap.getTerritoryByName("Asshai").allyUnits.add(new BasicUnit(new Color("Red"), "Lufu", 100));

        GameTopLevelControllerLoader gameTopLevelControllerLoader = GameTopLevelControllerLoader.getGameTopLevelControllerLoader();
        GameTopLevelController gameTopLevelController = gameTopLevelControllerLoader.getController();
        PolygonControllerLoader polygonControllerLoader = PolygonControllerLoader.getPolygonControllerLoader(2);
        UnitTableModel.getUnitTableModel();
        Platform.runLater(() -> {
            test_stage = new Stage();
            Polygon p = new Polygon();
            p.setId("Asshai");

            gameTopLevelController.gameTextDisplay.setText("Asshai");

            Pane pane = new Pane(p);

            Scene scene = new Scene(pane);

            test_stage.setScene(scene);
            Color c = new Color("Red");
            c.visible_territories = new ArrayList<>();
            List<Territory> territoryList = factory.createTwoPlayerMap().getTerritoryList();
            for (Territory territory : territoryList) {
                c.visible_territories.add(territory.getName());
            }
            when(Client.player.getCurrentColor()).thenReturn(c);
            try {
                polygonController.mouseClickedTerritory(new MouseEvent(p, null, MouseEvent.MOUSE_CLICKED,
                        0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                        true, true, true, true, true, true, null)
                );
            } catch (Exception e) {
                e.printStackTrace();
            }

        });


        WaitForAsyncUtils.waitForFxEvents();

        Color c = new Color("Red");
        c.visible_territories = new ArrayList<>();
        List<Territory> territoryList = factory.createTwoPlayerMap().getTerritoryList();
        for (Territory territory : territoryList) {
            c.visible_territories.add(territory.getName());
        }

        System.out.println("Rows in test case : " + UnitTableModel.getUnitTableModel().rowsProperty().size());

    }

//    @Test
//    public void bindPolygonToTerritoryColor(){
//        V1MapFactory mapFactory = new V1MapFactory();
//        polygonController.bindPolygonToTerritoryColor(mapFactory.createThreePlayerMap());
//
//        System.out.println(TerritoryModel.getTerritoryModelList().size());
//    }


}

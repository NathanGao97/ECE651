package edu.duke.ece651.mp.client.controller;


import edu.duke.ece651.mp.client.Client;

import edu.duke.ece651.mp.client.controller.loader.GameStartMenuControllerLoader;
import edu.duke.ece651.mp.client.controller.loader.UnitAssignControllerLoader;
import edu.duke.ece651.mp.common.Color;
import edu.duke.ece651.mp.common.Message;
import edu.duke.ece651.mp.common.RiscMap;
import edu.duke.ece651.mp.common.V1MapFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.concurrent.Task;

import java.io.IOException;

public class MapSelectController {

    @FXML
    public void onSelectMapRectangle(MouseEvent me) throws Exception {
        Object source = me.getSource();
        if(source instanceof Rectangle){
            // user input which type of currentMap
            Rectangle rectangle = (Rectangle) source;
            String rectID = rectangle.getId();
            Stage stage = (Stage) rectangle.getScene().getWindow();
            //the player receive the currentMap from the remote server
            Client.player.doSelectGameMapPhase(stage, rectID);
        }
        else{
            throw new Exception("The mouse event source is not rectangle");
        }
    }

    @FXML
    public void onSelectBackButton(ActionEvent ae){
        /* back to the game start menu */
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        GameStartMenuControllerLoader.getGameTopLevelControllerLoader().loadParentFXMLToStage(stage);
    }

}

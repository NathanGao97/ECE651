package edu.duke.ece651.mp.client.controller;

import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.controller.loader.MapSelectControllerLoader;
import edu.duke.ece651.mp.client.controller.taks.ContinueGameHandlerTask;
import edu.duke.ece651.mp.client.models.HBoxCell;
import edu.duke.ece651.mp.common.Color;
import edu.duke.ece651.mp.common.Message;
import edu.duke.ece651.mp.common.RiscMap;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.checkerframework.checker.units.qual.A;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class GameStartMenuController {

    @FXML
    Button back_from_select;

    private PopUpMessageHelper popUpMessageHelper = new PopUpMessageHelper();



    @FXML
    public void onNewGameButton(ActionEvent ae) throws IOException {
        // load MapChoice.fxml
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
        MapSelectControllerLoader.getMapSelectControllerLoader().loadParentFXMLToStage(stage);
    }

    @FXML
    public void onContinueGameButton(ActionEvent ae) throws IOException, ClassNotFoundException {
        List<Color> playerColors = new ArrayList<>();
        List<Integer> gameIDList = new ArrayList<>();
        List<Integer> gameCapacity = new ArrayList<>();

        Client.player.sendMessage(new Message("continue"));

        /* recv maps and display a list of maps input */
        Message mess = Client.player.recvMessage();

        while ("continue".equals(mess.getType())){
            playerColors.add(Client.player.recvColor());
            gameIDList.add(Client.player.recvInteger());
            gameCapacity.add(Client.player.recvInteger());
            mess = Client.player.recvMessage();
        }

        goToContinueGameScene(ae, playerColors, gameIDList, gameCapacity);
    }

    private void goToContinueGameScene(ActionEvent ae, List<Color> playerColors, List<Integer> gameIDList, List<Integer> gameCapacity){
        Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();

        List<HBoxCell> list = new ArrayList<>();
        /* compose the list of box cell with the list parameter passed in */
        for(int i=0; i<playerColors.size(); i++){
            list.add(new HBoxCell(gameIDList.get(i), gameCapacity.get(i), i, playerColors.get(i)));
        }

        /* add the box data into list view */
        ObservableList<HBoxCell> observableList = FXCollections.observableList(list);
        ListView<HBoxCell> listView = new ListView<>();
        listView.setItems(observableList);

        /* create the  scene needed for the list view*/
        BorderPane layout = new BorderPane();
        layout.setCenter(listView);
        layout.prefWidth(1280);
        layout.prefHeight(920);
        Scene scene = new Scene(layout, 1280, 920);

        /* display the scene */
        stage.setScene(scene);
    }


    @FXML
    public void onExitGameButton(ActionEvent ae) throws IOException{
        Platform.exit();
        System.exit(0);
    }


}

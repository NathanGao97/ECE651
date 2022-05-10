package edu.duke.ece651.mp.client.models;

import edu.duke.ece651.mp.client.controller.taks.ContinueGameHandlerTask;
import edu.duke.ece651.mp.common.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;

public class HBoxCell extends HBox {
    Label gameID;
    public Label color;
    Label gameCapacity;

    Button enter;

    public HBoxCell(Integer gameID, Integer capacity, Integer index, Color gameColor){

        super();
        this.setWidth(1280);
        this.setSpacing(350);

        enter = new Button();
        enter.setId(index.toString());
        enter.setText("Continue...");
        enter.setOnAction(new ContinueGameHandlerTask());
        this.gameID = new Label();
        this.gameID.setText("Game " + gameID.toString());
        this.gameID.setMaxWidth(Double.MAX_VALUE);

        this.color = new Label();
        this.color.setText(gameColor.getName());
        this.color.setMaxWidth(Double.MAX_VALUE);

        gameCapacity = new Label();
        gameCapacity.setText(capacity.toString() + " player currentMap");
        gameCapacity.setMaxWidth(Double.MAX_VALUE);

        this.getChildren().addAll(this.gameID, color, gameCapacity, enter);
    }

}

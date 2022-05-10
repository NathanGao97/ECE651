package edu.duke.ece651.mp.client.controller;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class PopUpMessageHelper {

    public PopUpMessageHelper(){}

    /* pop out a normal message window */
    public Stage popOutMessage(String mess){
        Stage stage = new Stage();
        Label l = new Label(mess);
        l.setFont(Font.font(15));
        Scene s = new Scene(l, 600, 200);
        stage.setScene(s);
        stage.show();
        return stage;
    }

    /* pop out a window that makes the parent window can be accessed only when this new window is closed  */
    public Stage popOutMessage(String mess, ActionEvent ae){
        Stage topStage = (Stage)((Node) ae.getSource()).getScene().getWindow();

        Stage stage = new Stage();
        stage.initOwner(topStage);
        stage.initModality(Modality.WINDOW_MODAL);
        Label l = new Label(mess);
        l.setFont(Font.font(15));
        Scene s = new Scene(l, 600, 200);
        stage.setScene(s);
        stage.show();
        return stage;
    }

    /* pop out a window that makes the parent window can be accessed only when this new window is closed  */
    public Stage popOutMessage(String mess, MouseEvent me){
        Stage topStage = (Stage)((Node) me.getSource()).getScene().getWindow();
        Stage stage = new Stage();
        stage.initOwner(topStage);
        stage.initModality(Modality.WINDOW_MODAL);
        Label l = new Label(mess);
        l.setFont(Font.font(15));
        Scene s = new Scene(l, 600, 200);
        stage.setScene(s);
        stage.show();
        return stage;
    }

    public Stage popOutMessage(String mess, Stage topStage){
        Stage stage = new Stage();
        stage.initOwner(topStage);
        stage.initModality(Modality.WINDOW_MODAL);
        Label l = new Label(mess);
        l.setFont(Font.font(15));
        Scene s = new Scene(l, 600, 200);
        stage.setScene(s);
        stage.show();
        return stage;
    }


}

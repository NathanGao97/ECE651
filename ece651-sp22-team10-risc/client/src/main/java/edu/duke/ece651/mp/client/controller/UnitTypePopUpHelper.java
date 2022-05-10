package edu.duke.ece651.mp.client.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class UnitTypePopUpHelper {

    public Stage popUpUnitSelect(String source_terr, String target_terr) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/unitPopUp.fxml"));
        Parent parent = fxmlLoader.load();
        UnitTypePopUpController moveAttackPopUpController = fxmlLoader.getController();
        moveAttackPopUpController.source_terr.setText(source_terr);
        moveAttackPopUpController.target_terr.setText(target_terr);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        return stage;
    }

}

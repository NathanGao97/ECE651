package edu.duke.ece651.mp.client.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class SelectUpgradeUnitTypePopUpHelper {
    //load fxml for the user to select map

    public Stage popUpSelectUpgrade() throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/selectUnitUpgrade.fxml"));
        Parent parent = fxmlLoader.load();

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        return stage;
    }
}

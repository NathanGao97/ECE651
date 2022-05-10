package edu.duke.ece651.mp.client.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class UnitUpgradePopUpHelper {

    public Stage popUpUpgrade(String source, String dest) throws IOException {
        Stage stage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/unitUpgrade.fxml"));
        Parent parent = fxmlLoader.load();
        UnitUpgradePopUpController unitUpgradeController = fxmlLoader.getController();

        unitUpgradeController.upgrade_source_type_text.setText(source);
        unitUpgradeController.upgrade_target_type_text.setText(dest);

        Image sourceImage = new Image(getClass().getResourceAsStream("/ui/image/" + source + ".png"));
        Image targetImage = new Image(getClass().getResourceAsStream("/ui/image/" + dest + ".png"));

        unitUpgradeController.upgrade_source_type.setImage(sourceImage);
        unitUpgradeController.upgrade_target_type.setImage(targetImage);

        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stage.show();
        return stage;
    }

}

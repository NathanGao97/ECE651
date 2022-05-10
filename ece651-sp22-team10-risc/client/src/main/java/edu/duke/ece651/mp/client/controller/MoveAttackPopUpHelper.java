package edu.duke.ece651.mp.client.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.IOException;

public class MoveAttackPopUpHelper {

    public static Stage moveAttackStage;


    public Stage popUpMoveAttackSelect(String unitType) throws IOException {


        moveAttackStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/ui/moveAttackPopUp.fxml"));
        Parent parent = fxmlLoader.load();
        MoveAttackPopUpController moveAttackPopUpController = fxmlLoader.getController();

        moveAttackPopUpController.unit_type_text_move_attack.setText(unitType);

        System.out.println("/ui/image/" + unitType + ".png");

        Image image = new Image(getClass().getResourceAsStream("/ui/image/" + unitType + ".png"));
        moveAttackPopUpController.move_attack_unit_image.setImage(image);
        Scene scene = new Scene(parent);
        moveAttackStage.setScene(scene);
        moveAttackStage.show();
        return moveAttackStage;
    }

}

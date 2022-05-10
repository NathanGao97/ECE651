package edu.duke.ece651.mp.client.controller;

import edu.duke.ece651.mp.client.MusicFactory;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

import java.io.IOException;


public class UnitTypePopUpController {

    public static String selectedUnitType;

    @FXML Text source_terr;
    @FXML Text target_terr;

    static MediaPlayer unitSound;

    @FXML
    public void onUnitTypeClicked(MouseEvent me) throws IOException {

        Object object =  me.getSource();
        if(!(object instanceof ImageView)) return;
        ImageView imageView = (ImageView) object;
        selectedUnitType = imageView.getId();

        unitSound = MusicFactory.createMediaPlayer(selectedUnitType.toLowerCase());
        unitSound.play();

        MoveAttackPopUpHelper moveAttackPopUpHelper = new MoveAttackPopUpHelper();
        moveAttackPopUpHelper.popUpMoveAttackSelect(selectedUnitType);
    }

}

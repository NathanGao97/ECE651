package edu.duke.ece651.mp.client.controller;

import edu.duke.ece651.mp.client.MusicFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

import java.io.IOException;

public class SelectUpgradeUnitTypePopUpController {
    private static int count = 0;

    public static String[] sourceDestType = new String[2];

    PopUpMessageHelper popUpMessageHelper = new PopUpMessageHelper();

    static MediaPlayer mediaPlayer;

    @FXML
    public void onUnitClicked(MouseEvent me) throws IOException {

        Object source = me.getSource();
        if(!(source instanceof ImageView))    return;
        ImageView imageView = (ImageView) source;

        mediaPlayer = MusicFactory.createMediaPlayer(imageView.getId().toLowerCase());

        if(count == 0){
            sourceDestType[0] = imageView.getId();
        }
        else {
            sourceDestType[1] = imageView.getId();
            if(!sourceDestType[0].equals(sourceDestType[1])){
                //do unit upgrade
                UnitUpgradePopUpHelper unitUpgradePopUpHelper = new UnitUpgradePopUpHelper();
                unitUpgradePopUpHelper.popUpUpgrade(sourceDestType[0], sourceDestType[1]);
            }
            else {
                popUpMessageHelper.popOutMessage("Can not upgrade on the same unit type");
                return;
            }
        }
        count = (count + 1) % 2;
    }

}

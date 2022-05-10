package edu.duke.ece651.mp.client.controller;

import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.MusicFactory;
import edu.duke.ece651.mp.common.Message;
import edu.duke.ece651.mp.common.Unit;
import edu.duke.ece651.mp.common.V1UnitFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;


public class UnitUpgradePopUpController {

    @FXML
    Text upgrade_source_type_text, upgrade_target_type_text;

    @FXML
    ImageView upgrade_source_type, upgrade_target_type;

    @FXML
    TextField num_of_units;

    PopUpMessageHelper popUpMessageHelper = new PopUpMessageHelper();


    V1UnitFactory unitFactory = new V1UnitFactory();

    public void onUpgradeButton() throws IOException, ClassNotFoundException {



        String unit_type = upgrade_source_type_text.getText();
        String target_type = upgrade_target_type_text.getText();

        String units = num_of_units.getText();

        if(unit_type == null || units == null){
            popUpMessageHelper.popOutMessage("The unit type or num of units is null");
            return;
        }

        int units_num = 0;
        try{
            units_num = Integer.parseInt(units);
        }
        catch (Exception e){
            popUpMessageHelper.popOutMessage("The unit num is not a valid integer");
            return;
        }


        String territoryName = PolygonController.sourceDest[0];

        //compose the unit upgrade command
        List<Unit> unitList = unitFactory.createUnitsByType(unit_type, Client.player.getCurrentColor(), units_num);
        Message sendedMessage = new Message("unitUpgrade", Client.player.getCurrentColor(), territoryName, unitList, target_type);
        Client.player.performUnitUpgradeAction(sendedMessage);


    }



}

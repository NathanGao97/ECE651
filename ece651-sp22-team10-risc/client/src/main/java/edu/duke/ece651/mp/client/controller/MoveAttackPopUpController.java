package edu.duke.ece651.mp.client.controller;

import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.MusicFactory;
import edu.duke.ece651.mp.common.Message;
import edu.duke.ece651.mp.common.Unit;
import edu.duke.ece651.mp.common.V1UnitFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;

import java.io.IOException;
import java.util.List;

public class MoveAttackPopUpController {

    @FXML
    ImageView move_attack_unit_image;

    @FXML
    Text unit_type_text_move_attack;

    @FXML
    TextField num_of_units;

    @FXML
    public void onMoveButton() throws IOException, ClassNotFoundException {
        System.out.println("move button");
        sendOnAttackOrMove("move");
        MoveAttackPopUpHelper.moveAttackStage.close();
    }

    @FXML
    public void onAttackButton() throws IOException, ClassNotFoundException {
        System.out.println("attack button");
        sendOnAttackOrMove("attack");
        MoveAttackPopUpHelper.moveAttackStage.close();
    }

    PopUpMessageHelper popUpMessageHelper = new PopUpMessageHelper();
    V1UnitFactory unitFactory = new V1UnitFactory();

    public void sendOnAttackOrMove(String messType) throws IOException, ClassNotFoundException {
        String source = PolygonController.sourceDest[0];
        String dest = PolygonController.sourceDest[1];
        String unit_type = unit_type_text_move_attack.getText();
        String unit_num_str = num_of_units.getText();
        //TODO: add Linked rule checker to check these
        if(source == null || dest == null || unit_type == null || unit_num_str == null){
            //pop up a window
            popUpMessageHelper.popOutMessage("You did not specify the source or dest or unit_type or unit_num!!");
            return;
        }

        int unit_num = 0;
        try{
            unit_num =Integer.parseInt(unit_num_str);
        }
        catch (Exception e){
            //pop up a window
            popUpMessageHelper.popOutMessage("The unit number input should be a number!!");
            return;
        }

        if(unit_num <= 0){
            popUpMessageHelper.popOutMessage("The unit number should be greater than 0!!");
            return;
        }

        //create that amount of units
        List<Unit> units = unitFactory.createUnitsByType(unit_type, Client.player.getCurrentColor(), unit_num);

        Message sendedMessage = new Message(messType, Client.player.getCurrentColor(), source, dest, units, unit_type);

        Client.player.sendMessage(sendedMessage);

        Message status = Client.player.recvMessage();

        updateOnMoveAttackSuccess(status, sendedMessage);

//        clearMoveAttackInput();
    }


    private void updateOnMoveAttackSuccess(Message status, Message sendedMessage){
        if(status.getType() == null){
            popUpMessageHelper.popOutMessage("Order has been sent to the server");
            //do local change
            if("move".equals(sendedMessage.getType())){
                Client.player.performMoveAction(sendedMessage);
            }
            else {
                Client.player.performAttackAction(sendedMessage);
            }
        }
        else {
            popUpMessageHelper.popOutMessage(status.getType());
        }
    }





}

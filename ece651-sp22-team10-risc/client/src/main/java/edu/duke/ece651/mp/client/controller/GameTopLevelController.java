package edu.duke.ece651.mp.client.controller;

import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.controller.taks.RecvMapMessageOnLosePhaseTask;
import edu.duke.ece651.mp.client.controller.taks.onsuccess.RecvMapMessageAfterCommitOnSuccess;
import edu.duke.ece651.mp.client.controller.taks.onsuccess.RecvMapMessageAfterCommitTask;
import edu.duke.ece651.mp.client.models.ColorModel;
import edu.duke.ece651.mp.client.models.UnitTableModel;
import edu.duke.ece651.mp.common.*;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

/* used to reslove controller dependecies */
public class GameTopLevelController implements Initializable{

    @FXML public Text gameTextDisplay, food_resource, tech_resource, tech_level, title;

    @FXML public TableView unitTable;

    @FXML ComboBox ma_source, ma_dest, ma_unit_type, up_unit_type, up_target_type;

    @FXML TextField ma_units, up_units;

    @FXML
    public Group territory_group;

    AbstractUnitFactory unitFactory = new V1UnitFactory();
    PopUpMessageHelper popUpMessageHelper = new PopUpMessageHelper();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ColorModel colorModel = ColorModel.getColorModel();
        title.textProperty().bind(colorModel.nameProperty());
        food_resource.textProperty().bind(colorModel.foodResourceProperty().asString());
        tech_resource.textProperty().bind(colorModel.sanityResourceProperty().asString());
        tech_level.textProperty().bind(colorModel.techLevelProperty().asString());

        //bind the table model with the view
        UnitTableModel unitTableModel = UnitTableModel.getUnitTableModel();
        unitTable.setItems(unitTableModel.rowsProperty());
    }

    public void sendOnAttackOrMove(String messType) throws IOException, ClassNotFoundException {
        String source = (String) ma_source.getValue();
        String dest = (String) ma_dest.getValue();
        String unit_type = (String) ma_unit_type.getValue();
        String unit_num_str = ma_units.getText();
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

        clearMoveAttackInput();
    }

    private void clearMoveAttackInput(){
        ma_source.setValue(null);
        ma_dest.setValue(null);
        ma_units.setText("");
        ma_unit_type.setValue(null);
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

    @FXML
    public void onAttackButton(ActionEvent ae) throws IOException, ClassNotFoundException {
        sendOnAttackOrMove("attack");
    }

    @FXML
    public void onMoveButton(ActionEvent ae) throws IOException, ClassNotFoundException {
        sendOnAttackOrMove("move");
    }

    /*
    * For upgrade, you have to click on the territory and select the unit type
    * number of units to update
    * */
    @FXML
    public void onUnitUpgrade(ActionEvent ae) throws IOException, ClassNotFoundException {
        String unit_type = (String) up_unit_type.getValue();
        String target_type = (String) up_target_type.getValue();

        String units = up_units.getText();
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

        //compose the unit upgrade command
        List<Unit> unitList = unitFactory.createUnitsByType(unit_type, Client.player.getCurrentColor(), units_num);
        Message sendedMessage = new Message("unitUpgrade", Client.player.getCurrentColor(), gameTextDisplay.getText(), unitList, target_type);
        Client.player.performUnitUpgradeAction(sendedMessage);
    }

    @FXML
    public void onTechUpgradeButton(ActionEvent ae) throws IOException, ClassNotFoundException {
        Message sendedMessage = new Message("techUpgrade", Client.player.getCurrentColor());
        Client.player.sendMessage(sendedMessage);

        Message status = Client.player.recvMessage();
        if(status.getType() == null){
            popUpMessageHelper.popOutMessage("Upgrade tech level successfully");
            Client.player.performTechUpgradeAction(sendedMessage);
        }
        else{
            popUpMessageHelper.popOutMessage(status.getType());
        }
    }

    @FXML
    public void onCommitButton(ActionEvent ae) throws IOException, ClassNotFoundException {
        Client.player.sendMessage(new Message("commit", Client.player.getCurrentColor()));
        List<Message> messageList = Client.player.recvListMessage();
        doEffectsAfterRecvMessageList(messageList);

        waitingAfterCommit(ae);
    }

    private void waitingAfterCommit(ActionEvent ae) throws IOException, ClassNotFoundException {
        //set the old stage as
        Stage topStage = (Stage)((Node) ae.getSource()).getScene().getWindow();

        Stage popUpStage = popUpMessageHelper.popOutMessage("Wait other players to finish ... ", topStage);
        popUpStage.setOnCloseRequest(event -> event.consume());

        // receive map and message Async
        Task task = new RecvMapMessageAfterCommitTask(Client.player);

        //check the received message (win, lose ....) and do things correspondingly
        task.setOnSucceeded(new RecvMapMessageAfterCommitOnSuccess(task, topStage, popUpStage));

        new Thread(task).start();
    }

    private void doEffectsAfterRecvMessageList(List<Message> messageList){
        String popUpMess = "";
        for(Message message : messageList){
            popUpMess += message.getOwner() + " player triggered " + message.getType() + "\n";
        }
        popUpMessageHelper.popOutMessage(popUpMess);
    }

    @FXML
    public void onBackButton(ActionEvent ae) throws IOException {

        Client.player.sendMessage(new Message("exit"));

        Stage topStage = (Stage)((Node) ae.getSource()).getScene().getWindow();

        goToGameStatusMenu(topStage);
    }

    private void goToGameStatusMenu(Stage stage) throws IOException {
        GridPane gameStartMenu = FXMLLoader.load(getClass().getResource("/ui/game_start_menu.fxml"));
        Scene scene = new Scene(gameStartMenu);
        stage.setScene(scene);
    }

    @FXML
    public void onResearchCloak() throws IOException, ClassNotFoundException {

        Message sendedMessage = new Message("researchCloak", Client.player.getCurrentColor());
        Client.player.sendMessage(sendedMessage);

        Message status = Client.player.recvMessage();
        if(status.getType() == null){
            popUpMessageHelper.popOutMessage("Cloak created");
            GetCloakActioner getCloakActioner = new GetCloakActioner();
            getCloakActioner.performAction(sendedMessage, Client.player.currentMap);
            Client.player.updateCurrentMap(Client.player.currentMap);
        }
        else{
            popUpMessageHelper.popOutMessage(status.getType());
        }
    }

    @FXML
    public void onCloakButton() throws IOException {

        String territoryName = gameTextDisplay.getText();

        //compose the unit upgrade command
        Message sendedMessage = new Message("cloak", Client.player.getCurrentColor(), territoryName);

        Client.player.sendMessage(sendedMessage);

    }

}

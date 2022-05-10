package edu.duke.ece651.mp.client.controller;

import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.common.Message;
import edu.duke.ece651.mp.common.RiscMap;
import edu.duke.ece651.mp.common.Territory;
import edu.duke.ece651.mp.common.V1UnitFactory;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class UnitAssignController {

    @FXML
    Group init;

    @FXML
    public
    Text wait;

    @FXML
    public
    Text promptRemain;

    @FXML
    public
    Text promptLast;

    @FXML
    Text error;

    @FXML
    Text remaining;

    @FXML
    public
    Text territoryName;

    @FXML
    TextField amount;

    private int initialUnitNum = 20;

    private V1UnitFactory unitFactory = new V1UnitFactory();


    @FXML
    public void onAssignButton(ActionEvent ae) throws IOException, ClassNotFoundException{
        // Get the assign number.
        String assign_str = amount.getText();
        List<Territory> ownedTerritory = Client.player.currentMap.getTerritoryListOwnedByColor(Client.player.getCurrentColor());
        amount.clear();
        int assign_int = 0;
        try {
            assign_int = parseToInt(assign_str);
        }
        catch(Exception e) {
            error.setText("The input of unit assigment can only be a number.");
            return;
        }

        // Get the current territory.
        Territory curTerr = ownedTerritory.get(Client.player.territoryIndex);

        if (Client.player.territoryIndex == 0) {
            exchangeWithServer(assign_int, curTerr);
            remaining.setText(String.valueOf(initialUnitNum));
            if (initialUnitNum == 0) {

                init.setVisible(false);
                wait.setVisible(true);
                promptRemain.setVisible(true);

                //end, recvMap and render the data
                Task task = new Task() {
                    @Override
                    protected Void call() throws Exception {
                        RiscMap map = Client.player.recvMap();
                        Client.player.recvMessage();
                        return null;
                    }
                };

                new Thread(task).start();
                task.setOnSucceeded(taskFinishEvent->{
                    Stage currStage = (Stage)((Node) ae.getSource()).getScene().getWindow();
                    currStage.close();
                });

                return ;
            }

            Client.player.territoryIndex++;
        }
        else if (Client.player.territoryIndex == 1) {
            exchangeWithServer(assign_int, curTerr);
            if(initialUnitNum != 0){
                exchangeWithServer(initialUnitNum, ownedTerritory.get(2));
            }
            init.setVisible(false);
            wait.setVisible(true);
            promptLast.setVisible(true);

            //end, render the map and begin the order phase
            Task task = new Task() {
                @Override
                protected Void call() throws Exception {
                    RiscMap map = Client.player.recvMap();
                    Client.player.recvMessage();
                    return null;
                }
            };

            new Thread(task).start();
            task.setOnSucceeded(taskFinishEvent->{

                Stage currStage = (Stage)((Node) ae.getSource()).getScene().getWindow();
                currStage.close();
            });

            return;
        }
    }

    public int parseToInt(String assign_str) throws IllegalArgumentException, NullPointerException, NumberFormatException{
        int assign_int = Integer.parseInt(assign_str);
        if (assign_int > initialUnitNum || assign_int < 0) {
            throw new IllegalArgumentException("Input number is invalid");
        }
        initialUnitNum -= assign_int;
        return assign_int;
    }

    /*
    * send mess and recv message
    * */
    public void exchangeWithServer(int assign_num, Territory terr) throws IOException, ClassNotFoundException{
//        String status = "ready to send.";

        // send the placement message to the server
        Message mess = new Message("placement", Client.player.getCurrentColor(), terr.getName(), null, unitFactory.createChopperUnit(Client.player.getCurrentColor(), assign_num));
        Client.player.sendMessage(mess);
        // wait the server to send back
        Message response_mess = Client.player.recvMessage();

    }


}

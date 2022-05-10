package edu.duke.ece651.mp.client.controller.taks;

import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.controller.GameStartMenuController;
import edu.duke.ece651.mp.client.controller.GameTopLevelController;
import edu.duke.ece651.mp.client.controller.PopUpMessageHelper;
import edu.duke.ece651.mp.client.controller.loader.GameStartMenuControllerLoader;
import edu.duke.ece651.mp.client.models.HBoxCell;
import edu.duke.ece651.mp.common.Color;
import edu.duke.ece651.mp.common.Message;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class ContinueGameHandlerTask implements EventHandler<ActionEvent> {

    PopUpMessageHelper popUpMessageHelper = new PopUpMessageHelper();

    @Override
    public void handle(ActionEvent ae) {
        Button b = (Button) ae.getSource();
        HBoxCell hBoxCell = (HBoxCell) b.getParent();

        try {
            //send game index
            Client.player.updateCurrentColor(new Color(hBoxCell.color.getText()));
            Client.player.sendMessage(new Message(b.getId()));

            Stage popStage = popUpMessageHelper.popOutMessage("Waiting for server response...", ae);
            popStage.setOnCloseRequest(event1 -> event1.consume());
            Message conMess = Client.player.recvMessage();
            System.out.println(conMess.getType());

            if ("ok".equals(conMess.getType())) {
                Message stageMess = Client.player.recvMessage();
                Stage contineGameStage = (Stage) ((Node) ae.getSource()).getScene().getWindow();

                if ("placement".equals(stageMess.getType())) {
                    //do placement phase
                    doPlacementAfterContinueGame(contineGameStage, popStage);
                } else {
                    // do game phase
                    doGamePhaseAfterContinueGame(contineGameStage, popStage, ae);
                }

            } else {
                if ("fail".equals(conMess.getType())) {
                    doFailPhaseAfterContinueGame();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doFailPhaseAfterContinueGame(){
        popUpMessageHelper.popOutMessage("Can not continue with this game");
        try {
            GameStartMenuControllerLoader gameStartMenuControllerLoader = GameStartMenuControllerLoader.getGameTopLevelControllerLoader();
            GameStartMenuController gameStartMenuController = gameStartMenuControllerLoader.getController();
            gameStartMenuController.onContinueGameButton(new ActionEvent(new Button(), null));
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    private void doPlacementAfterContinueGame(Stage contineGameStage, Stage popStage) throws IOException, ClassNotFoundException {
        Client.player.recvColor();
        Client.player.recvMap();
        Client.player.loadGameBaseAfterSelectMap(contineGameStage, Client.player.currentMap);

        //close the pop up
        popStage.close();

        // go to unit assignment phase
        Client.player.doUnitAssignmentPhase(contineGameStage);
    }

    private void doGamePhaseAfterContinueGame(Stage contineGameStage, Stage popStage, ActionEvent ae){
        Task task = new Task() {
            @Override
            protected Void call() throws Exception {
                Client.player.recvMap();
                return null;
            }
        };
        new Thread(task).start();
        task.setOnSucceeded(taskFinishEvent -> {
            Client.player.loadGameBaseAfterSelectMap(contineGameStage, Client.player.currentMap);
            popStage.close();

            try {
                waitingAfterCommit(ae);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }


    private void waitingAfterCommit(ActionEvent ae) throws IOException, ClassNotFoundException {
        //set the old stage as
        Stage waitStage = popUpMessageHelper.popOutMessage("Wait other players to finish ... ", ae);
        waitStage.setOnCloseRequest(event -> event.consume());

        Task task = new Task() {
            @Override
            protected Message call() throws Exception {
                Client.player.currentMap = Client.player.recvMap();
                Message status = Client.player.recvMessage();
                return status;
            }
        };

        new Thread(task).start();
        task.setOnSucceeded(taskEventFinish -> {
            /* load the received map */
            Stage stage = (Stage) ((Node) ae.getSource()).getScene().getWindow();
            GameTopLevelController gameTopLevelController = null;

            waitStage.close();
        });

    }

}

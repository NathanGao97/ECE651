package edu.duke.ece651.mp.client.controller.taks.onsuccess;

import edu.duke.ece651.mp.client.Player;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.io.IOException;

public class RecvColorMapOnSuccess implements EventHandler<WorkerStateEvent> {

    private Stage mainStage;
    private Stage popUpStage;

    private Player player;

    public RecvColorMapOnSuccess(Player player, Stage mainStage, Stage popUpStage){
        this.player = player;
        this.mainStage = mainStage;
        this.popUpStage = popUpStage;
    }

    @Override
    public void handle(WorkerStateEvent event) {
        /* load the received map */
        System.out.println("Load game and territory after select map");
        player.loadGameBaseAfterSelectMap(mainStage, player.currentMap);

        //close the pop up
        popUpStage.close();

        // go to unit assignment phase
        try {
            player.doUnitAssignmentPhase(mainStage);
        } catch (IOException e) {
            e.printStackTrace();
        }
        
    }
}

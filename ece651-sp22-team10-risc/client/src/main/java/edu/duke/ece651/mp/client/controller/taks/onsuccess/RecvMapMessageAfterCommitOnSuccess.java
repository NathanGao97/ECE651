package edu.duke.ece651.mp.client.controller.taks.onsuccess;

import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.Player;
import edu.duke.ece651.mp.client.controller.PopUpMessageHelper;
import edu.duke.ece651.mp.common.Message;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.io.IOException;

/*
*
* */
public class RecvMapMessageAfterCommitOnSuccess implements EventHandler<WorkerStateEvent> {
    private static PopUpMessageHelper popUpMessageHelper = new PopUpMessageHelper();
    Stage popUpStage;
    Task callingTask;
    Stage topStage;
    public RecvMapMessageAfterCommitOnSuccess(Task callingTask, Stage topStage, Stage popUpStage){
        this.callingTask = callingTask;
        this.popUpStage = popUpStage;
        this.topStage = topStage;
    }

    @Override
    public void handle(WorkerStateEvent event) {

        popUpStage.close();
        Message message = (Message) callingTask.getValue();

        if("lose".equals(message.getType())){
            popUpMessageHelper.popOutMessage("You lose, you can close this window and stay or just leave buy close this game");
            Client.player.onGameLosePhase();
        }
        else if("done".equals(message.getType())){
            popUpMessageHelper.popOutMessage("The game has finished", topStage);
        }
        else if("win".equals(message.getType())){
            popUpMessageHelper.popOutMessage("You win ...", topStage);
        }
        else {
            System.out.println("continue game");
        }

    }
}

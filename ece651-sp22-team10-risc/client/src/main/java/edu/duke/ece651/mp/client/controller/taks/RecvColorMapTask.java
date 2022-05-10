package edu.duke.ece651.mp.client.controller.taks;

import edu.duke.ece651.mp.client.Player;
import edu.duke.ece651.mp.common.RiscMap;
import javafx.concurrent.Task;

import java.util.concurrent.Callable;

public class RecvColorMapTask extends Task {

    Player player;

    public RecvColorMapTask(Player player){
        super();
        this.player = player;
    }

    @Override
    public Void call() throws Exception {
        System.out.println("Thread started running");

        player.recvColor();
        System.out.println("test exception throw");
        player.recvMap();

        System.out.println("Received color and map");
        return null;
    }

}

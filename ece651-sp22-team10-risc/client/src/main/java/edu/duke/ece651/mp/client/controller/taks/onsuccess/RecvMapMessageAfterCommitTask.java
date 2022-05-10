package edu.duke.ece651.mp.client.controller.taks.onsuccess;

import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.Player;
import edu.duke.ece651.mp.common.Message;
import javafx.concurrent.Task;

public class RecvMapMessageAfterCommitTask extends Task {
    Player player;

    public RecvMapMessageAfterCommitTask(Player player){
        super();
        this.player = player;
    }

    @Override
    public Message call() throws Exception {

        Client.player.currentMap = Client.player.recvMap();
        Message status = Client.player.recvMessage();
        return status;
    }

}

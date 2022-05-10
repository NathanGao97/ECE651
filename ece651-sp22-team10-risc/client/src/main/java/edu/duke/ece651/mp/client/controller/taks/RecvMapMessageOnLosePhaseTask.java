package edu.duke.ece651.mp.client.controller.taks;

import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.Player;
import edu.duke.ece651.mp.common.Message;
import javafx.concurrent.Task;


/*
* Receive map and message from server, return the message when finished
* */
public class RecvMapMessageOnLosePhaseTask extends Task {

    Player player;

    public RecvMapMessageOnLosePhaseTask(Player player){
        super();
        System.out.println("create task");
        this.player = player;
    }

    @Override
    public Message call() throws Exception {

        System.out.println("Lose game, send commit to server");

        Message message = new Message("commit", Client.player.getCurrentColor());

        Client.player.sendMessage(message);

        System.out.println(message);

        System.out.println("Receive mess");
        Client.player.recvMessage();
        System.out.println("Received mess");

        System.out.println("Receive map");
        Client.player.currentMap = Client.player.recvMap();
        System.out.println("Received map");

        System.out.println("Receive message");
        Message status = Client.player.recvMessage();
        System.out.println("Received message");
        return status;
    }
}

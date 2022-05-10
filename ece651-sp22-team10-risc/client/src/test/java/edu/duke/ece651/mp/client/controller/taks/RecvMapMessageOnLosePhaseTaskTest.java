package edu.duke.ece651.mp.client.controller.taks;

import static org.junit.jupiter.api.Assertions.*;
import edu.duke.ece651.mp.common.*;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;

import edu.duke.ece651.mp.client.Player;

import static org.mockito.Mockito.*;

public class RecvMapMessageOnLosePhaseTaskTest{
  public Player player = mock(Player.class);
  
  @Test
  public void testCall() throws Exception {
   RecvMapMessageOnLosePhaseTask recvController = new RecvMapMessageOnLosePhaseTask(player);
   Color red = new Color("red");
   when(player.recvMessage()).thenReturn(new Message("recv", red));
   V1MapFactory myFactory = new V1MapFactory(); 
   when(player.recvMap()).thenReturn(myFactory.createTwoPlayerMap());
   when(player.recvMessage()).thenReturn(new Message("recv", red));
   Platform.runLater(()->{
       try{
       recvController.call();
       }
       catch(Exception e) {}
     });
  }
}

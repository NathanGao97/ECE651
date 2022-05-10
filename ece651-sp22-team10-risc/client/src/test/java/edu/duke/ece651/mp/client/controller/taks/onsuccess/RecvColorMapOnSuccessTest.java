package edu.duke.ece651.mp.client.controller.taks.onsuccess;

import edu.duke.ece651.mp.client.controller.taks.*;
import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.Player;
import edu.duke.ece651.mp.common.*;
import javafx.application.Platform;
import javafx.stage.Stage;
import javafx.concurrent.Worker;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import java.io.IOException;
import static org.mockito.Mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

@ExtendWith(ApplicationExtension.class)
public class RecvColorMapOnSuccessTest {
  public RecvColorMapOnSuccess recvController;
  public Player player = mock(Player.class);
  Stage mainStage;
  Stage popUpStage;
  
  @Start
   private void start(Stage stage) throws IOException {
    
    V1MapFactory myFactory = new V1MapFactory(); 
    RiscMap myMap = myFactory.createTwoPlayerMap();
    mainStage = new Stage();
    popUpStage = new Stage();
    player.currentMap = myMap;
    recvController = new RecvColorMapOnSuccess(player, mainStage, popUpStage);  
  }

  @Test
  public void testHandle() {
    Task task = new RecvColorMapTask(player);
    task.setOnSucceeded(new RecvColorMapOnSuccess(Client.player, mainStage, popUpStage));
    new Thread(task).start();
    Platform.runLater(()->{
        recvController.handle(new WorkerStateEvent(task, null));
      });
  }
}

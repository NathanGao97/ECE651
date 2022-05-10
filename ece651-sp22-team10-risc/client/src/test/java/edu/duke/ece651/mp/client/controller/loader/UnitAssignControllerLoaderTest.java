package edu.duke.ece651.mp.client.controller.loader;

import javafx.stage.Stage;
import static org.junit.jupiter.api.Assertions.*;
 
import org.junit.jupiter.api.Test;
import javafx.application.Platform;

public class UnitAssignControllerLoaderTest {
  @Test
  public void testUnitAssignController() {

    Platform.runLater(()->{
        Stage stage = new Stage();
        UnitAssignControllerLoader.getUnitAssignControllerLoader();
      });
  }
}

package edu.duke.ece651.mp.client.controller.loader;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import javafx.stage.Stage;
import javafx.application.Platform;

public class PolygonControllerLoaderTest {
  @Test
  public void testPolygonController() {
     Platform.runLater(()->{
        Stage stage = new Stage();
        PolygonControllerLoader.getPolygonControllerLoader(1);
      });
  }

}

package edu.duke.ece651.mp.client.controller.loader;

import static org.junit.jupiter.api.Assertions.*;
import javafx.stage.Stage;

import org.junit.jupiter.api.Test;
import javafx.application.Platform;

public class LoginRegisterControllerLoaderTest {
  @Test
  public void testLoginRegister() {
    Platform.runLater(()->{
        Stage stage = new Stage();
        LoginRegisterControllerLoader.getLoginRegisterControllerLoader();
      });
  }

}

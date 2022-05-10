package edu.duke.ece651.mp.client.controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import jdk.jfr.Timestamp;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class PopUpMessageHelperTest {

    private PopUpMessageHelper popUpMessageHelper;

    @Start
    private void start(Stage stage){
        popUpMessageHelper = new PopUpMessageHelper();

    }

    @Test
    public void test_pop() {
        Platform.runLater(()->{
            Button b = new Button();
            Stage stage = new Stage();
            stage.setScene(new Scene(b));
            popUpMessageHelper.popOutMessage("mess", new ActionEvent(b, null));
        });
    }

  @Test
  public void test_popMouse() {
        Platform.runLater(()->{
            Button b = new Button();
            Stage stage = new Stage();
            stage.setScene(new Scene(b));
            popUpMessageHelper.popOutMessage("mess", new ActionEvent(b, null));
        });
  }
}

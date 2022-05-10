package edu.duke.ece651.mp.client.controller;

import static org.junit.jupiter.api.Assertions.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.Player;
import edu.duke.ece651.mp.common.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.scene.layout.Pane;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.matcher.control.TextInputControlMatchers;
import org.testfx.util.WaitForAsyncUtils;
import java.io.IOException;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import javafx.application.Platform;


@ExtendWith(ApplicationExtension.class)
public class SelectUpgradeUnitTypePopUpHelperTest {
  
    public SelectUpgradeUnitTypePopUpHelper helper;

    @Start
    private void start(Stage stage) {
      helper = new SelectUpgradeUnitTypePopUpHelper();
    }

  @Test
  public void test_PopUpSelectUpgrade() {
    Platform.runLater(() -> {
         try {
           helper.popUpSelectUpgrade();
            } catch (IOException e) {
                e.printStackTrace();
            }
      });
  }
}

package edu.duke.ece651.mp.client.controller;

import static org.junit.jupiter.api.Assertions.*;

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

import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.util.WaitForAsyncUtils;
import javafx.scene.text.Text;
import javafx.stage.Stage;


import java.io.IOException;

import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.net.URL;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
public class UnitTypePopUpControllerTest {
  public UnitTypePopUpController controller;
  public Text source_terr;
  public Text target_terr;
  
   @Start
   private void start(Stage stage) {
     controller = new UnitTypePopUpController();
     source_terr = new Text();
     target_terr = new Text();
     controller.source_terr = source_terr;
     controller.target_terr = target_terr;
     
   }

  @Test
  public void testOnUnitTypeClicked() {
    Platform.runLater(()->{
          ImageView imageView = new ImageView();
            imageView.setId("Nami");

            MouseEvent mouseEvent1 = new MouseEvent(imageView, null, MouseEvent.MOUSE_CLICKED,
                    0, 0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                    true, true, true, true, true, true, null);
            try{
              controller.onUnitTypeClicked(mouseEvent1);
            }
            catch(Exception e) {}
      });
  }
}

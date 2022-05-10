package edu.duke.ece651.mp.client.controller;

import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.Player;
import edu.duke.ece651.mp.common.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxAssert;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;
import org.testfx.util.WaitForAsyncUtils;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(ApplicationExtension.class)
class LoginRegisterControllerTest {

    private Stage test_stage;
    private Player player;
    private TextField username;
    private PasswordField password;
    private LoginRegisterController loginRegisterController;
    private PasswordField reEnterPassword;
    private Text loginError;
    private Group loginGroup;
    private Group registerGroup;


    @Start
    private void start(Stage stage){
        test_stage = new Stage();
        player = mock(Player.class);
        Client.player = player;
        loginRegisterController = new LoginRegisterController();
        username = new TextField();
        password = new PasswordField();
        reEnterPassword = new PasswordField();
        loginError = new Text();
        loginGroup = new Group();
        registerGroup = new Group();
        loginRegisterController.username = username;
        loginRegisterController.password = password;
        loginRegisterController.reEnterPassword = reEnterPassword;
        loginRegisterController.loginError = loginError;
        loginRegisterController.loginGroup = loginGroup;
        loginRegisterController.registerGroup = registerGroup;
    }


    @Test
    void onLoginButtonTest() throws IOException, ClassNotFoundException {
        when(player.recvMessage()).thenReturn(new Message("ok"));
        Platform.runLater(()->{
            Button b = new Button();

            Pane p = new Pane();

            p.getChildren().add(b);
            username.setText("username");
            password.setText("password");

            Scene scene = new Scene(p);

            test_stage.setScene(scene);

            try {
                loginRegisterController.onLoginButton(new ActionEvent(b, null));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        WaitForAsyncUtils.waitForFxEvents();

        verify(player, times(1)).sendMessage(new Message("login"));
        verify(player, times(1)).sendMessage(new Message(username.getText()));
        verify(player, times(1)).sendMessage(new Message(password.getText()));

    }

    @Test
    void onSignUpButtonTest() {
        Platform.runLater(()->{
            Button b = new Button();
            try {
                loginRegisterController.onSignUpButton(new ActionEvent(b, null));
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

        WaitForAsyncUtils.waitForFxEvents();
        assertTrue(username.getText().length() == 0);
        assertTrue(password.getText().length() == 0);
        assertTrue(reEnterPassword.getText().length() == 0);

        assertTrue(!loginError.visibleProperty().getValue());
        assertTrue(!loginGroup.visibleProperty().getValue());
        assertTrue(registerGroup.visibleProperty().getValue());
    }

    @Test
    void onReturnButtonTest() {
        Platform.runLater(()->{
            Button b = new Button();

            loginRegisterController.onReturnButton(new ActionEvent(b, null));

        });

        WaitForAsyncUtils.waitForFxEvents();

        assertTrue(username.getText().length() == 0);
        assertTrue(password.getText().length() == 0);
        assertTrue(!loginError.visibleProperty().getValue());
        assertTrue(loginGroup.visibleProperty().getValue());
        assertTrue(!registerGroup.visibleProperty().getValue());

    }

    @Test
    void onSubmitButtonTest() throws IOException, ClassNotFoundException {

        when(player.recvMessage()).thenReturn(new Message("ok"));

        Platform.runLater(()->{
            Button b = new Button();
            Pane p = new Pane(b);
            Scene scene = new Scene(p);
            test_stage.setScene(scene);
            username.setText("username");
            password.setText("password");
            reEnterPassword.setText("password");
            System.out.println("Scene before click submit button " + scene);
            try {
                loginRegisterController.onSubmitButton(new ActionEvent(b, null));
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        });

        WaitForAsyncUtils.waitForFxEvents();
        verify(player, times(1)).sendMessage(new Message("create"));
        verify(player, times(1)).sendMessage(new Message(username.getText()));
        verify(player, times(1)).sendMessage(new Message(password.getText()));
    }
}
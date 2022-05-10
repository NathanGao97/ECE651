package edu.duke.ece651.mp.client.controller;

import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.controller.loader.GameStartMenuControllerLoader;
import edu.duke.ece651.mp.client.controller.loader.GameTopLevelControllerLoader;
import edu.duke.ece651.mp.common.Message;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;


public class LoginRegisterController {
    @FXML
    Group loginGroup;

    @FXML
    Group registerGroup;

    @FXML
    Text loginError;

    @FXML
    TextField username;

    @FXML
    PasswordField password;

    @FXML
    PasswordField reEnterPassword;

    @FXML
    Button returnLogin;

    @FXML
    Button loginButton;

    @FXML
    Button submitButton;

    @FXML
    Button signUpButton;

    public void onLoginButton(ActionEvent ae) throws IOException, ClassNotFoundException {
        Object source = ae.getSource();
        if(source instanceof Button){
            Client.player.sendMessage(new Message("login"));
            Client.player.sendMessage(new Message(username.getText()));
            Client.player.sendMessage(new Message(password.getText()));

            Message status = Client.player.recvMessage();

            if("ok".equals(status.getType())){
                // go to next game start menu scene
                Button b = (Button) source;
                Stage stage = (Stage) b.getScene().getWindow();

                GameStartMenuControllerLoader.getGameTopLevelControllerLoader().loadParentFXMLToStage(stage);
            }
            else{
                loginError.setText("username or password wrong");
                loginError.setVisible(true);
            }
        }
        else{
            throw new IllegalArgumentException("Wrong action event, not caused by button");
        }

    }


    public void onSignUpButton(ActionEvent ae) throws IOException {
        Object source = ae.getSource();
        if(source instanceof Button){

            username.clear();
            password.clear();
            reEnterPassword.clear();

            loginError.setVisible(false);
            loginGroup.setVisible(false);
            registerGroup.setVisible(true);

        }
        else{
            throw new IllegalArgumentException("Wrong action event, not caused by button");
        }
    }


    public void onReturnButton(ActionEvent ae){
        Object source = ae.getSource();
        if(source instanceof Button){
            username.clear();
            password.clear();
            loginError.setVisible(false);
            registerGroup.setVisible(false);
            loginGroup.setVisible(true);
        }
        else{
            throw new IllegalArgumentException("Wrong action event, not caused by button");
        }
    }

    public void onSubmitButton(ActionEvent ae) throws IOException, ClassNotFoundException {
        Object source = ae.getSource();

        if(source instanceof Button){

            if(password.getText().equals(reEnterPassword.getText())){
                /* send username and password to the server */

                Client.player.sendMessage(new Message("create"));
                Client.player.sendMessage(new Message(username.getText()));
                Client.player.sendMessage(new Message(password.getText()));

                /* get response from the server */
                Message mess = Client.player.recvMessage();

                if("ok".equals(mess.getType())){
                    // go to next game start menu scene
                    Button b = (Button) source;
                    Stage stage = (Stage) b.getScene().getWindow();
                    GameStartMenuControllerLoader.getGameTopLevelControllerLoader().loadParentFXMLToStage(stage);

                }
                else{
                    throw new IllegalArgumentException("The return mess of sign up must be ok");
                }
            }
            else{
                loginError.setText("The two password entered is not correct");
                loginError.setVisible(true);

            }
        }
        else{
            throw new IllegalArgumentException("Wrong action event, not caused by button");
        }
    }




}

package edu.duke.ece651.mp.client.controller.loader;

import javafx.fxml.FXMLLoader;

public class LoginRegisterControllerLoader extends ControllerLoader{
    private static LoginRegisterControllerLoader loginRegisterControllerLoader = null;

    private LoginRegisterControllerLoader(){
        super("/ui/login.fxml");
    }

    public static LoginRegisterControllerLoader getLoginRegisterControllerLoader(){
        if(loginRegisterControllerLoader == null){
            loginRegisterControllerLoader = new LoginRegisterControllerLoader();
        }

        return loginRegisterControllerLoader;
    }



}

package edu.duke.ece651.mp.client.controller.loader;

import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.controller.GameTopLevelController;
import edu.duke.ece651.mp.client.controller.PolygonController;
import javafx.fxml.FXMLLoader;

public class GameTopLevelControllerLoader extends ControllerLoader{

    private static GameTopLevelControllerLoader gameTopLevelControllerLoader = null;

    private GameTopLevelControllerLoader(){
        super("/ui/GameBase.fxml");
    }

    public static GameTopLevelControllerLoader getGameTopLevelControllerLoader(){
        if(gameTopLevelControllerLoader == null){
            gameTopLevelControllerLoader = new GameTopLevelControllerLoader();
        }
        return gameTopLevelControllerLoader;
    }

}

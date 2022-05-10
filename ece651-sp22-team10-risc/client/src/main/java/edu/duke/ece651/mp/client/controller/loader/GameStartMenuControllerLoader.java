package edu.duke.ece651.mp.client.controller.loader;

public class GameStartMenuControllerLoader extends ControllerLoader{

    private static GameStartMenuControllerLoader gameTopLevelControllerLoader = null;



    private GameStartMenuControllerLoader(){
        super("/ui/game_start_menu.fxml");
    }

    public static GameStartMenuControllerLoader getGameTopLevelControllerLoader(){
        if(gameTopLevelControllerLoader == null){
            gameTopLevelControllerLoader = new GameStartMenuControllerLoader();
        }

        return gameTopLevelControllerLoader;
    }

}



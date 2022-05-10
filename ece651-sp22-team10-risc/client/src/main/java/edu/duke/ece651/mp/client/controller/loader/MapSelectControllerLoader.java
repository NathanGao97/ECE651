package edu.duke.ece651.mp.client.controller.loader;

public class MapSelectControllerLoader extends ControllerLoader{

    private static MapSelectControllerLoader mapSelectControllerLoader = null;

    private MapSelectControllerLoader() {
        super("/ui/MapChoice.fxml");
    }

    public static MapSelectControllerLoader getMapSelectControllerLoader(){
        if(mapSelectControllerLoader == null){
            mapSelectControllerLoader = new MapSelectControllerLoader();
        }

        return mapSelectControllerLoader;
    }

}

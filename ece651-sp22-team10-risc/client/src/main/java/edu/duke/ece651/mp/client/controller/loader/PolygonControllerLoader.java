package edu.duke.ece651.mp.client.controller.loader;

public class PolygonControllerLoader extends ControllerLoader{
    private static PolygonControllerLoader polygonControllerLoader = null;


    private PolygonControllerLoader(int mapType) {
        super("/ui/map" + mapType + "0.fxml");
    }

    public static PolygonControllerLoader getPolygonControllerLoader(int mapType){
        if(polygonControllerLoader == null){
            polygonControllerLoader = new PolygonControllerLoader(mapType);
        }

        return polygonControllerLoader;
    }

}

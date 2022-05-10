package edu.duke.ece651.mp.client.controller.loader;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;

class ControllerLoader {
    private FXMLLoader fxmlLoader;

    private Parent parent= null;

    ControllerLoader(String str){
        fxmlLoader = new FXMLLoader(getClass().getResource(str));
    }

    private Parent loadFXML(){
        if(parent == null){
            try {
                parent = fxmlLoader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return parent;
    }

    //T is inferred according to return type
    public <T> T getController(){
        //make sure fxml is loaded before get controller
        loadFXML();
        return fxmlLoader.getController();
    }

    public Parent getParent(){
        return loadFXML();
    }

    public void loadParentFXMLToStage(Stage stage){
        Parent p = loadFXML();
        //remove the root from the scene if it is pre-occupied by another scene
        if(p.getScene() != null && p.getScene() != stage.getScene()){
            p.getScene().setRoot(new Label("This node is currently used by another scene (No two scene can use the same node)"));
        }

        URL cssResource = getClass().getResource("/ui/style.css");
        stage.getScene().getStylesheets().add(cssResource.toString());
        stage.getScene().setRoot(p);
    }

}

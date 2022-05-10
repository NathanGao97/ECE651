package edu.duke.ece651.mp.client.controller.loader;

public class UnitAssignControllerLoader extends ControllerLoader{

    public static UnitAssignControllerLoader unitAssignControllerLoader = null;

    private UnitAssignControllerLoader() {
        super("/ui/AssignUnits.fxml");
    }


    public static UnitAssignControllerLoader getUnitAssignControllerLoader(){
        if(unitAssignControllerLoader == null){
            unitAssignControllerLoader = new UnitAssignControllerLoader();
        }

        return unitAssignControllerLoader;
    }

}

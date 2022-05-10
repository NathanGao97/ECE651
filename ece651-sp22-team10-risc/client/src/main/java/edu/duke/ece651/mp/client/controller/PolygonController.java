package edu.duke.ece651.mp.client.controller;

import edu.duke.ece651.mp.client.Client;
import edu.duke.ece651.mp.client.beans.RGBColorProperty;
import edu.duke.ece651.mp.client.controller.loader.GameTopLevelControllerLoader;
import edu.duke.ece651.mp.client.models.RGBColor;
import edu.duke.ece651.mp.client.models.TerritoryModel;
import edu.duke.ece651.mp.client.models.UnitTableModel;
import edu.duke.ece651.mp.client.models.UnitTableRowModel;
import edu.duke.ece651.mp.common.Message;
import edu.duke.ece651.mp.common.RiscMap;
import edu.duke.ece651.mp.common.Territory;
import edu.duke.ece651.mp.common.Unit;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.ObjectBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.IntegerProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.TableView;
import javafx.scene.control.Tooltip;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Polygon;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;


public class PolygonController implements Initializable {

    @FXML public Text gameTextDisplay, title;

    @FXML public Group territory_group, territory_group_text;

    HashMap<String, RGBColor> rgbColorHashMap = RGBColor.getRgbColorMap();

    static HashMap<String, Tooltip> polygonToToolTip = new HashMap<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        RiscMap currentMap = Client.player.currentMap;
        if(currentMap == null){
            throw new IllegalArgumentException("Should receive map before initialize polygon controller");
        }
        GameTopLevelControllerLoader gameTopLevelControllerLoader = GameTopLevelControllerLoader.getGameTopLevelControllerLoader();
        GameTopLevelController gameTopLevelController = gameTopLevelControllerLoader.getController();

        //bind polygon color with territory model
        bindPolygonToTerritoryColor(currentMap);

        //load Elements into game base after load polygon
        renderElementsInGameBaseAfterLoadTerritory(currentMap, gameTopLevelController);
    }

    public void bindPolygonToTerritoryColor(RiscMap currentMap){
        //compose list of territory models (we need them to be loaded before bind polygon)
        List<Territory> currentTerritories = currentMap.getTerritoryList();

        List<TerritoryModel> territoryModelList = TerritoryModel.getTerritoryModelList(currentTerritories, Client.player.getCurrentColor().visible_territories);

        for(Node n : territory_group.getChildren()){
            if(n instanceof Polygon){
                Polygon p = (Polygon) n;
                TerritoryModel territoryModel = TerritoryModel.getTerritoryModelByName(p.getId());
                //bind color
                RGBColorProperty rgbColorProperty = territoryModel.getRgbColor();
                IntegerProperty r = rgbColorProperty.rProperty();
                IntegerProperty g = rgbColorProperty.gProperty();
                IntegerProperty b = rgbColorProperty.bProperty();
                IntegerProperty opacity = rgbColorProperty.pProperty();
                Text text = findTextByName(p.getId());
                text.fillProperty().bind(Bindings.createObjectBinding(()->Color.rgb(r.get(), g.get(), b.get()), r, g, b));

                p.fillProperty().bind(Bindings.createObjectBinding(()->Color.rgb(208, 192, 192, opacity.get()), opacity));

                //bind tool tip
                Tooltip tooltip = new Tooltip();
                Tooltip.install(p, tooltip);
                polygonToToolTip.put(p.getId(), tooltip);
            }
        }
    }

    private Text findTextByName(String str){
        for(Node n : territory_group_text.getChildren()){
            if(n instanceof Text){
                Text t = (Text) n;
                if(str.equals(t.getText())) return t;
            }
        }

        return null;
    }

    private void renderElementsInGameBaseAfterLoadTerritory(RiscMap map, GameTopLevelController gameTopLevelController){

        // render source and dest check box
        List<Territory> territoryList = map.getTerritoryList();
        //add items to source and dest combo
        for(Territory terr:territoryList){
            gameTopLevelController.ma_source.getItems().add(terr.getName());
            gameTopLevelController.ma_dest.getItems().add(terr.getName());
        }

        List<String> unitName = new ArrayList<>();
        unitName.add("Chopper");
        unitName.add("Nami");
        unitName.add("Usopp");
        unitName.add("Franky");
        unitName.add("Sanji");
        unitName.add("Zoro");
        unitName.add("Luffy");
        unitName.add("Spy");

        //load check box component
        for(String name : unitName){
            gameTopLevelController.up_target_type.getItems().add(name);
            gameTopLevelController.up_unit_type.getItems().add(name);
            gameTopLevelController.ma_unit_type.getItems().add(name);
        }
    }

    public static String[] sourceDest = new String[2];
    private static int count = 0;
    UnitTypePopUpHelper unitTypePopUpHelper = new UnitTypePopUpHelper();
    public static Stage moveAttackPopUpStage;
    public static boolean sameTerritory;
    @FXML
    public void mouseClickedTerritory(MouseEvent me) throws IOException, ClassNotFoundException {

        if(me.getButton() == MouseButton.PRIMARY){
            System.out.println("Primary mouse clicked");
            onPrimaryMouseClicked(me);
        }

        if(me.getButton() == MouseButton.SECONDARY){
            System.out.println("Secondary mouse clicked");
            onSecondaryMouseClicked(me);
        }

    }

    PopUpMessageHelper popUpMessageHelper = new PopUpMessageHelper();

    private void onSecondaryMouseClicked(MouseEvent me) throws IOException, ClassNotFoundException {

        Object source = me.getSource();
        if(!(source instanceof Polygon))    return;
        Polygon p = (Polygon) source;

        String territoryName = p.getId();

        //compose the unit upgrade command
        Message sendedMessage = new Message("cloak", Client.player.getCurrentColor(), territoryName);

        Client.player.sendMessage(sendedMessage);

        Message message = Client.player.recvMessage();

        if(message.getType() == null){
            popUpMessageHelper.popOutMessage("Clocked " + p.getId() + " successfully!");
        }
        else {
            popUpMessageHelper.popOutMessage(message.getType());
        }


    }

    private void onPrimaryMouseClicked(MouseEvent me) throws IOException {
        Object source = me.getSource();
        if(!(source instanceof Polygon))    return;
        Polygon p = (Polygon) source;

        if(count == 0){
            sourceDest[0] = p.getId();
        }
        else {
            sourceDest[1] = p.getId();
            if(!sourceDest[0].equals(sourceDest[1])){
                moveAttackPopUpStage = unitTypePopUpHelper.popUpUnitSelect(sourceDest[0], sourceDest[1]);
            }
            else {
                SelectUpgradeUnitTypePopUpHelper selectUpgradeUnitTypePopUpHelper = new SelectUpgradeUnitTypePopUpHelper();
                selectUpgradeUnitTypePopUpHelper.popUpSelectUpgrade();

            }
        }
        count = (count + 1) % 2;
    }

    @FXML
    public void mouseMovedTerritory(MouseEvent me){

        Object source = me.getSource();
        if(source instanceof Polygon){

            //update table model
            Polygon p = (Polygon) source;
            String terrName = p.getId();
            gameTextDisplay.setText(terrName);
            Client.player.updateTableModel(Client.player.getCurrentColor().visible_territories);

            TerritoryModel territoryModel = TerritoryModel.getTerritoryModelByName(p.getId());
            territoryModel.getRgbColor().updateRGBColorProperty(rgbColorHashMap.get("Grey"));

            //update toolTip
            updateToolTip(p);
        }
    }

    private void updateToolTip(Polygon p){
        //get table model
        UnitTableModel unitTableModel = UnitTableModel.getUnitTableModel();
        polygonToToolTip.get(p.getId()).setText(unitTableModel.toString());
    }

    @FXML
    public void mouseLeavedTerritory(MouseEvent me){
        Polygon p = (Polygon) me.getSource();
        polygonToToolTip.get(p.getId()).hide();
        String terrName = p.getId();
        //reset territory color when leave
        String color = Client.player.currentMap.getTerritoryByName(terrName).getOwner().getName();
        TerritoryModel territoryModel = TerritoryModel.getTerritoryModelByName(p.getId());
        boolean visible = Client.player.getCurrentColor().visible_territories.contains(terrName);
        territoryModel.getRgbColor().updateRGBColorProperty(rgbColorHashMap.get(visible ? color:"????"));
    }



}

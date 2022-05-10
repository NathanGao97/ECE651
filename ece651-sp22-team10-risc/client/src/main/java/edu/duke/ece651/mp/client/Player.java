package edu.duke.ece651.mp.client;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import edu.duke.ece651.mp.client.controller.*;
import edu.duke.ece651.mp.client.controller.taks.RecvMapMessageOnLosePhaseTask;
import edu.duke.ece651.mp.client.controller.taks.onsuccess.RecvColorMapOnSuccess;
import edu.duke.ece651.mp.client.controller.taks.RecvColorMapTask;
import edu.duke.ece651.mp.client.controller.loader.GameTopLevelControllerLoader;
import edu.duke.ece651.mp.client.controller.loader.PolygonControllerLoader;
import edu.duke.ece651.mp.client.controller.loader.UnitAssignControllerLoader;
import edu.duke.ece651.mp.client.models.ColorModel;
import edu.duke.ece651.mp.client.models.TerritoryModel;
import edu.duke.ece651.mp.client.models.UnitTableModel;
import edu.duke.ece651.mp.common.*;
import javafx.concurrent.Task;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class Player {

  /* socket connection */
  private final Socket sock;

  private final AbstractUnitFactory unitFactory;

  /* one player can only hold one position and currentMap at one time */
  private Color currentColor;
  public RiscMap currentMap;

  public int territoryIndex;

  private final PopUpMessageHelper popUpMessageHelper = new PopUpMessageHelper();

  private final ObjectSenderReceiver objectSenderReceiver;

  /*
   * List used to store the number of games the client joined
   * */
  public Player(String hostname, int port) throws IOException {
    this.sock = new Socket(hostname, port);
    this.objectSenderReceiver = new ObjectSenderReceiver(sock.getInputStream(), sock.getOutputStream());

    this.unitFactory = new V1UnitFactory();
    territoryIndex = 0;
  }

  /*
  * This constructor is for testing only, not actual TCP connection is established
  * */
  public Player(ObjectSenderReceiver objectSenderReceiver) throws IOException {
    this.sock = null;
    this.objectSenderReceiver = objectSenderReceiver;
    this.unitFactory = new V1UnitFactory();
    territoryIndex = 0;
  }



  /*
  * ...Action function is used for change current map locally
  * */
  public void performAttackAction(Message sendedMessage){
    AttackActioner attackActioner = new AttackActioner();
    attackActioner.performAction(sendedMessage, currentMap);

    //update current map, color and the models
    updateCurrentMap(currentMap);
  }

  public void performMoveAction(Message sendedMessage){
    MoveActioner moveActioner = new MoveActioner();
    moveActioner.performAction(sendedMessage, currentMap);

    //update the current map, color and the models
    updateCurrentMap(currentMap);
  }

  public void performTechUpgradeAction(Message sendedMessage){
    TechUpgradeActioner techUpgradeActioner = new TechUpgradeActioner();
    techUpgradeActioner.performAction(sendedMessage, currentMap);

    //update the current map , color and the models
    updateCurrentMap(currentMap);
  }

  public void performUnitUpgradeAction(Message sendedMessage) throws IOException, ClassNotFoundException {
    //send command message to server
    this.sendMessage(sendedMessage);
    popUpMessageHelper.popOutMessage("Upgrade order has been sent to server.");
    Message status = this.recvMessage();

    //check the response
    if(status.getType() != null){
      //pop out error message
      popUpMessageHelper.popOutMessage(status.getType());
    }
    else {
      UnitUpgradeActioner unitUpgradeActioner = new UnitUpgradeActioner();
      unitUpgradeActioner.performAction(sendedMessage, Client.player.currentMap);
    }

    //TODO: update UnitTableModel
    updateCurrentMap(currentMap);
  }

  public void updateTableModel(List<String> visibleTerritories){
    // the table model go with the territory name the user clicked
    GameTopLevelControllerLoader gameTopLevelControllerLoader = GameTopLevelControllerLoader.getGameTopLevelControllerLoader();
    GameTopLevelController gameTopLevelController = gameTopLevelControllerLoader.getController();
    String selectedTerritory = gameTopLevelController.gameTextDisplay.getText();

    //update the unit table model according to territory model
    List<Unit> unitsOfSelectedTerritoryModel = TerritoryModel.getTerritoryModelByName(selectedTerritory).getUnitsOnTerritoryModel();

    //update the table model with the
    UnitTableModel.getUnitTableModel().updateAllyUnits(unitsOfSelectedTerritoryModel);

  }

  /*
  * Player can stay there watch the game, but can do nothing
  * */
  public void onGameLosePhase(){
//    Stage popUpStage = popUpMessageHelper.popOutMessage("You Lose, you can stay here or close the game");
    System.out.println("Game lose");
    // receive map and message Async
    Task task = new RecvMapMessageOnLosePhaseTask(this);

    task.setOnSucceeded(taskFinish->{
      Message message = (Message) task.getValue();
      if("lose".equals(message.getType())){
        popUpMessageHelper.popOutMessage("Map updated");
        onGameLosePhase();
      }
      else if("done".equals(message.getType())){
        popUpMessageHelper.popOutMessage("The game has done");
      }
    });

    Thread th  = new Thread(task);

    th.start();

  }

  /*
  * The user select which game map to play (game map differs by the number of players)
  * */
  private void requestMapSelectMapPhase(String mapType) throws IOException {
    Client.player.sendMessage(new Message("new"));
    Client.player.sendMessage(new Message(mapType.substring(mapType.length()-1, mapType.length())));
  }


  public void doSelectGameMapPhase(Stage stage, String rectID) throws IOException {

    //request map
    Client.player.requestMapSelectMapPhase(rectID);

    //wait for recv map
    Stage popUpStage = popUpMessageHelper.popOutMessage("Waiting for other players...", stage);
    popUpStage.setOnCloseRequest(event -> event.consume());

    // thread to recv map and player color
    Task task = new RecvColorMapTask(this);

    //(Async) After receive map and color successfully, go to doUnitAssignmentPhase
    task.setOnSucceeded(new RecvColorMapOnSuccess(Client.player, stage, popUpStage));

    System.out.println("Create thread to receive map and color");

    new Thread(task).start();

    Client.player.updateCurrentColor(new Color("Red"));

    return ;
  }

  public void doUnitAssignmentPhase(Stage parentStage) throws IOException {

    Stage stage = new Stage();

    /* disable close and touch parent stage */
    stage.initOwner(parentStage);
    stage.initModality(Modality.WINDOW_MODAL);
    stage.setOnCloseRequest(event -> {event.consume();});

    /* load unit assignment phase */
    UnitAssignControllerLoader unitAssignControllerLoader = UnitAssignControllerLoader.getUnitAssignControllerLoader();
    UnitAssignController unitAssignController = unitAssignControllerLoader.getController();

    /* set up  */
    unitAssignController.wait.setVisible(false);
    unitAssignController.promptRemain.setVisible(false);
    unitAssignController.promptLast.setVisible(false);

    unitAssignController.territoryName.setText(Client.player.currentMap.getTerritoryListOwnedByColor(Client.player.getCurrentColor()).get(Client.player.territoryIndex).getName());

    Scene scene = new Scene(unitAssignControllerLoader.getParent(), 1280, 920);
    stage.setScene(scene);
    stage.show();
  }

  //Load game base and territory with the received map
  public void loadGameBaseAfterSelectMap(Stage stage, RiscMap map){
    GameTopLevelControllerLoader gameTopLevelControllerLoader = GameTopLevelControllerLoader.getGameTopLevelControllerLoader();
    GameTopLevelController gameTopLevelController = gameTopLevelControllerLoader.getController();

    //load game base and its controller
    AnchorPane baseGame = (AnchorPane) gameTopLevelControllerLoader.getParent();

    System.out.println("Loaded game base");

    //load map2.fxml, map3.fxml ...... according to the received currentMap type
    Parent map_root = Client.player.loadTerritoryAfterSelectMap(map.getColorList().size());;
    baseGame.getChildren().add(map_root);

    //create scene and load css file
    Scene scene = new Scene(baseGame);
    stage.setScene(scene);

    //re-render unit type
  }

  /*
  * Load territories in map?.xml and the corresponding PolygonController
  * */
  private Parent loadTerritoryAfterSelectMap(int mapType){

    System.out.println("Going to load map" + mapType);

      PolygonControllerLoader polygonControllerLoader = PolygonControllerLoader.getPolygonControllerLoader(mapType);
      PolygonController polygonController = polygonControllerLoader.getController();

      GameTopLevelControllerLoader gameTopLevelControllerLoader = GameTopLevelControllerLoader.getGameTopLevelControllerLoader();
      GameTopLevelController gameTopLevelController = gameTopLevelControllerLoader.getController();

      polygonController.gameTextDisplay = gameTopLevelController.gameTextDisplay;
      gameTopLevelController.territory_group = polygonController.territory_group;

      polygonController.title.setText(gameTopLevelController.title.getText());

      Parent res = polygonControllerLoader.getParent();

      return res;

  }

  public Color getCurrentColor() {
    return this.currentColor;
  }

  public Socket getSocket(){
    return this.sock;
  }


  /*
   * This function will send the mess object to the server
   */
  public void sendMessage(Message mess) throws IOException {
      objectSenderReceiver.sendObject(mess);
  }

  /*
   * This function will be used to recv Message object sent from server
   *
   * @return the Message object we received
   */

  public Message recvMessage() throws ClassNotFoundException, IOException {
    Message readMessage = objectSenderReceiver.receiveObject();
    return readMessage;
  }

  public List<Message> recvListMessage() throws IOException, ClassNotFoundException {
    List<Message> messageList = objectSenderReceiver.receiveObject();
    return messageList;
  }

  public Integer recvInteger() throws ClassNotFoundException, IOException {
    Integer readInteger = objectSenderReceiver.receiveObject();
    return readInteger;
  }

  /*
   * This function will be used to receive color object sent from the server
   */
  public Color recvColor() throws ClassNotFoundException, IOException {
    Color readColor = objectSenderReceiver.receiveObject();
    //update the current color and its model
    updateCurrentColor(readColor);
    return readColor;
  }

  /*
   * This function will be used to receive RiscMap object from the server
   */
  public RiscMap recvMap() throws ClassNotFoundException, IOException {

    RiscMap readMap = objectSenderReceiver.receiveObject();
    currentMap = readMap;

    // update the current model and its map
    updateCurrentMap(currentMap);

    territoryIndex = 0;

    return readMap;
  }

  public void sendColor(Color c) throws IOException {
    objectSenderReceiver.sendObject(c);
  }


  /*
   * Find the color in map that has the same name with current color
   * */
  private Color findCurrentColorInMap(RiscMap map){
    List<Color> colors = map.getColorList();
    for(Color c : colors){
      if(c.getName().equals(currentColor.getName())){
        return  c;
      }
    }
    return null;
  }

  public void sendMap(RiscMap map) throws IOException {
    objectSenderReceiver.sendObject(map);
  }

  /*
   * Update the currentColor and the ColorModel
   * Also act as a color setter
   * */
  public void updateCurrentColor(Color c){
    this.currentColor = c;
    ColorModel.getColorModel().updateColorModel(this.currentColor);
  }

  /*
   * Update the current map, color and the models
   * */
  public void updateCurrentMap(RiscMap map){

    if(map != null){
      this.currentMap = map;

      //update current color and the model
      updateCurrentColor(findCurrentColorInMap(map));

      //TODO: check the visibility of territory and update
      System.out.println("Current visible territory" + currentColor.visible_territories);
      //update current map and the model
      TerritoryModel.updateTerritoryModels(map.getTerritoryList(), currentColor.visible_territories);

      //update current unit table model
      updateTableModel(currentColor.visible_territories);
    }

    return;
  }

}

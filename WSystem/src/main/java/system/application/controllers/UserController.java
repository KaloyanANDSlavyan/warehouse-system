package system.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import system.backend.WSystem;
import system.backend.dao.DAO;
import system.backend.dao.MainDAO;
import system.backend.dataholders.OwnerDataHolder;
import system.backend.others.Warehouse;
import system.backend.profiles.Agent;
import system.backend.profiles.Owner;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController extends AbstractController implements Initializable {
    @FXML
    public Label firstNameLabel;
    public static Label static_firstName;

    @FXML
    private Label lastNameLabel;
    public static Label static_lastName;

    @FXML
    private Label phoneLabel;
    public static Label static_phoneNumber;
    @FXML
    protected Button deleteButton = null;
    @FXML
    protected AnchorPane anchorPane = new AnchorPane();
    @FXML
    private ImageView imageView = new ImageView();
    private Image image = new Image("/styling/images/warehouseIMG.png");
    protected static AnchorPane anchorPane_static = new AnchorPane();
    private OwnerDataHolder ownerDataHolder = OwnerDataHolder.getInstance();

    private Owner owner;
    private Agent agent;
    private Warehouse wh;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        anchorPane_static = anchorPane;
    }

    public void setOwner(Owner owner){
        this.owner = owner;

        firstNameLabel.setText(owner.getFirstname());
        lastNameLabel.setText(owner.getLastname());
        phoneLabel.setText(owner.getPhoneNumber());

    }
    public void setAgent(Agent agent){
        this.agent = agent;

        firstNameLabel.setText(agent.getFirstname());
        lastNameLabel.setText(agent.getLastname());
        phoneLabel.setText(agent.getPhoneNumber());
    }
    public void setWarehouse(Warehouse wh){ // sets image of warehouse, warehouse id and warehouse category
        this.wh = wh;

        imageView.setImage(image);
        firstNameLabel.setText("Warehouse " + Long.toString(wh.getID()));
        lastNameLabel.setText(wh.getCategory());
        phoneLabel.setVisible(false);
    }

    public void handleDeleteButton(ActionEvent event) {
        System.out.println("Delete button clicked");
        WSystem wSystem = WSystem.getInstance();

        DAO<Agent, String> agentDAO = new MainDAO<>();
        DAO<Owner, String> ownerDAO = new MainDAO<>();
        DAO<Warehouse, String> warehouseDAO = new MainDAO<>();

        if(this.owner != null) {
            ownerDAO.deleteByID(Owner.class, this.owner.getID());
            anchorPane.setVisible(false);
        }
        else if(this.agent != null){
            agentDAO.deleteByID(Agent.class, this.agent.getID());
            anchorPane.setVisible(false);
        } else{
            wh.getOwner().getWarehouses().remove(wh);
            warehouseDAO.deleteByID(Warehouse.class, this.wh.getID());
            anchorPane.setVisible(false);
        }

    }
    public void loadEditUserInfo(String path){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Stage primaryStage = new Stage();
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            setDataForAgentOROwner(loader);

            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void  loadEditWarehouse(String path){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            Stage primaryStage = new Stage();
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            setDataForWarehouse(loader);

            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    public void setDataForWarehouse(FXMLLoader loader){
        CreateWarehouseController createWarehouseController = loader.getController();
        createWarehouseController.setData(wh);

    }
    public  void setDataForAgentOROwner(FXMLLoader loader){
        EditUserInfoController editUserInfoController = loader.getController();
        if (this.owner == null)
        editUserInfoController.setDataForAgent(agent);
        else {
            editUserInfoController.setDataForOwner(owner);
        }
    }


    public void handleEditButton(ActionEvent event) {
        System.out.println("Edit button clicked");

        if (this.agent == null && this.owner == null){
            loadEditWarehouse("/fxml/createWarehouseFXML.fxml");    // loads edit warehouse
        }else
            loadEditUserInfo("/fxml/editUserInfoFXML.fxml");    // loads edit user


        static_firstName = firstNameLabel;
        static_lastName = lastNameLabel;
        static_phoneNumber = phoneLabel;

    }
    public void transferMessage(String message){    // sets the warehouse category label
        System.out.println("Transfer Message: " + message);
        static_lastName.setText(message);   // warehouse category
    }

}

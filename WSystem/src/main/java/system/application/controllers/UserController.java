package system.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import system.backend.WSystem;
import system.backend.dao.AgentDAO;
import system.backend.dao.OwnerDAO;
import system.backend.profiles.Agent;
import system.backend.profiles.Owner;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UserController implements Initializable {
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
    protected static AnchorPane anchorPane_static = new AnchorPane();

    private Owner owner;
    private Agent agent;

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

    public void handleDeleteButton(ActionEvent event) {
        System.out.println("Delete button clicked");
        WSystem wSystem = WSystem.getInstance();
        AgentDAO agentDAO = wSystem.getAgentDAO();
        OwnerDAO ownerDAO = wSystem.getOwnerDAO();

        if(this.owner == null) {
            agentDAO.deleteByID(Agent.class, this.agent.getID());
            anchorPane.setVisible(false);

        }
        else {
            ownerDAO.deleteByID(Owner.class, this.owner.getID());
            anchorPane.setVisible(false);
        }

    }

    public void handleEditButton(ActionEvent event) {
        System.out.println("Edit button clicked");

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/editUserInfoFXML.fxml"));
            Stage primaryStage = new Stage();
            Parent root = loader.load();
            Scene scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.initStyle(StageStyle.UNDECORATED);
            EditUserInfoController editUserInfoController = loader.getController();
            if (this.owner == null){
                editUserInfoController.setDataForAgent(agent);
            }
            else  {
                editUserInfoController.setDataForOwner(owner);
            }
            primaryStage.show();
        }catch (IOException e){
            e.printStackTrace();
        }
        static_firstName = firstNameLabel;
        static_lastName = lastNameLabel;
        static_phoneNumber = phoneLabel;

    }

}

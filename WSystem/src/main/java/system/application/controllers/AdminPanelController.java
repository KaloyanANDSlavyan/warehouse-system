package system.application.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import system.backend.WSystem;
import system.backend.profiles.Agent;
import system.backend.profiles.Owner;
import system.backend.profiles.Profile;

import java.util.List;

public class AdminPanelController {
    @FXML
    private Button exitButton = null;
    @FXML
    private Button createOwnerButton = null;
    @FXML
    private Button createAgentButton = null;
    @FXML
    private Button profilesButton = null;
    @FXML
    private AnchorPane loaderPane = null;

    public void setLoader(String fxmlFile) {
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getView(fxmlFile);
        loaderPane.getChildren().clear();

        WSystem wSystem = WSystem.getInstance();

        if (wSystem.hasProfiles()) {
            // add the button
            // after click -> Profile button action
            System.out.println("Yes the system has profiles!");
            System.out.println("Button added.");
        }

        loaderPane.getChildren().add(view);
    }

    public void onExitAction(ActionEvent actionEvent) {
        System.out.println("Clicked " + exitButton.getText());
        Platform.exit();
        System.exit(0);
    }

    public void handleButton1Action(ActionEvent actionEvent) {
        System.out.println("You clicked " + createOwnerButton.getText());
        setLoader("registerOwnerFXML");
    }

    public void handleButton2Action(ActionEvent actionEvent) {
        System.out.println("You clicked " + createAgentButton.getText());
        setLoader("registerAgentFXML");
    }

    public void handleButton3Action(ActionEvent actionEvent) {
        System.out.println("You clicked " + profilesButton.getText());
        setLoader("profilesFXML");

        //Change panel
        System.out.println("Button clicked");
        WSystem wSystem = WSystem.getInstance();

        if (wSystem.hasAgentProfiles()) {
            //add AgentProfiles button
            System.out.println("Showing agents here:");
            for (Agent agent : wSystem.getAgents()) {
                System.out.println("\nAgent:");
                System.out.println(agent.getID());
                System.out.println(agent.getFirstname());
                System.out.println(agent.getLastname());
                System.out.println(agent.getPassword());
                System.out.println(agent.getEmailAddress());
                System.out.println(agent.getPhoneNumber());
            }
        }
        if (wSystem.hasOwnerProfiles()) {
            //add OwnerProfiles button
            System.out.println("\n\nShowing owners here:");
            for (Owner owner : wSystem.getOwners()) {
                System.out.println("\nOwner:");
                System.out.println(owner.getID());
                System.out.println(owner.getFirstname());
                System.out.println(owner.getLastname());
                System.out.println(owner.getPassword());
                System.out.println(owner.getEmailAddress());
                System.out.println(owner.getPhoneNumber());
            }
        }
    }
}

// WSystem wSystem = WSystem.getInstance()  ->  Get system instance
// wSystem.hasProfiles()  ->  Returns true, if profiles exist. Otherwise - false
// wSystem.hasOwnerProfiles()  ->  Return true, if owner profiles exist. Otherwise - false
// wSystem.hasAgentProfiles()  ->  Return true, if agent profiles exist. Otherwise - false

// wSystem.getOwners()  ->  Returns list of all owners, registered in the system  -->  List<Owner>
// wSystem.getAgents()  ->  Returns list of all agents, registered in the system  -->  List<Agent>
// use getters to access values

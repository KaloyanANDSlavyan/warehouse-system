package system.application.controllers;

import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import system.backend.WSystem;
import system.backend.profiles.Agent;
import system.backend.profiles.Owner;

public class AdminPanelController {
    @FXML
    private AnchorPane anchorPane = null;
    @FXML
    private Button exitButton = null;
    @FXML
    private Button createOwnerButton = null;
    @FXML
    private Button createAgentButton = null;
    @FXML
    private Button profilesButton = new Button();
    @FXML
    private AnchorPane loaderPane = null;
    @FXML
    private Button refreshButton = null;
    private BooleanProperty profilesCheck = new SimpleBooleanProperty(false);


    public void initialize() {
        System.out.println("AdminPanel initialized.");
        profilesState();
        profilesButton.visibleProperty().bind(profilesCheck);
    }

    public void profilesState(){
        WSystem wSystem = WSystem.getInstance();
        if (wSystem.hasProfiles())
            profilesCheck.set(true);

        System.out.println("profilesCheck is: " + profilesCheck);
    }

    public Button getProfilesButton() {
        return profilesButton;
    }

    public boolean isProfilesCheck() {
        return profilesCheck.get();
    }

    public BooleanProperty profilesCheckProperty() {
        return profilesCheck;
    }

    public AnchorPane getAnchorPane() {
        return anchorPane;
    }

    public void setLoader(String fxmlFile) {
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getView(fxmlFile);
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(view);
        initialize();
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
    }


    public void handleRefreshButton(ActionEvent event) {
        initialize();
    }
}

// WSystem wSystem = WSystem.getInstance()  ->  Get system instance
// wSystem.hasProfiles()  ->  Returns true, if profiles exist. Otherwise - false
// wSystem.hasOwnerProfiles()  ->  Return true, if owner profiles exist. Otherwise - false
// wSystem.hasAgentProfiles()  ->  Return true, if agent profiles exist. Otherwise - false

// wSystem.getOwners()  ->  Returns list of all owners, registered in the system  -->  List<Owner>
// wSystem.getAgents()  ->  Returns list of all agents, registered in the system  -->  List<Agent>
// use getters to access values

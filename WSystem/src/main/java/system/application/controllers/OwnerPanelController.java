package system.application.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import system.backend.WSystem;
import system.backend.profiles.Owner;

import java.io.IOException;

public class OwnerPanelController {
    @FXML
    private AnchorPane anchorPane = null;
    @FXML
    private Button exitButton = null;
    @FXML
    private Button createWarehouseButton = null;
    @FXML
    private Button assignAgentsButton = null;
    @FXML
    private Button profileButton = null;
    @FXML
    protected Button reportsButton = new Button();
    protected static Button reportsButton_static = new Button();
    @FXML
    private Label firstNameLabel = null;
    @FXML
    private AnchorPane loaderPane = null;
    @FXML
    private Button logoutButton = null;
    private WSystem wSystem = WSystem.getInstance();
    private Owner owner;


    public void initialize(){
        //setFirstNameLabel();
    }
    public void setFirstNameLabel(Owner owner){     // Sets the firstNameLabel to the first name of the given owner
        this.owner = owner;
        firstNameLabel.setText(owner.getFirstname());
    }

    public void onExitAction(ActionEvent event) {
        System.out.println("Clicked " + exitButton.getText());
        Platform.exit();
        System.exit(0);
    }

    public void setLoader(String fxmlFile) {
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getView(fxmlFile);
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(view);
        initialize();
    }

    public void handleLogoutButton(ActionEvent event) {
        closeStage(event);
        loadStage("/fxml/loginScreenFXML.fxml");
    }

    public void handleButton1Action(ActionEvent event) {
        System.out.println("You clicked: " + createWarehouseButton.getText());
        setLoader("createWarehouseFXML");
    }

    public void handleButton2Action(ActionEvent event) {
        System.out.println("You clicked: " + assignAgentsButton.getText());
    }

    public void handleButton3Action(ActionEvent event) {
        System.out.println("You clicked: " + reportsButton.getText());
    }
    public void closeStage(ActionEvent event){
        logoutButton = (Button) event.getSource();
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        stage.close();
    }
    public void loadStage(String fxmlPath){
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent loginScreenRoot = null;

        try {
            loginScreenRoot = (Parent) loader.load();
        }
        catch (IOException e) {
            e.printStackTrace();
            System.out.println("Couldn't load the stage.");
        }

        if (loginScreenRoot == null)
            throw new AssertionError();

        Scene loginScreenScene = new Scene(loginScreenRoot);
        Stage loginScreenStage = new Stage();
        loginScreenStage.setScene(loginScreenScene);
        loginScreenStage.initStyle(StageStyle.UNDECORATED);

        loginScreenStage.show();
    }

    public void handleProfileButton(ActionEvent event) {
        System.out.println("You clicked: " + profileButton.getText());
    }
}

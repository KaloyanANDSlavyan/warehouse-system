package system.application.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;

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

    public void setLoader(String fxmlFile){
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getView(fxmlFile);
        loaderPane.getChildren().clear();
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
    }
}

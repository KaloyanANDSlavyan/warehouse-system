package system.application.controllers;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class LoginScreenController {
    @FXML
    private AnchorPane anchorPane = null;
    @FXML
    private Button exitButton = null;
    @FXML
    private Button administratorButton = null;
    @FXML
    private Button ownerButton = null;
    @FXML
    private Button agentButton = null;
    @FXML
    private AnchorPane loaderPane = null;

    public void onExitClick(MouseEvent mouseEvent) {
        System.out.println("Clicked " + exitButton.getText());
        Platform.exit();
        System.exit(0);
    }
    public void setLoader(String fxmlFile){
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getView(fxmlFile);
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(view);
    }
    public void handleButton1Action(ActionEvent actionEvent) {
        System.out.println("You clicked " + administratorButton.getText());
        setLoader("administratorLoginFXML");
    }

    public void handleButton2Action(ActionEvent actionEvent) {
        System.out.println("You clicked " + ownerButton.getText());
        setLoader("ownerLoginFXML");
    }

    public void handleButton3Action(ActionEvent actionEvent) {
        System.out.println("You clicked " + agentButton.getText());
        setLoader("agentLoginFXML");
    }
}

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
    // test

   /* public void onLogInClick(MouseEvent mouseEvent) {
        System.out.println("Clicked " + logInButton.getText());

    }*/



    public void onExitClick(MouseEvent mouseEvent) {
        System.out.println("Clicked " + exitButton.getText());
        Platform.exit();
        System.exit(0);
    }

    public void handleButton1Action(ActionEvent actionEvent) {
        System.out.println("You clicked " + administratorButton.getText());
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getView("administratorLoginFXML");
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(view);
    }

    public void handleButton2Action(ActionEvent actionEvent) {
        System.out.println("You clicked " + ownerButton.getText());
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getView("ownerLoginFXML");
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(view);
    }

    public void handleButton3Action(ActionEvent actionEvent) {
        System.out.println("You clicked " + agentButton.getText());
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getView("agentLoginFXML");
        loaderPane.getChildren().clear();
        loaderPane.getChildren().add(view);
    }
}

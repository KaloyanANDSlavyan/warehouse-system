package system.application.controllers;

import javafx.application.Platform;
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
    private AnchorPane anchorPane2 = null;
    @FXML
    private Label helloLabel = null;
    @FXML
    private Button logInButton = null;
    @FXML
    private Button exitButton = null;
    @FXML
    private TextField usernameField = null;
    @FXML
    private PasswordField passwordField = null;

    public void onLogInClick(MouseEvent mouseEvent) {
        System.out.println("Clicked " + logInButton.getText());

    }

    public void onExitClick(MouseEvent mouseEvent) {
        System.out.println("Clicked " + exitButton.getText());
        Platform.exit();
        System.exit(0);
    }
}

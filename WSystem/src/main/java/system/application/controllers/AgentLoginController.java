package system.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class AgentLoginController {
    @FXML
    private TextField usernameField = null;
    @FXML
    private PasswordField passwordField = null;
    @FXML
    private Button loginButton = null;
    @FXML
    private Label userTypeLabel = null;

    public void setOnAction(ActionEvent actionEvent) {
        if (passwordField.getText().trim().equals("") && usernameField.getText().trim().equals(""))
            System.out.println("Text fields are empty!");
        else if (passwordField.getText().trim().equals("") || usernameField.getText().trim().equals(""))
            System.out.println("Text field is empty!");
        else {
            String username = usernameField.getText().trim();
            String password = passwordField.getText().trim();
            System.out.println("Username: " + username + " Password: " + password);
        }
    }
}

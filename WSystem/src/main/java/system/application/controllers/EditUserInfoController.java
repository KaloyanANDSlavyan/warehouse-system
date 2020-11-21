package system.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import system.backend.profiles.Agent;
import system.backend.profiles.Owner;

public class EditUserInfoController extends UserController {

    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField phoneNumberField;
    @FXML
    private Button exitButton;

    private Owner owner;
    private Agent agent;

    public void closeStage(ActionEvent event){
        exitButton = (Button) event.getSource();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }

    public void setDataForOwner(Owner owner){
        this.owner = owner;

        firstNameField.setText(owner.getFirstname());
        lastNameField.setText(owner.getLastname());
        usernameField.setText(owner.getUsername());
        passwordField.setText(owner.getPassword());
        emailField.setText(owner.getEmailAddress());
        phoneNumberField.setText(owner.getPhoneNumber());
    }
    public void setDataForAgent(Agent agent){
        this.agent = agent;

        firstNameField.setText(agent.getFirstname());
        lastNameField.setText(agent.getLastname());
        usernameField.setText(agent.getUsername());
        passwordField.setText(agent.getPassword());
        emailField.setText(agent.getEmailAddress());
        phoneNumberField.setText(agent.getPhoneNumber());

    }

    public void handleDoneButton(ActionEvent event) {
        System.out.println("Edit complete.");
        String editFirstName = firstNameField.getText();
        String editLastName = lastNameField.getText();
        String editUsername = usernameField.getText();
        String editPassword = passwordField.getText();
        String editEmail = emailField.getText();
        String editPhoneNumber = phoneNumberField.getText();

        static_firstName.setText(editFirstName);
        static_lastName.setText(editLastName);
        static_phoneNumber.setText(editPhoneNumber);
        closeStage(event);
    }

    public void handleExitButton(ActionEvent event) {
       closeStage(event);
    }
}

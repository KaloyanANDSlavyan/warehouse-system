package system.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import system.backend.WSystem;
import system.backend.profiles.Admin;
import system.backend.profiles.Profile;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class RegisterAgentController {
    @FXML
    private TextField usernameField = null;
    @FXML
    private TextField firstNameField = null;
    @FXML
    private TextField lastNameField = null;
    @FXML
    private PasswordField passwordField = null;
    @FXML
    private PasswordField confirmField = null;
    @FXML
    private TextField emailField = null;
    @FXML
    private TextField phoneNumberField = null;
    @FXML
    private Button registerButton = null;
    @FXML
    private Label userTypeLabel = null;

    public void registerButtonAction(ActionEvent actionEvent) {
        System.out.println("Register Button Clicked.");
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();

        if(firstName.equals("") || lastName.equals("")
                || username.equals("") || password.equals("")
                || confirmPassword.equals("") || phoneNumber.equals(""))
            System.out.println("Please fill all of the required data!");

        if(!password.equals(confirmPassword))
            System.out.println("Passwords don't match!");

        WSystem wSystem = WSystem.getInstance();
        Admin admin = (Admin)wSystem.getAdmin();

        Set<ConstraintViolation<Object>> cons =
                admin.createAgent(firstName, lastName, username, password, email, phoneNumber);

        List<String> firstname_con = new ArrayList<>();
        List<String> lastname_con = new ArrayList<>();
        List<String> username_con = new ArrayList<>();
        List<String> pass_con = new ArrayList<>();
        List<String> email_con = new ArrayList<>();
        List<String> phone_con = new ArrayList<>();

        for(ConstraintViolation<Object> con : cons) {
            if (con.getPropertyPath().toString().equals("firstname"))
                firstname_con.add(con.getMessage());
            else if(con.getPropertyPath().toString().equals("lastname"))
                lastname_con.add(con.getMessage());
            else if (con.getPropertyPath().toString().equals("username"))
                username_con.add(con.getMessage());
            else if (con.getPropertyPath().toString().equals("password"))
                pass_con.add(con.getMessage());
            else if (con.getPropertyPath().toString().equals("emailAddress"))
                email_con.add(con.getMessage());
            else if (con.getPropertyPath().toString().equals("phoneNumber"))
                phone_con.add(con.getMessage());
        }

        System.out.println("\n\n\nShow messages:");
        System.out.println("First Name Violations:");
        for(String message : firstname_con) {
            System.out.println(message);
        }
        System.out.println("\n");
        System.out.println("Last Name Violations:");
        for(String message : lastname_con) {
            System.out.println(message);
        }
        System.out.println("\n");
        System.out.println("Username Violations:");
        for(String message : username_con) {
            System.out.println(message);
        }
        System.out.println("\n");
        System.out.println("Password Violations:");
        for(String message : pass_con) {
            System.out.println(message);
        }
        System.out.println("\n");
        System.out.println("Email Violations:");
        for(String message : email_con) {
            System.out.println(message);
        }
        System.out.println("\n");
        System.out.println("Phone Violations:");
        for(String message : phone_con) {
            System.out.println(message);
        }
        System.out.println("\n");
    }
}
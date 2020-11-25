package system.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import system.backend.WSystem;
import system.backend.profiles.Admin;
import system.backend.profiles.Profile;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class RegisterAgentController extends AdminPanelController implements Initializable {
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
    @FXML
    private Label successLabel = null;
    @FXML
    private VBox consVbox1 = null;
    @FXML
    private VBox consVbox2 = null;
    @FXML
    private Label violationsLabel = null;
    @FXML
    private Hyperlink why1 = null;
    @FXML
    private Hyperlink why2 = null;
    private WSystem wSystem = WSystem.getInstance();

    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String confirmPassword;
    private String email;
    private String phoneNumber;

    private List<String> firstname_con = new ArrayList<>();
    private List<String> lastname_con = new ArrayList<>();
    private List<String> username_con = new ArrayList<>();
    private List<String> pass_con = new ArrayList<>();
    private List<String> email_con = new ArrayList<>();
    private List<String> phone_con = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        consVbox1.setMinWidth(Region.USE_COMPUTED_SIZE);
        consVbox2.setMinWidth(Region.USE_COMPUTED_SIZE);
        consVbox1.setPrefWidth(Region.USE_COMPUTED_SIZE);
        consVbox2.setPrefWidth(Region.USE_COMPUTED_SIZE);
        consVbox1.setMaxWidth(Region.USE_PREF_SIZE);
        consVbox2.setMaxWidth(Region.USE_PREF_SIZE);

        consVbox1.setMinHeight(Region.USE_COMPUTED_SIZE);
        consVbox2.setMinHeight(Region.USE_COMPUTED_SIZE);
        consVbox1.setPrefHeight(Region.USE_COMPUTED_SIZE);
        consVbox2.setPrefHeight(Region.USE_COMPUTED_SIZE);
        consVbox1.setMaxHeight(Region.USE_PREF_SIZE);
        consVbox2.setMaxHeight(Region.USE_PREF_SIZE);

    }

    public void fillConsBox1(String message) {
        Label consLabel = new Label();
        consLabel.setText(message);
        consLabel.setStyle("-fx-text-fill: red; -fx-font-size: 11px");
        consVbox1.getChildren().add(consLabel);
        System.out.println(message);
        why1.setVisible(true);
    }

    public void fillConsBox2(String message){
        Label consLabel = new Label();
        consLabel.setText(message);
        consLabel.setStyle("-fx-text-fill: red; -fx-font-size: 11px");
        consVbox2.getChildren().add(consLabel);
        System.out.println(message);
        why2.setVisible(true);
    }

    public void registerButtonAction(ActionEvent actionEvent) {
        consVbox1.getChildren().clear();
        consVbox2.getChildren().clear();
        why1.setVisible(false);
        why2.setVisible(false);

        System.out.println("Register Button Clicked.");
        getData();

        if(allDataFilled()) {
            if (passwordMatch())
                registerOrViolate();
            else passwordMatchMessage();
        } else fillDataMessage();
    }

    public void registerOrViolate(){

        firstname_con.clear();
        lastname_con.clear();
        username_con.clear();
        pass_con.clear();
        email_con.clear();
        phone_con.clear();

        Admin admin = (Admin) wSystem.getAdmin();

        Set<ConstraintViolation<Object>> cons =
                admin.createAgent(firstName, lastName, username, password, email, phoneNumber);

        if (cons.isEmpty()) {
            violationsLabel.setVisible(false);
            successLabel.setVisible(true);
            profilesState();    // checks if there are profiles. true => sets button visible. false => sets button invisible
        } else {
            violationsLabel.setVisible(true);
            successLabel.setVisible(false);
        }

        addConstraints(cons);
        showMessages();
    }

    public boolean allDataFilled(){

        if (firstName.equals("") || lastName.equals("") || username.equals("")
                || password.equals("") || confirmPassword.equals("") || phoneNumber.equals(""))
            return false;

        return true;
    }

    public void fillDataMessage(){
        successLabel.setVisible(false);
        violationsLabel.setVisible(true);
        String message = "Please fill all of the required data!";

        if(firstName.equals("") || lastName.equals("") || username.equals(""))
            fillConsBox1(message);
        if(password.equals("") || confirmPassword.equals("") || phoneNumber.equals(""))
            fillConsBox2(message);
    }

    public boolean passwordMatch(){
        if (!password.equals(confirmPassword))
            return false;

        return true;
    }

    public void passwordMatchMessage(){
        String message = "Passwords don't match!";
        System.out.println("Passwords don't match!");
        fillConsBox2(message);
        successLabel.setVisible(false);
        violationsLabel.setVisible(true);
    }

    public void addConstraints(Set<ConstraintViolation<Object>> cons){
        for (ConstraintViolation<Object> con : cons) {
            if (con.getPropertyPath().toString().equals("firstname"))
                firstname_con.add(con.getMessage());
            else if (con.getPropertyPath().toString().equals("lastname"))
                lastname_con.add(con.getMessage());
            else if (con.getPropertyPath().toString().equals("username")) {
                if (con.getMessage().equals(" is already taken")) {
                    username_con.add("Username" + con.getMessage());
                } else username_con.add(con.getMessage());
            }
            else if (con.getPropertyPath().toString().equals("password"))
                pass_con.add(con.getMessage());
            else if (con.getPropertyPath().toString().equals("emailAddress")) {
                if (con.getMessage().equals(" is already taken"))
                    email_con.add("Email address" + con.getMessage());
                else email_con.add(con.getMessage());
            }
            else if (con.getPropertyPath().toString().equals("phoneNumber")) {
                if (con.getMessage().equals(" is already taken"))
                    phone_con.add("Phone number" + con.getMessage());
                else phone_con.add(con.getMessage());
            }
        }
    }

    public void showMessages(){
        System.out.println("\n\n\nShow messages:");
        System.out.println("First Name Violations:");
        for (String message : firstname_con) {
            if (!message.isEmpty()) {
                fillConsBox1(message);
            }
        }
        System.out.println("\n");
        System.out.println("Last Name Violations:");
        for (String message : lastname_con) {
            if (!message.isEmpty()) {
                fillConsBox1(message);
            }
        }
        System.out.println("\n");
        System.out.println("Username Violations:");
        for (String message : username_con) {
            if (!message.isEmpty()) {
                fillConsBox1(message);
            }
        }
        System.out.println("\n");
        System.out.println("Password Violations:");
        for (String message : pass_con) {
            if (!message.isEmpty()) {
                fillConsBox2(message);
            }
        }
        System.out.println("\n");
        System.out.println("Email Violations:");
        for (String message : email_con) {
            if (!message.isEmpty()) {
                fillConsBox2(message);
            }
        }
        System.out.println("\n");
        System.out.println("Phone Violations:");
        for (String message : phone_con) {
            if (!message.isEmpty()) {
                fillConsBox2(message);
            }
        }
        System.out.println("\n");
    }

    public void getData(){
        firstName = firstNameField.getText().trim();
        lastName = lastNameField.getText().trim();
        username = usernameField.getText().trim();
        password = passwordField.getText();
        confirmPassword = confirmField.getText();
        email = emailField.getText();
        phoneNumber = phoneNumberField.getText();
    }

    public void showConsPane1(MouseEvent mouseEvent) { consVbox1.setVisible(true); }

    public void hideConsPane1(MouseEvent mouseEvent) { consVbox1.setVisible(false); }

    public void showConsPane2(MouseEvent mouseEvent) { consVbox2.setVisible(true); }

    public void hideConsPane2(MouseEvent mouseEvent) { consVbox2.setVisible(false); }

}
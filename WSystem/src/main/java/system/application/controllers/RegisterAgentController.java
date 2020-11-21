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
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String username = usernameField.getText().trim();
        String password = passwordField.getText();
        String confirmPassword = confirmField.getText();
        String email = emailField.getText();
        String phoneNumber = phoneNumberField.getText();

        if(firstName.equals("") || lastName.equals("") || username.equals("") || password.equals("") || confirmPassword.equals("") || phoneNumber.equals("")){
            System.out.println("Please fill all of the required data!");
        }

        if(!password.equals(confirmPassword)){
            String message = "Passwords don't match!";
            System.out.println("Passwords don't match!");
            fillConsBox2(message);
        }

        Admin admin = (Admin)wSystem.getAdmin();

        Set<ConstraintViolation<Object>> cons =
                admin.createAgent(firstName, lastName, username, password, email, phoneNumber);

        if (cons.isEmpty()) {
            violationsLabel.setVisible(false);
            successLabel.setVisible(true);
            profilesState();  // checks if there are profiles. true => sets button visible. false => sets button invisible
        }else
            violationsLabel.setVisible(true);

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
            if (!message.isEmpty()) {
              fillConsBox1(message);
            }
        }
        System.out.println("\n");
        System.out.println("Last Name Violations:");
        for(String message : lastname_con) {
            if (!message.isEmpty()) {
               fillConsBox1(message);
            }
        }
        System.out.println("\n");
        System.out.println("Username Violations:");
        for(String message : username_con) {
            if (!message.isEmpty()) {
               fillConsBox1(message);
            }
        }
        System.out.println("\n");
        System.out.println("Password Violations:");
        for(String message : pass_con) {
            if (!message.isEmpty()) {
               fillConsBox2(message);
            }
        }
        System.out.println("\n");
        System.out.println("Email Violations:");
        for(String message : email_con) {
            if (!message.isEmpty()) {
              fillConsBox2(message);
            }
        }
        System.out.println("\n");
        System.out.println("Phone Violations:");
        for(String message : phone_con) {
            if (!message.isEmpty()) {
               fillConsBox2(message);
            }
        }
        System.out.println("\n");
    }

    public void showConsPane1(MouseEvent mouseEvent) { consVbox1.setVisible(true); }

    public void hideConsPane1(MouseEvent mouseEvent) { consVbox1.setVisible(false); }

    public void showConsPane2(MouseEvent mouseEvent) { consVbox2.setVisible(true); }

    public void hideConsPane2(MouseEvent mouseEvent) { consVbox2.setVisible(false); }

}
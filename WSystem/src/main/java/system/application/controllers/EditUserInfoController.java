package system.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import system.backend.WSystem;
import system.backend.others.Indicator;
import system.backend.profiles.Agent;
import system.backend.profiles.Owner;
import system.backend.profiles.Profile;
import system.backend.services.AgentValidation;
import system.backend.services.OwnerValidation;
import system.backend.services.ValidationService;
import system.backend.services.WarehouseValidation;
import system.backend.validators.indicators.ValidationIndicator;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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

    private String editFirstName;
    private String editLastName;
    private String editUsername;
    private String editPassword;
    private String editEmail;
    private String editPhoneNumber;

    private String oldFirstName;
    private String oldLastName;
    private String oldUsername;
    private String oldPassword;
    private String oldEmail;
    private String oldPhone;

    private List<String> firstname_con = new ArrayList<>();
    private List<String> lastname_con = new ArrayList<>();
    private List<String> username_con = new ArrayList<>();
    private List<String> pass_con = new ArrayList<>();
    private List<String> email_con = new ArrayList<>();
    private List<String> phone_con = new ArrayList<>();

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

        WSystem wSystem = WSystem.getInstance();

        getNewData();

        if(owner != null){
            OwnerValidation ownerValidation = wSystem.getOwnerValidation();
            Indicator.getInstance().setValidationIndicator(ValidationIndicator.OWNER);
            ownerValidation.setIgnoreThisID(owner.getID());

            getOldOwnerData();
            setNewOwnerData();

            Set<ConstraintViolation<Owner>> cons = ownerValidation.validate(owner);
            ownerValidation.setIgnoreThisID(null);

            if(cons.isEmpty())
                WSystem.getInstance().getOwnerDAO().update(owner);
            else {
                setOldOwnerData();

                System.out.println("Couldn't update");
                addOwnerConstraints(cons);
                showMessages();
            }

            static_firstName.setText(owner.getFirstname());
            static_lastName.setText(owner.getLastname());
            static_phoneNumber.setText(owner.getPhoneNumber());
        } else {
            AgentValidation agentValidation = wSystem.getAgentValidation();
            Indicator.getInstance().setValidationIndicator(ValidationIndicator.AGENT);
            agentValidation.setIgnoreThisID(agent.getID());

            getOldAgentData();
            setNewAgentData();

            Set<ConstraintViolation<Agent>> cons = agentValidation.validate(agent);
            agentValidation.setIgnoreThisID(null);

            if(cons.isEmpty())
                WSystem.getInstance().getAgentDAO().update(agent);
            else {
                setOldAgentData();

                System.out.println("Couldn't update");
                addAgentConstraints(cons);
                showMessages();
            }
            static_firstName.setText(agent.getFirstname());
            static_lastName.setText(agent.getLastname());
            static_phoneNumber.setText(agent.getPhoneNumber());
        }
        closeStage(event);
    }

    public void handleExitButton(ActionEvent event) {
       closeStage(event);
    }

    public void getNewData(){
        editFirstName = firstNameField.getText();
        editLastName = lastNameField.getText();
        editUsername = usernameField.getText();
        editPassword = passwordField.getText();
        editEmail = emailField.getText();
        editPhoneNumber = phoneNumberField.getText();
    }

    public void getOldOwnerData(){
        oldFirstName = owner.getFirstname();
        oldLastName = owner.getLastname();
        oldUsername = owner.getUsername();
        oldPassword = owner.getPassword();
        oldEmail = owner.getEmailAddress();
        oldPhone = owner.getPhoneNumber();
    }

    public void getOldAgentData(){
        oldFirstName = agent.getFirstname();
        oldLastName = agent.getLastname();
        oldUsername = agent.getUsername();
        oldPassword = agent.getPassword();
        oldEmail = agent.getEmailAddress();
        oldPhone = agent.getPhoneNumber();
    }

    public void setNewOwnerData(){
        owner.setFirstname(editFirstName);
        owner.setLastname(editLastName);
        owner.setUsername(editUsername);
        owner.setPassword(editPassword);
        owner.setEmailAddress(editEmail);
        owner.setPhoneNumber(editPhoneNumber);
    }

    public void setNewAgentData(){
        agent.setFirstname(editFirstName);
        agent.setLastname(editLastName);
        agent.setUsername(editUsername);
        agent.setPassword(editPassword);
        agent.setEmailAddress(editEmail);
        agent.setPhoneNumber(editPhoneNumber);
    }

    public void setOldOwnerData(){
        owner.setFirstname(oldFirstName);
        owner.setLastname(oldLastName);
        owner.setUsername(oldUsername);
        owner.setPassword(oldPassword);
        owner.setEmailAddress(oldEmail);
        owner.setPhoneNumber(oldPhone);
    }

    public void setOldAgentData(){
        agent.setFirstname(oldFirstName);
        agent.setLastname(oldLastName);
        agent.setUsername(oldUsername);
        agent.setPassword(oldPassword);
        agent.setEmailAddress(oldEmail);
        agent.setPhoneNumber(oldPhone);
    }

    public void addOwnerConstraints(Set<ConstraintViolation<Owner>> cons){

        for (ConstraintViolation<Owner> con : cons) {
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

    public void addAgentConstraints(Set<ConstraintViolation<Agent>> cons){

        for (ConstraintViolation<Agent> con : cons) {
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
//        if(!firstname_con.isEmpty() && (!lastname_con.isEmpty() || !username_con.isEmpty()))
//            firstname_con.add("\n");
//
//        if(!lastname_con.isEmpty() && !username_con.isEmpty())
//            lastname_con.add("\n");
//
//        if(!pass_con.isEmpty() && (!email_con.isEmpty() || !phone_con.isEmpty()))
//            pass_con.add("\n");
//
//        if(!email_con.isEmpty() && !phone_con.isEmpty())
//            email_con.add("\n");

        System.out.println("\n\n\nShow messages:");
        System.out.println("First Name Violations:");

        for (String message : firstname_con) {
            if (!message.isEmpty()) {
                //fillConsBox1(message);
                System.out.println(message);
            }
        }
        System.out.println("\n");
        System.out.println("Last Name Violations:");
        for (String message : lastname_con) {
            if (!message.isEmpty()) {
                //fillConsBox1(message);
                System.out.println(message);
            }
        }
        System.out.println("\n");
        System.out.println("Username Violations:");
        for (String message : username_con) {
            if (!message.isEmpty()) {
                //fillConsBox1(message);
                System.out.println(message);
            }
        }
        System.out.println("\n");
        System.out.println("Password Violations:");
        for (String message : pass_con) {
            if (!message.isEmpty()) {
                //fillConsBox2(message);
                System.out.println(message);
            }
        }
        System.out.println("\n");
        System.out.println("Email Violations:");
        for (String message : email_con) {
            if (!message.isEmpty()) {
                //fillConsBox2(message);
                System.out.println(message);
            }
        }
        System.out.println("\n");
        System.out.println("Phone Violations:");
        for (String message : phone_con) {
            if (!message.isEmpty()) {
                //fillConsBox2(message);
                System.out.println(message);
            }
        }
        System.out.println("\n");
    }
}

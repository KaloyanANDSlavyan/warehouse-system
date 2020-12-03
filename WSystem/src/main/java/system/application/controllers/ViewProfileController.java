package system.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import system.application.others.DataRetriever;
import system.application.others.MessageService;
import system.backend.dataholders.OwnerDataHolder;
import system.backend.profiles.Owner;
import system.backend.profiles.ProfileManager;

import javax.validation.ConstraintViolation;
import javax.xml.crypto.Data;
import java.net.URL;
import java.util.*;

public class ViewProfileController extends AbstractController implements Initializable {
    @FXML
    private VBox editVbox;
    @FXML
    private VBox profileVbox;
    @FXML
    private AnchorPane anchorPane = null;
    @FXML
    private Label firstNameLabel = null;
    @FXML
    private TextField firstNameField;
    @FXML
    private Label lastNameLabel = null;
    @FXML
    private TextField lastNameField;
    @FXML
    private Label usernameLabel = null;
    @FXML
    private TextField usernameField;
    @FXML
    private Label emailLabel = null;
    @FXML
    private TextField emailField;
    @FXML
    private Label phoneLabel = null;
    @FXML
    private TextField phoneField;
    @FXML
    private Button editButton = null;
    @FXML
    private Hyperlink changePassword = null;
    private Owner owner;
    private OwnerDataHolder ownerDataHolder = OwnerDataHolder.getInstance();

    private Map<String, String> newData = new HashMap<>();
    private Map<String, String> oldData = new HashMap<>();
    private Set<ConstraintViolation<Object>> cons = new LinkedHashSet<>();

    private List<String> firstname_con = new ArrayList<>();
    private List<String> lastname_con = new ArrayList<>();
    private List<String> username_con = new ArrayList<>();
    private List<String> pass_con = new ArrayList<>();
    private List<String> email_con = new ArrayList<>();
    private List<String> phone_con = new ArrayList<>();

    ProfileManager<Owner> ownerProfileManager = new ProfileManager<>();
    DataRetriever dataRetriever = DataRetriever.getInstance();
    MessageService messageService = MessageService.getInstance();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.owner = ownerDataHolder.getOwner();

        firstNameLabel.setText(owner.getFirstname());
        firstNameField.setText(owner.getFirstname());
        lastNameLabel.setText(owner.getLastname());
        lastNameField.setText(owner.getLastname());
        usernameLabel.setText(owner.getUsername());
        usernameField.setText(owner.getUsername());
        emailLabel.setText(owner.getEmailAddress());
        emailField.setText(owner.getEmailAddress());
        phoneLabel.setText(owner.getPhoneNumber());
        phoneField.setText(owner.getPhoneNumber());
    }
    public void setLoader(String fxmlFile){
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getView(fxmlFile);

        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(view);
    }

    public void handleEditButton(ActionEvent event) {

        System.out.println("You clicked: " + editButton.getText());
       // setLoader("changePasswordFXML");
        editVbox.setVisible(true);
        profileVbox.setVisible(false);
    }

    public void handleChangePass(ActionEvent event) {
        System.out.println("You clicked: " + changePassword.getText());
        setLoader("changePasswordFXML");
    }

    public void handleDoneButton(ActionEvent event) {
        editVbox.setVisible(false);
        profileVbox.setVisible(true);

        String firstName = firstNameField.getText();
        String lastName = lastNameField.getText();
        String username = usernameField.getText();
        String email = emailField.getText();
        String phone = phoneField.getText();

//        firstNameLabel.setText(firstNameField.getText());
//        lastNameLabel.setText(lastNameField.getText());
//        usernameLabel.setText(usernameField.getText());
//        emailLabel.setText(emailField.getText());
//        phoneLabel.setText(phoneField.getText());

        firstNameLabel.setText(owner.getFirstname());
        lastNameLabel.setText(owner.getLastname());
        usernameLabel.setText(owner.getUsername());
        emailLabel.setText(owner.getEmailAddress());
        phoneLabel.setText(owner.getPhoneNumber());

        firstname_con.clear();
        lastname_con.clear();
        username_con.clear();
        email_con.clear();
        phone_con.clear();

        cons.clear();
        oldData.clear();
        newData.clear();

        dataRetriever.getEditDataWithoutPass(this, newData);
        ownerProfileManager.getProfileDataWithoutPass(owner, oldData);
        cons = ownerProfileManager.updateProfileWithoutPass(owner, Owner.class, newData);

        if(cons.isEmpty()) {
            System.out.println("Owner successfully updated!!!!!");
        }
        else {
            System.out.println("Imash greshka nqkude WE :@ @: ");
            ownerProfileManager.setProfileDataWithoutPass(owner, oldData);
            addConstraints(cons);
            showMessages();
        }
        //ownerDataHolder.setOwner(owner);
        System.out.println("owner phone:" + owner.getPhoneNumber());

        firstNameLabel.setText(owner.getFirstname());
        lastNameLabel.setText(owner.getLastname());
        usernameLabel.setText(owner.getUsername());
        emailLabel.setText(owner.getEmailAddress());
        phoneLabel.setText(owner.getPhoneNumber());
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

    public void handleCancelButton(ActionEvent event) {
        editVbox.setVisible(false);
        profileVbox.setVisible(true);
    }

    public TextField getFirstNameField() {
        return firstNameField;
    }

    public TextField getLastNameField() {
        return lastNameField;
    }

    public TextField getUsernameField() {
        return usernameField;
    }

    public TextField getEmailField() {
        return emailField;
    }

    public TextField getPhoneNumberField() {
        return phoneField;
    }
}

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
import system.backend.dataholders.OwnerDataHolder;
import system.backend.profiles.Owner;

import java.net.URL;
import java.util.ResourceBundle;

public class ViewProfileController implements Initializable {
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

        firstNameLabel.setText(firstNameField.getText());
        lastNameLabel.setText(lastNameField.getText());
        usernameLabel.setText(usernameField.getText());
        emailLabel.setText(emailField.getText());
        phoneLabel.setText(phoneField.getText());
    }

    public void handleCancelButton(ActionEvent event) {
        editVbox.setVisible(false);
        profileVbox.setVisible(true);
    }
}

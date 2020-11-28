package system.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;

public class ChangePassController implements Initializable {
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private PasswordField oldPassField;
    @FXML
    private PasswordField newPassField;
    @FXML
    private PasswordField confirmNewPassField;
    @FXML
    private Button changePassButton;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
    public void setLoader(String fxmlFile){
        FxmlLoader object = new FxmlLoader();
        AnchorPane view = object.getView(fxmlFile);
        anchorPane.getChildren().clear();
        anchorPane.getChildren().add(view);
    }

    public void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked " + changePassButton.getText());
        String oldPass = oldPassField.getText();
        String newPass = newPassField.getText();
        String confirmNewPass = confirmNewPassField.getText();
    }

    public void handleButton2Action(ActionEvent event) {
        System.out.println("Redirecting to Profile");
        setLoader("viewProfileFXML");
    }
}

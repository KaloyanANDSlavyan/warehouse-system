package system.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.layout.AnchorPane;
import system.backend.WSystem;
import system.backend.dao.DAO;
import system.backend.dao.MainDAO;
import system.backend.dataholders.OwnerDataHolder;
import system.backend.profiles.Owner;
import system.backend.services.ValidationService;

import javax.validation.ConstraintViolation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

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

    private ValidationService validationService = ValidationService.getInstance();

    private List<String> pass_con = new ArrayList<>();

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

        pass_con.clear();

        System.out.println("You clicked " + changePassButton.getText());
        String oldPass = oldPassField.getText();
        String newPass = newPassField.getText();
        String confirmNewPass = confirmNewPassField.getText();

        WSystem wSystem = WSystem.getInstance();

        if(oldPass.equals("") || newPass.equals("") || confirmNewPass.equals(""))
            System.out.println("Please fill all of the required data!");
        else{
            if(!newPass.equals(confirmNewPass))
                System.out.println("Passwords don't match!");
            else{
                try {
                    Owner owner = wSystem.findOwnerBy1Value(oldPass);

                    System.out.println("oldPass: " + oldPass);
                    System.out.println("owner pass: " + owner.getPassword());
                    owner.setPassword(newPass);

                    Set<ConstraintViolation<Object>> cons = validationService.validateProperty(owner, "password");

                    if(cons.isEmpty()){
                        DAO<Owner, String> ownerDAO = new MainDAO<>();
                        ownerDAO.updateColumn(Owner.class, "password", newPass, oldPass);
                        System.out.println("Password successfully changed.");
                    } else{
                        System.out.println("There are violations.");
                        addConstraints(cons);
                        showMessages();
                    }
                } catch (Exception e){
                    System.out.println("The current password is incorrect!");
                }
            }
        }
    }

    public void addConstraints(Set<ConstraintViolation<Object>> cons){

        for (ConstraintViolation<Object> con : cons)
            if (con.getPropertyPath().toString().equals("password"))
                pass_con.add(con.getMessage());
    }

    public void showMessages(){
        System.out.println("\n\n\nShow messages:");

        System.out.println("\n");
        System.out.println("Password Violations:");
        for (String message : pass_con) {
            if (!message.isEmpty()) {
                //fillConsBox1(message);
                System.out.println(message);
            }
        }
        System.out.println("\n");
    }


    public void handleButton2Action(ActionEvent event) {
        System.out.println("Redirecting to Profile");
        setLoader("viewProfileFXML");
    }
}

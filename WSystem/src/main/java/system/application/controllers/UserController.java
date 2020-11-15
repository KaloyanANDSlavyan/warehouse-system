package system.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import system.backend.profiles.Agent;
import system.backend.profiles.Owner;

public class UserController {
    @FXML
    private Label firstNameLabel;

    @FXML
    private Label lastNameLabel;

    @FXML
    private Label phoneLabel;

    private Owner owner;
    private Agent agent;

    public void setOwner(Owner owner){
        this.owner = owner;

        firstNameLabel.setText(owner.getFirstname());
        lastNameLabel.setText(owner.getLastname());
        phoneLabel.setText(owner.getPhoneNumber());

    }
    public void setAgent(Agent agent){
        this.agent = agent;

        firstNameLabel.setText(agent.getFirstname());
        lastNameLabel.setText(agent.getLastname());
        phoneLabel.setText(agent.getPhoneNumber());
    }

    public void handleDeleteButton(ActionEvent event) {
        System.out.println("Delete button clicked");

    }

    public void handleEditButton(ActionEvent event) {
        System.out.println("Edit button clicked");
    }
}

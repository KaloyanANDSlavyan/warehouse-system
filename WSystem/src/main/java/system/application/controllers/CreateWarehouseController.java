package system.application.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import system.backend.WSystem;
import system.backend.dataholders.OwnerDataHolder;
import system.backend.others.Warehouse;
import system.backend.profiles.Owner;

import javax.validation.ConstraintViolation;
import java.util.Set;

public class CreateWarehouseController {

    @FXML
    private TextField sizeField = null;

    @FXML
    private ComboBox<String> typeBox = null;    // ComboBox for Type of Warehouse
    @FXML
    private ListView<String> stockTypeView = null;  // ListView for Type of Stock
    private ObservableList<String>  comboBoxItems = FXCollections.observableArrayList();    // ComboBox Items
    private ObservableList<String> foodsList = FXCollections.observableArrayList();    // ListView items
    private ObservableList<String> techList = FXCollections.observableArrayList();    // ListView items
    private ObservableList<String> militaryList = FXCollections.observableArrayList();    // ListView items
    @FXML
    private TextField temperatureField = null;
    @FXML
    private Label successLabel = null;
    private WSystem wSystem = WSystem.getInstance();

    private Owner owner;

    public void initialize(){
        comboBoxItems.addAll("Food Industry", "Tech Industry", "Military");    // Adds items to the list, which will contain different warehouse types
        typeBox.setItems(comboBoxItems);    // sets the list to the ComboBox
        stockTypeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Enables multiple choice for the ListView

        OwnerDataHolder ownerDataHolder = OwnerDataHolder.getInstance();
        owner = ownerDataHolder.getOwner();
    }

    public void addToListView(){    // Method which adds items to the ListView for given index of ComboBox
        int index = typeBox.getSelectionModel().getSelectedIndex();
        if (index == 0) {
            stockTypeView.getItems().clear();
            foodsList.addAll("Vegetables", "Fruits", "Seeds");
            stockTypeView.setItems(foodsList);
        }
        else if(index == 1){
            stockTypeView.getItems().clear();
            techList.addAll("Computers", "Smartphones", "Chargers");
            stockTypeView.setItems(techList);
        }
        else{
            stockTypeView.getItems().clear();
            militaryList.addAll("Explosives", "Guns", "Military Equipment");
            stockTypeView.setItems(militaryList);
        }
    }

    public void createButtonAction(ActionEvent event) {
        System.out.println("Create Button Clicked.");
        String category = typeBox.getSelectionModel().getSelectedItem();

        ObservableList<String> stockType = stockTypeView.getSelectionModel().getSelectedItems();  // Selected items of ListView
        for (String o : stockType){
            System.out.println("o = " + o );
        }
        double size = Double.parseDouble(sizeField.getText());
        double temperature = Double.parseDouble(temperatureField.getText());

        Set<ConstraintViolation<Object>> cons = owner.createWarehouse(category, size, temperature, stockType);
    }

    public void handleComboBox(ActionEvent event) {
        addToListView();
    }
}

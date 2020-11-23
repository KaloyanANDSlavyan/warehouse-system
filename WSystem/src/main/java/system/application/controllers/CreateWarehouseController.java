package system.application.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import system.backend.WSystem;

public class CreateWarehouseController {

    @FXML
    private TextField sizeField = null;

    @FXML
    private ComboBox<String> typeBox = null;    // ComboBox for Type of Warehouse
    @FXML
    private ListView<String> stockTypeView = null;  // ListView for Type of Stock
    private ObservableList<String>  comboBoxItems = FXCollections.observableArrayList();    // ComboBox Items
    private ObservableList<String>  list1 = FXCollections.observableArrayList();    // ListView items
    private ObservableList<String>  list2 = FXCollections.observableArrayList();    // ListView items
    @FXML
    private TextField temperatureField = null;
    @FXML
    private Label successLabel = null;
    private WSystem wSystem = WSystem.getInstance();

    public void initialize(){
        comboBoxItems.addAll("test1", "test2", "test3");    // Adds items to the list, which will contain different warehouse types
        typeBox.setItems(comboBoxItems);    // sets the list to the ComboBox
        stockTypeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Enables multiple choice for the ListView
    }

    public void addToListView(){    // Method which adds items to the ListView for given index of ComboBox
        int index = typeBox.getSelectionModel().getSelectedIndex();
        if (index == 1) {
            stockTypeView.getItems().clear();
            list1.addAll("a1", "a2", "a3");
            stockTypeView.setItems(list1);
        }
        else{
            stockTypeView.getItems().clear();
            list2.addAll("b1", "b2", "b3");
            stockTypeView.setItems(list2);
        }
    }

    public void createButtonAction(ActionEvent event) {
        System.out.println("Create Button Clicked.");
        String comboBoxItem = typeBox.getSelectionModel().getSelectedItem();

        ObservableList<String> selectedItems = stockTypeView.getSelectionModel().getSelectedItems();  // Selected items of ListView
        for (String o : selectedItems){
            System.out.println("o = " + o );
        }
        String size = sizeField.getText();
        String temperature = temperatureField.getText();
    }

    public void handleComboBox(ActionEvent event) {
        addToListView();
    }
}

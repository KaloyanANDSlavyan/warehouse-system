package system.application.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import system.backend.WSystem;
import system.backend.dataholders.OwnerDataHolder;
import system.backend.others.StockType;
import system.backend.others.Warehouse;
import system.backend.profiles.Owner;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CreateWarehouseController extends OwnerPanelController {
    @FXML
    private AnchorPane loaderPane;
    @FXML
    private TextField sizeField = null;
    @FXML
    private Button createButton;
    @FXML
    private Button exitButton;
    @FXML
    private Button doneButton;
    @FXML
    private Label titleLabel;

    @FXML
    private ComboBox<String> typeBox = null;    // ComboBox for Type of Warehouse
    @FXML
    private ListView<String> stockTypeView = null;  // ListView for Type of Stock
    private ObservableList<String>  comboBoxItems = FXCollections.observableArrayList();    // ComboBox Items
    private ObservableList<String> foodsList = FXCollections.observableArrayList();    // ListView items
    private ObservableList<String> techList = FXCollections.observableArrayList();    // ListView items
    private ObservableList<String> militaryList = FXCollections.observableArrayList();    // ListView items
    private ObservableList<String> stockType = FXCollections.observableArrayList();
    @FXML
    private TextField temperatureField = null;
    @FXML
    private Label successLabel = null;
    @FXML
    private HBox Hbox = null;
    @FXML
    private VBox consVbox = null;
    private WSystem wSystem = WSystem.getInstance();
    private Warehouse wh;

    private Owner owner;

    private List<String> size_con = new ArrayList<>();
    private List<String> category_con = new ArrayList<>();
    private List<String> temperature_con = new ArrayList<>();

    public void initialize(){
        comboBoxItems.addAll("Food Industry", "Tech Industry", "Military");    // Adds items to the list, which will contain different warehouse types
        typeBox.setItems(comboBoxItems);    // sets the list to the ComboBox
        stockTypeView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE); // Enables multiple choice for the ListView

        OwnerDataHolder ownerDataHolder = OwnerDataHolder.getInstance();
        owner = ownerDataHolder.getOwner();

        consVbox.setMinWidth(Region.USE_COMPUTED_SIZE);
        consVbox.setPrefWidth(Region.USE_COMPUTED_SIZE);
        consVbox.setMaxWidth(Region.USE_PREF_SIZE);
        consVbox.setMinHeight(Region.USE_COMPUTED_SIZE);
        consVbox.setPrefHeight(Region.USE_COMPUTED_SIZE);
        consVbox.setMaxHeight(Region.USE_PREF_SIZE);
        exitButton.setVisible(false);
    }
    public void setData(Warehouse wh){      // (EDIT WAREHOUSE) sets fields and boxes to the values of warehouse
        loaderPane.setStyle("-fx-background-color: #202245;\n" + "-fx-border-color: #14152b;\n" +
                "-fx-border-width: 1px;");
        titleLabel.setText("EDIT");
        this.wh = wh;
        createButton.setVisible(false);
        exitButton.setVisible(true);
        doneButton.setVisible(true);
       sizeField.setText(Double.toString(wh.getSize()));
       typeBox.getSelectionModel().select(wh.getCategory());
        addToListView();
        for (StockType st: wh.getStockTypes()) {
            stockTypeView.getSelectionModel().select(st.getType());
        }
       temperatureField.setText(Double.toString(wh.getTemperature()));

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

    public void fillConsBox(String message) {
        Label consLabel = new Label();
        consLabel.setText(message);
        consLabel.setStyle("-fx-text-fill: red; -fx-font-size: 11px");
        consVbox.getChildren().add(consLabel);
        System.out.println(message);
        Hbox.setVisible(true);
    }

    public void createButtonAction(ActionEvent event) {
        System.out.println("Create Button Clicked.");
        consVbox.getChildren().clear();
        successLabel.setVisible(false);
        Hbox.setVisible(false);
        String category = typeBox.getSelectionModel().getSelectedItem();

        stockType = stockTypeView.getSelectionModel().getSelectedItems();  // Selected items of ListView
        for (String o : stockType) {
            System.out.println("o = " + o);
        }
        double size;
        double temperature;

        if (sizeField.getText().isEmpty() || temperatureField.getText().isEmpty())
            System.out.println("Please fill all of the required data!");
        else {
            if (stockType.isEmpty())
                System.out.println("Please select stock types!");
            else {
                size = Double.parseDouble(sizeField.getText());
                temperature = Double.parseDouble(temperatureField.getText());
                Set<ConstraintViolation<Warehouse>> cons = owner.createWarehouse(category, size, temperature, stockType);

                size_con.clear();
                temperature_con.clear();

                if (!cons.isEmpty()) {
                    for (ConstraintViolation<Warehouse> con : cons) {
                        if (con.getPropertyPath().toString().equals("size"))
                            size_con.add(con.getMessage());
                        else if (con.getPropertyPath().toString().equals("temperature"))
                            temperature_con.add(con.getMessage());
                    }
                    System.out.println("\n\n\nShow messages:");
                    System.out.println("Size Violations:");

                    for (String message : size_con) {
                        if (!message.isEmpty()) {
                            System.out.println(message);
                            fillConsBox(message);
                        }
                    }
                    System.out.println("\n");
                    System.out.println("Temperature Violations:");
                    for (String message : temperature_con) {
                        if (!message.isEmpty()) {
                            System.out.println(message);
                            fillConsBox(message);
                        }
                    }
                }else{      // Executes when cons.IsEmpty() => true
                    System.out.println("Warehouse created");
                    warehousesState(owner);
                    successLabel.setVisible(true);
                }

            }
        }
    }
    public void closeStage(ActionEvent event){
        exitButton = (Button) event.getSource();
        Stage stage = (Stage) exitButton.getScene().getWindow();
        stage.close();
    }
    public void handleComboBox(ActionEvent event) {
        addToListView();
    }

    public void showConsPane(MouseEvent mouseEvent) {
        consVbox.setVisible(true);
    }

    public void hideConsPane(MouseEvent mouseEvent) {
        consVbox.setVisible(false);
    }

    public void handleExitButton(ActionEvent event) {
        closeStage(event);
    }

    public void doneButtonAction(ActionEvent event) {   // HANDLES EDIT WAREHOUSE
        double size = wh.getSize();
        System.out.println("Size: " + size);
        String category = typeBox.getSelectionModel().getSelectedItem();
        System.out.println("Category: " + category);
        stockType = stockTypeView.getSelectionModel().getSelectedItems();  // Selected items of ListView
        for (String o : stockType) {
            System.out.println("o = " + o);
        }
        double temperature = wh.getTemperature();
        System.out.println("Temperature: " + temperature);
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userFXML.fxml"));
            Parent root = loader.load();

            UserController userController = loader.getController();
            userController.transferMessage(category);   // transfer data between two controllers
        }catch (IOException e){
            e.printStackTrace();
        }
        closeStage(event);
    }
}

package system.application.controllers.owner;

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
import system.application.controllers.admin.UserController;
import system.application.others.DataRetriever;
import system.application.others.MessageService;
import system.backend.WSystem;
import system.backend.dao.DAO;
import system.backend.dao.MainDAO;
import system.backend.dataholders.OwnerDataHolder;
import system.backend.others.StockType;
import system.backend.others.Warehouse;
import system.backend.profiles.Owner;
import system.backend.services.ValidationService;

import javax.validation.ConstraintViolation;
import java.io.IOException;
import java.util.*;

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

    private String[] keys = {"size", "temperature"};

    private Map<String, Map<String, String>> data = new HashMap<>();
    private Map<String, Map<String, String>> oldData = new HashMap<>();
    private Map<String, Set<String>> cons = new LinkedHashMap<>();

    private MessageService messageService = MessageService.getInstance();

    private double oldSize;
    private double oldtemperature;
    private String oldCategory;
    private Set<StockType> oldStockTypes;

    DataRetriever dataRetriever = DataRetriever.getInstance();
    ValidationService validationService = ValidationService.getInstance();

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

    public void fillConsBox1(String message) {
        Label consLabel = new Label();
        consLabel.setText(message);
        consLabel.setStyle("-fx-text-fill: red; -fx-font-size: 11px");
        consVbox.getChildren().add(consLabel);
        System.out.println(message);
        Hbox.setVisible(true);
    }

    public void addConstraints(Set<ConstraintViolation<Object>> cons){
        for (ConstraintViolation<Object> con : cons) {
            if (con.getPropertyPath().toString().equals("size"))
                size_con.add(con.getMessage());
            else if (con.getPropertyPath().toString().equals("temperature"))
                temperature_con.add(con.getMessage());
        }
    }

    public void showMessages(){
        System.out.println("\n\n\nShow messages:");
        System.out.println("Size Violations:");

        for (String message : size_con) {
            if (!message.isEmpty()) {
                fillConsBox1(message);
            }
        }
        System.out.println("\n");
        System.out.println("Temperature Violations:");
        for (String message : temperature_con) {
            if (!message.isEmpty()) {
                fillConsBox1(message);
            }
        }
    }

    public void createButtonAction(ActionEvent event) {
        System.out.println("Create Button Clicked.");
        consVbox.getChildren().clear();
        successLabel.setVisible(false);
        Hbox.setVisible(false);

        data.clear();
        cons.clear();

        dataRetriever.getWarehouseDataFromController(this, data);

        if (!validationService.allDataFilled(data.get("data")))
            System.out.println("Please fill all of the required data!");
        else {
            if (!validationService.allDataFilled(data.get("stocktypes")))
                System.out.println("Please select stock types!");
            else {
                cons = owner.createWarehouse(data);

                if (!cons.isEmpty()) {
                    messageService.showMessages(this, cons, keys);
                    System.out.println("Yes cons is not emprty");
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
        consVbox.getChildren().clear();
        size_con.clear();
        temperature_con.clear();

        oldSize = wh.getSize();
        oldtemperature = wh.getTemperature();
        oldCategory = wh.getCategory();
        oldStockTypes = wh.getStockTypes();

        String category = typeBox.getSelectionModel().getSelectedItem();
        System.out.println("Category: " + category);
        stockType = stockTypeView.getSelectionModel().getSelectedItems();  // Selected items of ListView
        for (String o : stockType) {
            System.out.println("o = " + o);
        }
        double size;
        double temperature;

        if (sizeField.getText().isEmpty() || temperatureField.getText().isEmpty())
            System.out.println("Please fill all of the required data!");
        else {
            if (!stockType.isEmpty()) {
                Set<StockType> stockTypes = new HashSet<>();
                for (String s : stockType) {
                    stockTypes.add(wSystem.findStockTypeBy1Value(s));
                    wh.setStockTypes(stockTypes);
                }
            }
            size = Double.parseDouble(sizeField.getText());
            temperature = Double.parseDouble(temperatureField.getText());

            wh.setSize(size);
            wh.setTemperature(temperature);
            wh.setCategory(category);

            DAO<Warehouse, String> warehouseDAO = new MainDAO<>();
            Set<ConstraintViolation<Object>> cons = validationService.oldValidate(wh);

            if (cons.isEmpty()) {
                warehouseDAO.update(wh);
                System.out.println("Warehouse successfully updated!");
                closeStage(event);
            } else {
                wh.setCategory(oldCategory);
                wh.setSize(oldSize);
                wh.setStockTypes(oldStockTypes);
                wh.setTemperature(oldtemperature);
                System.out.println("EI kulvach!!!!!!");
                addConstraints(cons);
                showMessages();
            }
        }
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/userFXML.fxml"));
            Parent root = loader.load();

            UserController userController = loader.getController();
            userController.transferMessage(wh.getCategory());   // transfer data between two controllers
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public ComboBox<String> getTypeBox() {
        return typeBox;
    }

    public ListView<String> getStockTypeView() {
        return stockTypeView;
    }

    public TextField getSizeField() {
        return sizeField;
    }

    public TextField getTemperatureField() {
        return temperatureField;
    }
}

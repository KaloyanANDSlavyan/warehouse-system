package system.backend.profiles;

import system.backend.WSystem;
import system.backend.constraints.MyUnique;
//import system.backend.others.StockType;
import system.backend.others.StockType;
import system.backend.others.Warehouse;
import system.backend.services.ValidationService;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.ConstraintViolation;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
public class Owner extends AbstractMainProfile implements SecondaryProfile {
    @Size(max = 50)
    @Email(message = "Invalid email address")
    @MyUnique(type = Owner.class, column = "emailAddress")
    private String emailAddress;
    @Size(min = 10, message = "Phone number must contain 10 digits")
    //@Pattern(regexp = "(?=.*[0-9]).+", message = "Phone number can contain only digits")
    @Pattern(regexp = "(?!.*[a-z])(?!.*[A-Z])(?!.*[!@#$%^&*)(_=+'|<>~.,?]).*", message = "Phone number can contain only digits")
    @MyUnique(type = Owner.class, column = "phoneNumber")
    private String phoneNumber;
//(?!.*[!@#$%^&*)(-_=+\/'|<>~.,?)
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Warehouse> warehouses = new ArrayList<>();

    public Owner(){
        super();
    }

//    public Set<ConstraintViolation<Object>> createWarehouse(String category, double size,
//                                                            double temperature, List<String> stockType){
//        WSystem wSystem = WSystem.getInstance();
//
//        Set<StockType> stockTypes = new HashSet<>();
//        for(String s : stockType) {
//            stockTypes.add(wSystem.findStockTypeBy1Value(s));
//        }
//
//        Warehouse warehouse = new Warehouse();
//        warehouse.setCategory(category);
//        warehouse.setSize(size);
//        warehouse.setTemperature(temperature);
//        warehouse.setStockTypes(stockTypes);
//
//        ValidationService validationService = ValidationService.getInstance();
//        Set<ConstraintViolation<Object>> cons = validationService.oldValidate(warehouse);
//
//        if(cons.isEmpty()) {
//            warehouse.setOwner(this);
//            wSystem.addWarehouseToDatabase(warehouse);
//        }
//
//        return cons;
//    }

    public Map<String, Set<String>> createWarehouse(Map<String, Map<String, String>> data){
        WSystem wSystem = WSystem.getInstance();

        Set<StockType> stocks = new HashSet<>();

        Map<String, String> stockTypes = data.get("stocktypes");
        for(Map.Entry<String, String> entry : stockTypes.entrySet()){
            stocks.add(wSystem.findStockTypeBy1Value(entry.getValue()));
        }

        Map<String, String> _data = data.get("data");

        Warehouse warehouse = new Warehouse();
        warehouse.setCategory(_data.get("category"));
        warehouse.setSize(Double.parseDouble(_data.get("size")));
        warehouse.setTemperature(Double.parseDouble(_data.get("temperature")));
        warehouse.setStockTypes(stocks);

        ValidationService validationService = ValidationService.getInstance();
        Map<String, Set<String>> cons = validationService.validate(warehouse);

        if(cons.isEmpty()) {
            warehouse.setOwner(this);
            wSystem.addWarehouseToDatabase(warehouse);
        }

        return cons;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public List<Warehouse> getWarehouses() {
        return warehouses;
    }
}

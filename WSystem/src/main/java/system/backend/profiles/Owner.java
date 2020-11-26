package system.backend.profiles;

import system.backend.WSystem;
import system.backend.constraints.MyUnique;
//import system.backend.others.StockType;
import system.backend.others.StockType;
import system.backend.others.Warehouse;
import system.backend.services.ValidationService;
import system.backend.services.WarehouseValidation;
import system.backend.validators.groups.OnSaveChecks;
import system.backend.validators.groups.OnUpdateChecks;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.ConstraintViolation;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Owner extends AbstractProfile {
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

    public Set<ConstraintViolation<Warehouse>> createWarehouse(String category, double size,
                                                            double temperature, List<String> stockType){
        WSystem wSystem = WSystem.getInstance();

        Set<StockType> stockTypes = new HashSet<>();
        for(String s : stockType) {
            stockTypes.add(wSystem.findStockTypeBy1Value(s));
        }

        Warehouse warehouse = new Warehouse();
        warehouse.setCategory(category);
        warehouse.setSize(size);
        warehouse.setTemperature(temperature);
        warehouse.setStockTypes(stockTypes);

        WarehouseValidation warehouseValidation = wSystem.getWarehouseValidation();
        Set<ConstraintViolation<Warehouse>> cons = warehouseValidation.validate(warehouse);

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

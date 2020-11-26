package system.backend.others;

import system.backend.profiles.Owner;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Set;

@Entity
public class Warehouse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected long ID;
    @Max(value = 1000000, message = "Size - too long")
    //@Pattern(regexp = "[0-9]", message = "Size - can contain only digits")
    private double size;
    @Max(value = 50, message = "Temperature - too long")
    //@Pattern(regexp = "[0-9]", message = "Temperature - can contain only digits")
    private double temperature;
    private String category;
    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "Warehouse_StockType",
            joinColumns = {@JoinColumn(name = "warehouse_id")},
            inverseJoinColumns = {@JoinColumn(name = "stockType_id")})
    private Set<StockType> stockTypes;
    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Owner owner;

    public Warehouse(){

    }

    public long getID() {
        return ID;
    }

    public void setID(long ID) {
        this.ID = ID;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public Set<StockType> getStockTypes() {
        return stockTypes;
    }

    public void setStockTypes(Set<StockType> stockTypes) {
        this.stockTypes = stockTypes;
    }

    public Owner getOwner() {
        return owner;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setOwner(Owner owner) {
        this.owner = owner;
        owner.getWarehouses().add(this);
    }
}

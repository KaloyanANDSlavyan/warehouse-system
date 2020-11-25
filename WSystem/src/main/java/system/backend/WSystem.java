package system.backend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import system.backend.dao.*;
import system.backend.others.StockType;
import system.backend.others.Warehouse;
import system.backend.profiles.*;
import system.backend.services.ValidationService;
import system.backend.validators.indicators.ValidationIndicator;

import javax.validation.ConstraintViolation;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WSystem {
    private Logger LOGGER;
    private static WSystem wsystem;
    //Actors
    private Admin admin;
    private List<Agent> agents;
    private List<Owner> owners;
    //DAOs
    private AdminDAO adminDAO;
    private OwnerDAO ownerDAO;
    private AgentDAO agentDAO;
    private WarehouseDAO warehouseDAO;
    private StockTypeDAO stockTypeDAO;
    private ProfileDAO<Profile> profileDAO;
    
    WSystem(){
        // Creating the system
        setLOGGER(LogManager.getLogger());
        setAdminDAO(new AdminDAO());
        setOwnerDAO(new OwnerDAO());
        setAgentDAO(new AgentDAO());
        setWarehouseDAO(new WarehouseDAO());
        setProfileDAO(new ProfileDAO<>());
        setStockTypeDAO(new StockTypeDAO());

        agents = new ArrayList<>();
        owners = new ArrayList<>();
    }

    public static WSystem getInstance(){
        if(wsystem == null)
            wsystem = new WSystem();
        return wsystem;
    }

    public Set<ConstraintViolation<Object>> createProfile(Profile profile){
        // Validating profile
        Set<ConstraintViolation<Object>> constraints
                = ValidationService.getInstance().validate(profile);

        if (constraints.isEmpty()){
            if(profile.getClass().getSimpleName().equals("Admin")) {
                setAdmin(profile);
            }
            else if(profile.getClass().getSimpleName().equals("Owner"))
                owners.add((Owner)profile);
            else if(profile.getClass().getSimpleName().equals("Agent"))
                agents.add((Agent)profile);

            profileDAO.save(profile);
        }
        return constraints;
    }

    public void createAdmin(String firstName, String lastName,
                            String username, String pass){
//        CryptoService cryptoService = CryptoService.getInstance();
//        pass = cryptoService.encrypt(pass, cryptoService.getKey(), cryptoService.getCipher());

        adminDAO.deleteIfExists();
        ValidationService.getInstance().setValidationIndicator(ValidationIndicator.ADMIN);

        Profile admin = new Admin(firstName, lastName, username, pass);
        Set<ConstraintViolation<Object>> constraints = createProfile(admin);

        for (ConstraintViolation<Object> con : constraints) {
            LOGGER.error("Admin couldn't be validated");
            LOGGER.error("The " + con.getPropertyPath() + " is not valid!");
        }
    }

    public void createStockType(){
        List<StockType> stockTypeList = stockTypeDAO.selectAll(StockType.class);
//        if(!stockTypeList.isEmpty()){
//            stockTypeDAO.deleteAll("StockType");
//            stockTypeDAO.setAutoIncrement();
//        }
        if(!stockTypeList.isEmpty()){
            for(StockType stock : stockTypeList)
                stockTypeDAO.delete(stock);
            stockTypeDAO.setAutoIncrement();
        }

        String[] stockTypes = {"Vegetables", "Fruits", "Seeds",
                               "Computers", "Smartphones", "Chargers",
                               "Explosives", "Guns", "Military Equipment"};
        StockType[] stockTypes1 = new StockType[9];
        for(int i = 0; i < stockTypes.length; i++){
            stockTypes1[i] = new StockType();
            stockTypes1[i].setType(stockTypes[i]);
            stockTypeDAO.save(stockTypes1[i]);
        }
    }

    public boolean hasProfiles(){
        if(hasAgentProfiles() || hasOwnerProfiles())
            return true;

        return false;
    }

    public boolean hasAgentProfiles(){
        agents = agentDAO.selectAll(Agent.class);

        if(!agents.isEmpty())
            return true;

        return false;
    }

    public boolean hasOwnerProfiles(){
        owners = ownerDAO.selectAll(Owner.class);

        if(!owners.isEmpty())
            return true;

        return false;
    }

    public Owner findOwnerBy2Values(String value1, String value2){
        Owner owner = ownerDAO.findBy2Values(Owner.class, "username", "password", value1, value2);
        return owner;
    }

    public StockType findStockTypeBy1Value(String value){
        StockType stockType = stockTypeDAO.findBy1Value(StockType.class, "type", value);
        return stockType;
    }

    public void initializeDB(){
        createAdmin("Adminov", "Adminov", "admin", "Admin_123");
        createStockType();
    }

    public void addWarehouseToDatabase(Warehouse warehouse){
        warehouseDAO.save(warehouse);
    }

    public Profile getAdmin() {
        return admin;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public List<Owner> getOwners() {
        return owners;
    }

    public void setLOGGER(Logger LOGGER) {
        this.LOGGER = LOGGER;
    }

    public void setAdminDAO(AdminDAO adminDAO) {
        this.adminDAO = adminDAO;
    }

    public void setOwnerDAO(OwnerDAO ownerDAO) {
        this.ownerDAO = ownerDAO;
    }

    public void setAgentDAO(AgentDAO agentDAO) {
        this.agentDAO = agentDAO;
    }

    public void setProfileDAO(ProfileDAO<Profile> profileDAO) {
        this.profileDAO = profileDAO;
    }

    public StockTypeDAO getStockTypeDAO() {
        return stockTypeDAO;
    }

    public void setStockTypeDAO(StockTypeDAO stockTypeDAO) {
        this.stockTypeDAO = stockTypeDAO;
    }

    public void setAdmin(Profile admin) {
        this.admin = (Admin)admin;
    }

    public AdminDAO getAdminDAO() {
        return adminDAO;
    }

    public OwnerDAO getOwnerDAO() {
        return ownerDAO;
    }

    public AgentDAO getAgentDAO() {
        return agentDAO;
    }

    public ProfileDAO<Profile> getProfileDAO() {
        return profileDAO;
    }

    public WarehouseDAO getWarehouseDAO() {
        return warehouseDAO;
    }

    public void setWarehouseDAO(WarehouseDAO warehouseDAO) {
        this.warehouseDAO = warehouseDAO;
    }
}

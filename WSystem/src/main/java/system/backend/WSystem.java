package system.backend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class WSystem {
    private static WSystem wsystem;
    private Admin admin;
    private List<Agent> agents;
    private List<Owner> owners;
    private Logger LOGGER;
    
    WSystem(){
        LOGGER = LogManager.getLogger();
        createAdmin("Admin", "Adminov", "admin", "Admin_123");
        agents = new ArrayList<Agent>();
        owners = new ArrayList<Owner>();
    }

    public static WSystem getInstance(){
        if(wsystem == null)
            wsystem = new WSystem();
        return wsystem;
    }

    public void createAdmin(String firstName, String lastName,
                             String username, String password){
        Admin admin = new Admin(firstName, lastName, username, password);
        AdminDAO.getInstance().deleteIfExists();
        try {
            Set<ConstraintViolation<Admin>> constraints
                    = ValidationService.getInstance().validateAdmin(admin);
            if (constraints.isEmpty())
                AdminDAO.getInstance().save(admin);
            else throw new ConstraintViolationException(constraints);
        }catch(ConstraintViolationException e){
            for(ConstraintViolation con: e.getConstraintViolations()) {
                LOGGER.error("The " + con.getPropertyPath() + " is not valid!");
            }
        }
    }

    public Admin getAdmin() {
        return admin;
    }

    public List<Agent> getAgents() {
        return agents;
    }

    public List<Owner> getOwners() {
        return owners;
    }
}

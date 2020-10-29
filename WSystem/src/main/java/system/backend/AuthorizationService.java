package system.backend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//This class will be used where authorization is needed
public class AuthorizationService {
    private static AuthorizationService service;
    private Logger LOGGER;

    AuthorizationService(){
        LOGGER = LogManager.getLogger();
    }

    public static AuthorizationService getInstance(){
        if(service == null)
            service = new AuthorizationService();
        return service;
    }

    public boolean authorizeLogin(String username, String password, Class c){
        try {
            if (c == Admin.class)
                return authorizeAdmin(username, password);
            else if (c == Agent.class)
                return authorizeAgent(username, password);
            else return authorizeOwner(username, password);
            // can be NoResultException
        } catch(Exception e){
            LOGGER.info("An exception occurred but handled: " + e.getMessage());
            return false;
        }
    }

    public boolean authorizeAdmin(String username, String password){
            Admin admin = AdminDAO.getInstance().findByCredentials(username, password);
            if(!admin.getUsername().equals(username) || !admin.getPassword().equals(password))
                return false;
            return true;
    }

    public boolean authorizeAgent(String username, String password){
        return false;
    }

    public boolean authorizeOwner(String username, String password){
        return false;
    }
}


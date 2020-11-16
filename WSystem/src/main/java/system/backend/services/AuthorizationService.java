package system.backend.services;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import system.backend.WSystem;
import system.backend.dao.AdminDAO;
import system.backend.profiles.Admin;
import system.backend.profiles.Agent;
import system.backend.profiles.Owner;
import system.backend.profiles.Profile;

import java.security.CryptoPrimitive;

//This class will be used where authorization is needed
public class AuthorizationService {
    private static AuthorizationService service;
    private final Logger LOGGER;

    AuthorizationService(){
        LOGGER = LogManager.getLogger();
    }

    public static AuthorizationService getInstance(){
        if(service == null)
            service = new AuthorizationService();
        return service;
    }

    public boolean authorizeLogin(String username, String password, Class<?> c){
//        CryptoService cryptoService = CryptoService.getInstance();
//        password = cryptoService.encrypt(password, cryptoService.getKey(), cryptoService.getCipher());
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
        WSystem wSystem = WSystem.getInstance();
        Admin admin = wSystem.getAdminDAO().findBy2Values(Admin.class, "username", "password", username, password);
        return admin.getUsername().equals(username) && admin.getPassword().equals(password);
    }

    public boolean authorizeAgent(String username, String password){
        WSystem wSystem = WSystem.getInstance();
        Agent agent = wSystem.getAgentDAO().findBy2Values(Agent.class, "username", "password", username, password);
        return agent.getUsername().equals(username) && agent.getPassword().equals(password);
    }

    public boolean authorizeOwner(String username, String password){
        WSystem wSystem = WSystem.getInstance();
        Owner owner = wSystem.getOwnerDAO().findBy2Values(Owner.class, "username", "password", username, password);
        return owner.getUsername().equals(username) && owner.getPassword().equals(password);
    }
}


package system.backend;

import javax.persistence.NoResultException;

//This class will be used where authorization is needed
public class AuthorizationService {
    private static AuthorizationService service;

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
        } catch(Exception e){
            return false;
        }
    }

    public boolean authorizeAdmin(String username, String password){
            Admin admin = AdminDAO.getInstance().findByDetails(username, password);
            if (admin.getUsername().equals(username) && admin.getPassword().equals(password))
                return true;
            else return false;
    }

    public boolean authorizeAgent(String username, String password){
        return false;
    }

    public boolean authorizeOwner(String username, String password){
        return false;
    }
}


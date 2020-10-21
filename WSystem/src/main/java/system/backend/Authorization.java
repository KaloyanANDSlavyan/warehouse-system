package system.backend;

import javax.persistence.NoResultException;

//This class will be used where authorization is needed
public class Authorization {
    private static Authorization authorization;

    public static Authorization getInstance(){
        if(authorization == null)
            authorization = new Authorization();
        return authorization;
    }

    public boolean admin(String username, String password){
        try {
            Admin admin = AdminDAO.getInstance().findByDetails(username, password);
            if(admin.getUsername().equals(username) && admin.getPassword().equals(password))
                return true;
            else return false;
        } catch (NoResultException e) {
            return false;
        }
    }
}


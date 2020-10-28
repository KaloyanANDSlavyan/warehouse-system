package system.backend;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;

//DAO Layer for admin
public class AdminDAO implements DAO {
    private static AdminDAO adminDAO;
    private EntityManager manager;

    public static AdminDAO getInstance(){
        if(adminDAO == null) {
            adminDAO = new AdminDAO();
        }
        return adminDAO;
    }

    public AdminDAO(){
        Configuration config = Configuration.getInstance();
        manager = config.getManager();
    }

    public void save(Admin admin){
        manager.getTransaction().begin();
        manager.persist(admin);
        manager.getTransaction().commit();
    }

    public Admin findByDetails(String username, String password) {
        String text = "SELECT * FROM Admin WHERE username= :username && password = :password";
        Query query = manager.createNativeQuery(text, Admin.class);
        query.setParameter("username", username);
        query.setParameter("password", password);
        Admin admin = (Admin)query.getSingleResult();
        return admin;
    }

    public void deleteIfExists(){
        String text1 = "DELETE FROM Admin";
        String text2 = "ALTER TABLE Admin AUTO_INCREMENT = 1";
        Query query1 = manager.createQuery(text1);
        Query query2 = manager.createNativeQuery(text2);
        manager.getTransaction().begin();
        query1.executeUpdate();
        query2.executeUpdate();
        manager.getTransaction().commit();
    }
}

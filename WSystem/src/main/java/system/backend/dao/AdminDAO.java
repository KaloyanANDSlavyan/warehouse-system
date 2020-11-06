package system.backend.dao;

import system.backend.profiles.Admin;

import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;

//DAO Layer for admin
public class AdminDAO extends ProfileDAO<Admin> {

    public AdminDAO(){
        super();
    }

    public Admin findByCredentials(String username, String password) {
//        String text = "select a.username, a.password " +
//                "from Admin a " +
//                "where a.username = :username and a.password = :password";
        //String text = "SELECT * FROM Admin WHERE username= :username && password = :password";
//        Query query = manager.createQuery(text);
//        query.setParameter("username", username);
//        query.setParameter("password", password);
//        List admin = query.getResultList();
//        return admin.get(0);

        // Configuring the query
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<Admin> q = cb.createQuery(Admin.class);
        Root<Admin> admin = q.from(Admin.class);
        ParameterExpression<String> p = cb.parameter(String.class, "username");
        ParameterExpression<String> p2 = cb.parameter(String.class, "password");

        // Setting the query
        q.select(admin).where(cb.equal(admin.get("username"), p), cb.equal(admin.get("password"), p2));

        // Executing the query
        TypedQuery<Admin> typed = manager.createQuery(q);
        typed.setParameter("username", username);
        typed.setParameter("password", password);
        Admin a = typed.getSingleResult();
        return a;
    }

    public void deleteIfExists(){
        // Search for admin if exists
        Admin admin = manager.find(Admin.class, Long.valueOf(1));
        if(admin != null){
            manager.getTransaction().begin();
            manager.remove(admin);
            manager.getTransaction().commit();

            setAutoIncrement();
        }
    }

    public void setAutoIncrement(){
        //String text1 = "DELETE FROM Admin";
        String text2 = "ALTER TABLE Admin AUTO_INCREMENT = 1";
        //Query query1 = manager.createQuery(text1);
        Query query2 = manager.createNativeQuery(text2);
        manager.getTransaction().begin();
        //query1.executeUpdate();
        query2.executeUpdate();
        manager.getTransaction().commit();
    }
}

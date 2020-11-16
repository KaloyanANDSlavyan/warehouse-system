package system.backend.dao;

import system.backend.Configuration;
import system.backend.profiles.Admin;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

abstract public class AbstractDAO<T> implements DAO<T> {
    protected EntityManager manager;

    public AbstractDAO(){
        Configuration config = Configuration.getInstance();
        manager = config.getManager();
    }

    public void save(T object){
        try {
            manager.getTransaction().begin();
            manager.persist(object);
            manager.getTransaction().commit();
        } catch(Exception e){
            e.printStackTrace();
            System.out.println("LQLQLQLQLQLQLQLQLQLQLQ");
        }
    }

    public void deleteByID(Class<T> c, Long id){
        T object = manager.find(c, id);

        manager.getTransaction().begin();
        manager.remove(object);
        manager.getTransaction().commit();
    }

    public List<T> selectAll(Class<T> c){
        CriteriaBuilder cb = manager.getCriteriaBuilder();
        CriteriaQuery<T> q = cb.createQuery(c);
        Root<T> r = q.from(c);
        q.select(r);

        TypedQuery<T> query = manager.createQuery(q);
        List<T> results = query.getResultList();

        return results;
    }

    public T findBy2Values(Class<T> c, String column1, String column2, String value1, String value2) {
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
        CriteriaQuery<T> q = cb.createQuery(c);
        Root<T> entity = q.from(c);
        ParameterExpression<String> p = cb.parameter(String.class, column1);
        ParameterExpression<String> p2 = cb.parameter(String.class, column2);

        // Setting the query
        q.select(entity).where(cb.equal(entity.get(column1), p), cb.equal(entity.get(column2), p2));

        // Executing the query
        TypedQuery<T> typed = manager.createQuery(q);
        typed.setParameter(column1, value1);
        typed.setParameter(column2, value2);
        T result = typed.getSingleResult();

        return result;
    }
}

package system;

import org.junit.Test;
import system.backend.dao.OwnerDAO;
import system.backend.profiles.Owner;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.ParameterizedType;
import java.util.List;

public class Generic<T> {
    @Test
    public void tests(){
        EntityManagerFactory factory = Persistence.createEntityManagerFactory("test-unit");
        EntityManager drug = factory.createEntityManager();
        Generic2<Owner> ownerDAO = new Generic2<>();
        List<Owner> list = ownerDAO.selectAll(Owner.class, drug);
        System.out.println(list);
    }

}

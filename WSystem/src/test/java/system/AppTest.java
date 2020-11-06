package system;

import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import system.backend.profiles.Admin;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.TypeVariable;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    @Ignore
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    @Test
    public void testshit(){
        Generic<String> strings = new Generic<>();
        TypeVariable[] tValue = strings.getClass().getTypeParameters();
        System.out.println();
//        EntityManagerFactory factory = Persistence.createEntityManagerFactory("test-unit");
//        EntityManager manager = factory.createEntityManager();
//        Admin admin = new Admin("Ivan", "Cankov", "Lqlq", "asdAfasdfa123__");
//        manager.getTransaction().begin();
//        manager.persist(admin);
//        manager.getTransaction().commit();
//        TestDAO testDAO = new TestDAO();
//        List<Admin> list = testDAO.selectAll(Admin.class, manager);
//        System.out.println(list.get(0).getFirstname());
    }
}

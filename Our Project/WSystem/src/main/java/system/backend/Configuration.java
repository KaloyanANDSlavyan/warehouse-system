package system.backend;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Configuration {
    private static Configuration config;
    private EntityManagerFactory factory;
    private EntityManager manager;

    public static Configuration getInstance(){
        if(config == null)
            config = new Configuration();
        return config;
    }

    public EntityManagerFactory getFactory() {
        return factory;
    }

    public void createFactory() {
        factory = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    public EntityManager getManager() {
        return manager;
    }

    public void createManager() {
        manager = factory.createEntityManager();
    }

    public void closeFactory(){
        factory.close();
    }

    public void closeManager(){
        manager.close();
    }
}

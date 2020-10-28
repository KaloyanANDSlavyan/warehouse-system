package system.backend;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.validation.ConstraintViolationException;

public class Configuration {
    private static Configuration config;
    private EntityManagerFactory factory;
    private EntityManager manager;
    private Logger LOGGER;

    private void createLogger(){
        LOGGER = LogManager.getLogger();
    }

    public EntityManagerFactory getFactory() {
        return factory;
    }

    private void createFactory() {
        factory = Persistence.createEntityManagerFactory("my-persistence-unit");
    }

    public EntityManager getManager() {
        return manager;
    }

    private void createManager() {
        manager = factory.createEntityManager();
    }

    private void createDB(){
        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

        // Create MetadataSources
        MetadataSources sources = new MetadataSources(registry);

        // Create Metadata
        Metadata metadata = sources.getMetadataBuilder().build();

        // Create SessionFactory
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();
        sessionFactory.close();
        StandardServiceRegistryBuilder.destroy(registry);
    }

    // Creating DB in separate thread
    public void configure(){
        new Thread(() -> {
                try {
                    Thread.sleep(5000);
                    createLogger();
                    createDB();
                    createFactory();
                    createManager();
                    WSystem wSystem = WSystem.getInstance();
                    LOGGER.info("Successfully finished the configuration of the application");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
    }

    public static Configuration getInstance(){
        if(config == null) {
            config = new Configuration();
        }
        return config;
    }

    public void closeFactory(){
        factory.close();
    }

    public void closeManager(){
        manager.close();
    }
}
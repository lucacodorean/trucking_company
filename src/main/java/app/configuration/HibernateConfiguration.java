package app.configuration;

import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.SessionFactory;

public class HibernateConfiguration {
    private static final String HIBERNATE_CONFIGURATION_FILE = "hibernate.cfg.xml";
    private static final SessionFactory SESSION_FACTORY = buildSessionFactory();

    private HibernateConfiguration() { }

    private static SessionFactory buildSessionFactory() {
        try {
            Configuration configuration = new Configuration().configure(HIBERNATE_CONFIGURATION_FILE);
            new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            return configuration.buildSessionFactory();

        } catch (Exception ex) {
            System.err.println("Initial SessionFactory creation failed because " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return SESSION_FACTORY;
    }

    public static void shutdownSession() {
        getSessionFactory().close();
    }
}

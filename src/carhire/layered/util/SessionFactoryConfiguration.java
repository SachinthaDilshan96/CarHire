package carhire.layered.util;

import carhire.layered.entity.UserEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryConfiguration {
    private static SessionFactoryConfiguration sessionFactoryConfiguration;
    private SessionFactory sessionFactory;

    private SessionFactoryConfiguration(){
        Configuration configuration = new Configuration().configure()
                .addAnnotatedClass(UserEntity.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionFactoryConfiguration getInstance(){
        return (sessionFactoryConfiguration==null)?new SessionFactoryConfiguration():sessionFactoryConfiguration;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }
}

package carhire.layered.util;

import carhire.layered.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryConfiguration {
    private static SessionFactoryConfiguration sessionFactoryConfiguration;
    private SessionFactory sessionFactory;

    private SessionFactoryConfiguration(){
        Configuration configuration = new Configuration().configure()
                .addAnnotatedClass(UserEntity.class)
                .addAnnotatedClass(VehicleCategoryEntity.class)
                .addAnnotatedClass(VehicleBrandEntity.class)
                .addAnnotatedClass(VehicleEntity.class)
                .addAnnotatedClass(CustomerEntity.class)
                .addAnnotatedClass(HireEntity.class);
        sessionFactory = configuration.buildSessionFactory();
    }

    public static SessionFactoryConfiguration getInstance(){
        return (sessionFactoryConfiguration==null)?new SessionFactoryConfiguration():sessionFactoryConfiguration;
    }

    public Session getSession(){
        return sessionFactory.openSession();
    }
}

package edu.birzeit.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * 
 * @author haawad
 *
 */
public class DemoSesssionFactory {

    private static SessionFactory sessionFactory;
    private static Session session;

    public DemoSesssionFactory() {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Failed to create sessionFactory object." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public Session getSession() {
        session = sessionFactory.openSession();
        return session;
    }
}

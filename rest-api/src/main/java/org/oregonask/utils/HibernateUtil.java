package org.oregonask.utils;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	
	private static Logger logger = Logger.getLogger(HibernateUtil.class);
	
	private static SessionFactory buildSessionFactory() {
		try {
			// Use hibernate.cfg.xml to get a SessionFactory
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			logger.info("Hibernate Configuration loaded.");
			
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			logger.info("Hibernate serviceRegistry created.");
			
			SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			
			return sessionFactory;
		} catch (Throwable ex) {
			logger.error("Initial SessionFactory creation failed." + ex);
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static void rollback(Transaction tx) {
		if (tx != null) tx.rollback();
	}
	
	public static void close(Session session) {
		session.close();
	}
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) sessionFactory = buildSessionFactory();
		return sessionFactory;
	}
}

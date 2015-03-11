package org.oregonask.utils;

import java.util.Map;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtil {
	
	private static SessionFactory sessionFactory;
	
	private static Map<String, ClassMetadata> clazzez = (Map<String, ClassMetadata>) HibernateUtil.getSessionFactory().getAllClassMetadata();
	
	private static SessionFactory buildSessionFactory() {
		try {
			// Use hibernate.cfg.xml to get a SessionFactory
			Configuration configuration = new Configuration();
			configuration.configure("hibernate.cfg.xml");
			
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			
			SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
			
			return sessionFactory;
		} catch (Throwable ex) {
			ex.printStackTrace();
			throw new ExceptionInInitializerError(ex);
		}
	}
	
	public static void rollback(Transaction tx) {
		try {
			if (tx != null) tx.rollback();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void close(Session session) {
		try {
		if(session != null)
			session.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static SessionFactory getSessionFactory() {
		if (sessionFactory == null) sessionFactory = buildSessionFactory();
		return sessionFactory;
	}
	
	public static Class<?> getClass(String classname) {
		classname = "org.oregonask.entities." + classname;
		Class<?> clazz = null;
		for (String className : clazzez.keySet()) {
			if(className.equalsIgnoreCase(classname)) {
				try {
					clazz = Class.forName(className);
					break;
				} catch (ClassNotFoundException e) {}
			}
		}
		return clazz;
	}
}

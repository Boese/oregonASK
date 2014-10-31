package org.oregonask.services;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.oregonask.exceptions.DataAccessLayerException;
import org.oregonask.utils.HibernateUtil;


public abstract class AbstractService {

	private Session session;
	private Transaction tx;
	private Class<?> clazz;
	
	public AbstractService(Class<?> clazz) {
		HibernateUtil.getSessionFactory();
		this.clazz = clazz;
	}
	
	public void save(Object obj) {
		
		try {
			startOperation();
			session.save(obj);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	public void update(Object obj) {

		try {
			startOperation();
			session.update(obj);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	public void delete(Integer id) {
		
		Object obj = null;
		
		try {
			startOperation();
			obj = session.get(clazz, id);
			session.delete(obj);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	public Object find(int id) {
		
		Object obj = null;
		
		try {
			startOperation();
			obj = session.get(clazz, id);
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(session);
		}
		
		return obj;
	}
	
	public List<?> findAll() {
		
		List<?> objects = null;
		
		try {
			startOperation();
			Query query = session.createQuery("from " + clazz.getName());
			objects = query.list();
			tx.commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(session);
		}
		
		return objects;
	}
	
	protected Session getSession() {
		return session;
	}
	
	protected Transaction getTransaction() {
		return tx;
	}
	
	protected void handleException(HibernateException e) throws DataAccessLayerException {
		HibernateUtil.rollback(tx);
		throw new DataAccessLayerException(e);
	}
	
	protected void startOperation() throws HibernateException {
		session = HibernateUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
	}
}
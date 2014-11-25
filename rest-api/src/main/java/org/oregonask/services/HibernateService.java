package org.oregonask.services;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.oregonask.utils.HibernateUtil;

public class HibernateService {
	private Session session;
	private Transaction tx;
	
	public void startOperation() {
		try {
		session = HibernateUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void handleException(HibernateException e) {
		try {
		HibernateUtil.rollback(tx);
		} catch(Exception ex) {
			ex.printStackTrace();
		}
	}

	public Session getSession() {
		return session;
	}


	public Transaction getTx() {
		return tx;
	}
}

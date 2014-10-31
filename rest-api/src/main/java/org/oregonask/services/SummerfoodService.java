package org.oregonask.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.oregonask.entities.Summerfood;
import org.oregonask.utils.HibernateUtil;

public class SummerfoodService extends AbstractService {
	public SummerfoodService() {
		super(Summerfood.class);
	}
	
	@Override
	public Object find(int id) {
		
		Summerfood summerfood = null;
		
		try {
			startOperation();
			summerfood = (Summerfood) getSession().createCriteria(Summerfood.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(summerfood.getSchool());
			Hibernate.initialize(summerfood.getSponsor());
			Hibernate.initialize(summerfood.getSummerfoodInfo());
			getTransaction().commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(getSession());
		}
		
		return summerfood;
	}
}

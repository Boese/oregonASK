package org.oregonask.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.oregonask.entities.County;
import org.oregonask.utils.HibernateUtil;

public class CountyService extends AbstractService {
	
	public CountyService() {
		super(County.class);
	}
	
	@Override
	public Object find(Long id) {
		
		County county = null;
		
		try {
			startOperation();
			county = (County) getSession().createCriteria(County.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(county.getLocations());
			getTransaction().commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(getSession());
		}
		
		return county;
	}
}

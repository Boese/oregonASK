package org.oregonask.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.oregonask.entities.CongressionalDistrict;
import org.oregonask.utils.HibernateUtil;

public class CongressionalDistrictService extends AbstractService {
	
	public CongressionalDistrictService() {
		super(CongressionalDistrict.class);
	}
	
	@Override
	public Object find(Long id) {
		
		CongressionalDistrict cd = null;
		
		try {
			startOperation();
			cd = (CongressionalDistrict) getSession().createCriteria(CongressionalDistrict.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(cd.getLocations());
			getTransaction().commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(getSession());
		}
		
		return cd;
	}

}

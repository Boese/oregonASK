package org.oregonask.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.oregonask.entities.EthnicityCount;
import org.oregonask.utils.HibernateUtil;

public class EthnicityCountService extends AbstractService {
	
	public EthnicityCountService() {
		super(EthnicityCount.class);
	}
	
	@Override
	public Object find(Long id) {
		
		EthnicityCount ec = null;
		
		try {
			startOperation();
			ec = (EthnicityCount) getSession().createCriteria(EthnicityCount.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(ec.getEthnicity());
			Hibernate.initialize(ec.getSchoolDistricts());
			getTransaction().commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(getSession());
		}
		
		return ec;
	}

}

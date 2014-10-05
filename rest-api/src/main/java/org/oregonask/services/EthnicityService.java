package org.oregonask.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.oregonask.entities.Ethnicity;
import org.oregonask.utils.HibernateUtil;

public class EthnicityService extends AbstractService {
	
	public EthnicityService() {
		super(Ethnicity.class);
	}
	
	@Override
	public Object find(Long id) {
		
		Ethnicity ethnicity = null;
		
		try {
			startOperation();
			ethnicity = (Ethnicity) getSession().createCriteria(Ethnicity.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(ethnicity.getEthnicityCount());
			getTransaction().commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(getSession());
		}
		
		return ethnicity;
	}

}

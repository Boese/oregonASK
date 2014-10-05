package org.oregonask.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.oregonask.entities.KidsServed;
import org.oregonask.utils.HibernateUtil;

public class KidsServedService extends AbstractService {
	
	public KidsServedService() {
		super(KidsServed.class);
	}
	
	@Override
	public Object find(Long id) {
		
		KidsServed ks = null;
		
		try {
			startOperation();
			ks = (KidsServed) getSession().createCriteria(KidsServed.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(ks.getPrograms());
			getTransaction().commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(getSession());
		}
		
		return ks;
	}

}

package org.oregonask.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.oregonask.entities.GradeCount;
import org.oregonask.utils.HibernateUtil;

public class GradeCountService extends AbstractService {
	
	public GradeCountService() {
		super(GradeCount.class);
	}
	
	@Override
	public Object find(Long id) {
		
		GradeCount gc = null;
		
		try {
			startOperation();
			gc = (GradeCount) getSession().createCriteria(GradeCount.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(gc.getSchoolDistricts());
			getTransaction().commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(getSession());
		}
		
		return gc;
	}

}

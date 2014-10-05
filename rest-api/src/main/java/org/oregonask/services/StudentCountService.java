package org.oregonask.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.oregonask.entities.StudentCount;
import org.oregonask.utils.HibernateUtil;

public class StudentCountService extends AbstractService {
	
	public StudentCountService() {
		super(StudentCount.class);
	}
	
	@Override
	public Object find(Long id) {
		
		StudentCount sc = null;
		
		try {
			startOperation();
			sc = (StudentCount) getSession().createCriteria(StudentCount.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(sc.getSchools());
			getTransaction().commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(getSession());
		}
		
		return sc;
	}

}

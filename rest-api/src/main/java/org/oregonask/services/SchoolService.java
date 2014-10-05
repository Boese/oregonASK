package org.oregonask.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.oregonask.entities.School;
import org.oregonask.utils.HibernateUtil;

public class SchoolService extends AbstractService {
	
	public SchoolService() {
		super(School.class);
	}
	
	@Override
	public Object find(Long id) {
		
		School school = null;
		
		try {
			startOperation();
			school = (School) getSession().createCriteria(School.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(school.getPrograms());
			Hibernate.initialize(school.getStudentCount());
			Hibernate.initialize(school.getLocation());
			getTransaction().commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(getSession());
		}
		
		return school;
	}

}

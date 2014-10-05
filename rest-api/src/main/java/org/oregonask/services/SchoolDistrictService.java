package org.oregonask.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.oregonask.entities.SchoolDistrict;
import org.oregonask.utils.HibernateUtil;

public class SchoolDistrictService extends AbstractService {
	
	public SchoolDistrictService() {
		super(SchoolDistrict.class);
	}
	
	@Override
	public Object find(Long id) {
		
		SchoolDistrict sd = null;
		
		try {
			startOperation();
			sd = (SchoolDistrict) getSession().createCriteria(SchoolDistrict.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(sd.getEducationServiceDistrict());
			Hibernate.initialize(sd.getSchoolLocations());
			getTransaction().commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(getSession());
		}
		
		return sd;
	}

}

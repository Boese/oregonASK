package org.oregonask.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.oregonask.entities.EducationServiceDistrict;
import org.oregonask.utils.HibernateUtil;

public class EducationServiceDistrictService extends AbstractService {
	
	public EducationServiceDistrictService() {
		super(EducationServiceDistrict.class);
	}
	
	@Override
	public Object find(Long id) {
		
		EducationServiceDistrict ed = null;
		
		try {
			startOperation();
			ed = (EducationServiceDistrict) getSession().createCriteria(EducationServiceDistrict.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(ed.getSchoolDistricts());
			getTransaction().commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(getSession());
		}
		
		return ed;
	}

}

package org.oregonask.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.oregonask.entities.Nutrition;
import org.oregonask.utils.HibernateUtil;

public class NutritionService extends AbstractService{

	public NutritionService() {
		super(Nutrition.class);
	}
	
	@Override
	public Object find(int id) {
		
		Nutrition nutrition = null;
		
		try {
			startOperation();
			nutrition = (Nutrition) getSession().createCriteria(Nutrition.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(nutrition.getSponsor());
			Hibernate.initialize(nutrition.getSchool());
			Hibernate.initialize(nutrition.getNutritionInfo());
			getTransaction().commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(getSession());
		}
		
		return nutrition;
	}

}

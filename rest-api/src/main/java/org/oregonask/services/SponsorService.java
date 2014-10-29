package org.oregonask.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.oregonask.entities.Sponsor;
import org.oregonask.utils.HibernateUtil;

public class SponsorService extends AbstractService {

	public SponsorService() {
		super(Sponsor.class);
	}

	@Override
	public Object find(int id) {
		
		Sponsor sponsor = null;
		
		try {
			startOperation();
			sponsor = (Sponsor) getSession().createCriteria(Sponsor.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(sponsor.getNutritions());
			getTransaction().commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(getSession());
		}
		
		return sponsor;
	}
}

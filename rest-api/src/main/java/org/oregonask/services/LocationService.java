package org.oregonask.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.oregonask.entities.Location;
import org.oregonask.utils.HibernateUtil;

public class LocationService extends AbstractService {

	public LocationService() {
		super(Location.class);
	}
	
	@Override
	public Object find(Long id) {
		
		Location location = null;
		
		try {
			startOperation();
			location = (Location) getSession().createCriteria(Location.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(location.getPrograms());
			Hibernate.initialize(location.getCongressionalDistrict());
			Hibernate.initialize(location.getCounty());
			Hibernate.initialize(location.getSchoolDistrict());
			getTransaction().commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(getSession());
		}
		
		return location;
	}
}

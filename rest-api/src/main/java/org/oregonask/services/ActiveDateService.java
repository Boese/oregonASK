package org.oregonask.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.oregonask.entities.ActiveDate;
import org.oregonask.utils.HibernateUtil;

public class ActiveDateService extends AbstractService {
	
	public ActiveDateService() {
		super(ActiveDate.class);
	}
	
	@Override
	public Object find(Long id) {
		
		ActiveDate activeDate = null;
		
		try {
			startOperation();
			activeDate = (ActiveDate) getSession().createCriteria(ActiveDate.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(activeDate.getPrograms());
			getTransaction().commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(getSession());
		}
		
		return activeDate;
	}

}

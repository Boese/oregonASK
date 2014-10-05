package org.oregonask.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.oregonask.entities.Provider;
import org.oregonask.utils.HibernateUtil;

public class ProviderService extends AbstractService {
	
	public ProviderService() {
		super(Provider.class);
	}
	
	@Override
	public Object find(Long id) {
		
		Provider provider = null;
		
		try {
			startOperation();
			provider = (Provider) getSession().createCriteria(Provider.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(provider.getPrograms());
			getTransaction().commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(getSession());
		}
		
		return provider;
	}
}

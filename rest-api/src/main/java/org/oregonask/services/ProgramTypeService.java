package org.oregonask.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.oregonask.entities.ProgramType;
import org.oregonask.utils.HibernateUtil;

public class ProgramTypeService extends AbstractService {
	
	public ProgramTypeService() {
		super(ProgramType.class);
	}
	
	@Override
	public Object find(Long id) {
		
		ProgramType pt = null;
		
		try {
			startOperation();
			pt = (ProgramType) getSession().createCriteria(ProgramType.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(pt.getPrograms());
			getTransaction().commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(getSession());
		}
		
		return pt;
	}

}

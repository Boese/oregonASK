package org.oregonask.services;

import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.criterion.Restrictions;
import org.oregonask.entities.Program;
import org.oregonask.utils.HibernateUtil;

public class ProgramService extends AbstractService {

	public ProgramService() {
		super(Program.class);
	}
	
	@Override
	public Object find(Long id) {
		
		Program program = null;
		
		try {
			startOperation();
			program = (Program) getSession().createCriteria(Program.class).add(Restrictions.idEq(id)).uniqueResult();
			Hibernate.initialize(program.getActiveDate());
			Hibernate.initialize(program.getKidsCount());
			Hibernate.initialize(program.getLocations());
			Hibernate.initialize(program.getProgramType());
			Hibernate.initialize(program.getProvider());
			Hibernate.initialize(program.getSchools());
			getTransaction().commit();
		} catch (HibernateException e) {
			handleException(e);
		} finally {
			HibernateUtil.close(getSession());
		}
		
		return program;
	}
}

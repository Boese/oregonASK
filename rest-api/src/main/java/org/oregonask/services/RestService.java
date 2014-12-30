package org.oregonask.services;

import java.util.Calendar;
import java.util.List;

import org.hibernate.Session;
import org.oregonask.entities.IEntity;
import org.oregonask.entities.IUpdateLastEditBy;
import org.oregonask.utils.HibernateUtil;
import org.oregonask.utils.ReturnMessage;

import spark.Request;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestService {
	private final ObjectMapper mapper = new ObjectMapper();
	private Session session;
	
	public RestService() {
		session = HibernateUtil.getSessionFactory().openSession();
	}
	
	public Object get(Request request) {
		try {
			String[] wildcard = request.splat();
			String[] params = wildcard[0].split("/");
			List<?> objects;
			Object object;
			switch(params.length) {
			// Normal get /api/Entity -> return all entities
			case 1: 
				session.beginTransaction();
				objects = session.createQuery("from " + params[0]).list();
				session.getTransaction().commit();
				return objects;
			case 2:
				// Normal get /api/Entity/id -> return single entity where id=:id
				session.beginTransaction();
				object = session.createQuery("from " + params[0] + " where id=" + params[1]).uniqueResult();
				((IEntity) object).initialize();
				session.getTransaction().commit();
				return object;
			}
		} catch (Exception e) {
			HibernateUtil.rollback(session.getTransaction());
			e.printStackTrace();
		} finally {
			HibernateUtil.close(session);
		}
		return new ReturnMessage("failed");
	}
	
	public Object put(Request request) {
		try {
			String token = request.headers("Token").toString().replace('"',' ').trim();
			String email = AuthService.getInstance().getUserEmail(token);
			Class<?> clazz = Class.forName("org.oregonask.entities." + request.splat()[0]);
			Object obj = mapper.readValue(request.body(), clazz);
			((IUpdateLastEditBy) obj).setLastEditBy(email);
			((IUpdateLastEditBy) obj).setTimeStamp((new java.sql.Timestamp(Calendar.getInstance().getTime().getTime())).toString());
			session.beginTransaction();
			session.saveOrUpdate(obj);
			session.getTransaction().commit();
			return new ReturnMessage("success");
		} catch (Exception e) {
			HibernateUtil.rollback(session.getTransaction());
			e.printStackTrace();
			return new ReturnMessage("failed");
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	public Object delete(Request request) {
		try {
			session.beginTransaction();
			Object obj = session.createQuery("from " + request.splat()[0] + " where id=" + request.params(":id")).uniqueResult();
			session.delete(obj);
			session.getTransaction().commit();
			return new ReturnMessage("success");
		} catch (Exception e) {
			HibernateUtil.rollback(session.getTransaction());
			e.printStackTrace();
			return new ReturnMessage("failed");
		} finally  {
			HibernateUtil.close(session);
		}
	}
}

package org.oregonask.services;

import java.util.List;

import org.hibernate.HibernateException;
import org.oregonask.entities.IEntity;
import org.oregonask.entities.User;
import org.oregonask.utils.HibernateUtil;
import org.oregonask.utils.ReturnMessage;

import spark.Request;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestService {
	private final HibernateService hibService = new HibernateService();
	private final ObjectMapper mapper = new ObjectMapper();
	
	public RestService() {}
	
	public Object get(Request request) {
		try {
			String[] wildcard = request.splat();
			String[] params = wildcard[0].split("/");
			List<?> objects;
			Object object;
			switch(params.length) {
			// Normal get /api/Entity -> return all entities
			case 1: 
				hibService.startOperation();
				objects = hibService.getSession().createQuery("from " + params[0]).list();
				hibService.getTx().commit();
				return objects;
			case 2:
				// Normal get /api/Entity/id -> return single entity where id=:id
				hibService.startOperation();
				object = hibService.getSession().createQuery("from " + params[0] + " where id=" + params[1]).uniqueResult();
				((IEntity) object).initialize();
				hibService.getTx().commit();
				return object;
			}
		} catch (HibernateException e) {
			hibService.handleException(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		  finally {
			HibernateUtil.close(hibService.getSession());
		}
		return new ReturnMessage("failed");
	}
	
	public Object put(Request request) {
		try {
			hibService.startOperation();
			Class<?> clazz = Class.forName("org.oregonask.entities." + request.splat()[0]);
			Object obj = mapper.readValue(request.body(), clazz);
			if(obj.getClass().isInstance(User.class))
				throw new Exception();
			hibService.getSession().saveOrUpdate(obj);
			hibService.getTx().commit();
			return new ReturnMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMessage("failed");
		} finally {
			HibernateUtil.close(hibService.getSession());
		}
	}
	
	public Object delete(Request request) {
		try {
			hibService.startOperation();
			Object obj = hibService.getSession().createQuery("from " + request.splat()[0] + " where id=" + request.params(":id")).uniqueResult();
			hibService.getSession().delete(obj);
			hibService.getTx().commit();
			return new ReturnMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMessage("failed");
		} finally {
			HibernateUtil.close(hibService.getSession());
		}
	}
}

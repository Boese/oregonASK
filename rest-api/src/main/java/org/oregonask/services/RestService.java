package org.oregonask.services;

import java.util.Calendar;
import java.util.List;

import org.hibernate.HibernateException;
import org.oregonask.entities.IEntity;
import org.oregonask.entities.IUpdateLastEditBy;
import org.oregonask.utils.HibernateUtil;
import org.oregonask.utils.ReturnMessage;

import spark.Request;

import com.fasterxml.jackson.databind.ObjectMapper;

public class RestService {
	private final HibernateService h = new HibernateService();
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
				h.startOperation();
				objects = h.getSession().createQuery("from " + params[0]).list();
				h.getTx().commit();
				return objects;
			case 2:
				// Normal get /api/Entity/id -> return single entity where id=:id
				h.startOperation();
				object = h.getSession().createQuery("from " + params[0] + " where id=" + params[1]).uniqueResult();
				((IEntity) object).initialize();
				h.getTx().commit();
				return object;
			}
		} catch (HibernateException e) {
			h.handleException(e);
		} catch (Exception e) {
			e.printStackTrace();
		}
		  finally {
			HibernateUtil.close(h.getSession());
		}
		return new ReturnMessage("failed");
	}
	
	public Object put(Request request) {
		try {
			String token = request.headers("Token").toString().replace('"',' ').trim();
			String email = AuthService.getInstance().getUserEmail(token);
			h.startOperation();
			Class<?> clazz = Class.forName("org.oregonask.entities." + request.splat()[0]);
			Object obj = mapper.readValue(request.body(), clazz);
			((IUpdateLastEditBy) obj).setLastEditBy(email);
			((IUpdateLastEditBy) obj).setTimeStamp((new java.sql.Timestamp(Calendar.getInstance().getTime().getTime())).toString());
			h.getSession().saveOrUpdate(obj);
			h.getTx().commit();
			return new ReturnMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMessage("failed");
		} finally {
			HibernateUtil.close(h.getSession());
		}
	}
	
	public Object delete(Request request) {
		try {
			h.startOperation();
			Object obj = h.getSession().createQuery("from " + request.splat()[0] + " where id=" + request.params(":id")).uniqueResult();
			h.getSession().delete(obj);
			h.getTx().commit();
			return new ReturnMessage("success");
		} catch (Exception e) {
			e.printStackTrace();
			return new ReturnMessage("failed");
		} finally {
			HibernateUtil.close(h.getSession());
		}
	}
}

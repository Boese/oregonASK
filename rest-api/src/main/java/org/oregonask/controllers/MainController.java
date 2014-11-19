package org.oregonask.controllers;

import static spark.Spark.after;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.put;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.oregonask.entities.IEntity;
import org.oregonask.exceptions.DataAccessLayerException;
import org.oregonask.utils.HibernateUtil;
import org.oregonask.utils.JsonTransformer;
import org.oregonask.utils.ReturnMessage;

import com.fasterxml.jackson.databind.ObjectMapper;


public class MainController {
	private final ObjectMapper mapper = new ObjectMapper();
	
	private Session session;
	private Transaction tx;
	
	protected void startOperation() throws HibernateException {
		session = HibernateUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
	}
	
	protected void handleException(HibernateException e) throws DataAccessLayerException {
		HibernateUtil.rollback(tx);
		throw new DataAccessLayerException(e);
	}
	
	public MainController() {
		
		// api/* -> return all *
		// api/* /:id -> return * with id
		get("/api/*", (request, response) -> {
			try {
				String[] wildcard = request.splat();
				String[] params = wildcard[0].split("/");
				List<?> objects;
				Object object;
				switch(params.length) {
				case 1: 
					startOperation();
					objects = session.createQuery("from " + params[0]).list();
					tx.commit();
					return objects;
				case 2: 
					
					// api/new/* -> return properties of class, for creating entity
					if(params[0].equalsIgnoreCase("new")) {
						startOperation();
						Class<?> clazz = Class.forName("org.oregonask.entities." + params[1]);
						
						String[] columnNames = HibernateUtil.getSessionFactory().getClassMetadata(clazz).getPropertyNames();
						Type[] columnTypes = HibernateUtil.getSessionFactory().getClassMetadata(clazz).getPropertyTypes();
						Map<Object,Type> o = new HashMap<Object,Type>();
						int i = 0;
						for (String string : columnNames) {
							o.put(string, columnTypes[i]);
							i++;
						}
						Map<Object,Object> r = new HashMap<Object,Object>();
						for (Map.Entry<Object, Type> ob : o.entrySet()) {
							if(ob.getValue().isEntityType()) {
								String tableName = ob.getKey().toString();
								tableName = Character.toUpperCase(tableName.charAt(0)) + tableName.substring(1);
								r.put(tableName, "id");
							} else if(!ob.getValue().isCollectionType())  {
								r.put(ob.getKey(), ob.getValue().getName());
							}
						}
						
						tx.commit();
						return r;
					} else {
						startOperation();
						object = session.createQuery("from " + params[0] + " where id=" + params[1]).uniqueResult();
						((IEntity) object).initialize();
						tx.commit();
						return object;
					}
				}
			} catch (HibernateException e) {
				handleException(e);
			} catch (Exception e) {
				e.printStackTrace();
			}
			  finally {
				HibernateUtil.close(session);
			}
			return new ReturnMessage("failed");
		}, new JsonTransformer());

		// api/* -> create or update *
		put("/api/*", (request, response) -> {
			try {
				startOperation();
				Class<?> clazz = Class.forName("org.oregonask.entities." + request.splat()[0]);
				Object obj = mapper.readValue(request.body(), clazz);
				session.saveOrUpdate(obj);
				tx.commit();
				response.status(201);
				return new ReturnMessage("success");
			} catch (Exception ex) {
				ex.printStackTrace();
				return new ReturnMessage("failed");
			} finally {
				HibernateUtil.close(session);
			}
		}, new JsonTransformer());

		// api/*/:id -> delete * where id=:id
		delete("/api/*/:id", (request, response) -> {
			try {
				startOperation();
				Object obj = session.createQuery("from " + request.splat()[0] + " where id=" + request.params(":id")).uniqueResult();
				session.delete(obj);
				tx.commit();
				return new ReturnMessage("success");
			} catch (Exception ex) {
				ex.printStackTrace();
				return new ReturnMessage("failed");
			} finally {
				HibernateUtil.close(session);
			}
		}, new JsonTransformer());
		
		after((req, res) -> {
			res.type("application/json");
		});
	}
}

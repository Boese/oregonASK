package org.oregonask.controllers;

import static spark.Spark.after;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.put;

import java.util.List;

import org.hibernate.HibernateException;
import org.oregonask.utils.HibernateUtil;
import org.oregonask.utils.JsonTransformer;
import org.oregonask.utils.ReturnMessage;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.oregonask.exceptions.DataAccessLayerException;

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
		
		get("/api/*/:id", (request, response) -> {
			Object object = null;
			try {
				//String[] bars = request.splat();
				try {
					startOperation();
					object = session.createQuery("from " + request.splat()[0] + " where id=" + request.params(":id")).uniqueResult();
					tx.commit();
				} catch (HibernateException e) {
					handleException(e);
				} finally {
					HibernateUtil.close(session);
				}
			} catch (Exception ex) {
				ex.printStackTrace();
				return new ReturnMessage("failed");
			}
			
			return object;
		}, new JsonTransformer());
		
		get("/api/*", (request, response) -> {
			List<?> objects = null;
			try{
				try {
					startOperation();
					objects = session.createQuery("from " + request.splat()[0]).list();
					tx.commit();
				} catch (HibernateException e) {
					handleException(e);
				} finally {
					HibernateUtil.close(session);
				}
			}catch(Exception ex) {
				ex.printStackTrace();
				return new ReturnMessage("failed");
			}
			
			return objects;
		}, new JsonTransformer());

		put("/api/*", (request, response) -> {
			try {
				Class<?> clazz = Class.forName("org.oregonask.entities." + request.splat()[0]);
				Object obj = mapper.readValue(request.body(), clazz);
				startOperation();
				session.saveOrUpdate(obj);
				tx.commit();
				response.status(201);
				return new ReturnMessage("success");
			} catch (Exception ex) {
				ex.printStackTrace();
				return new ReturnMessage("failed");
			}
		}, new JsonTransformer());

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
			}
		}, new JsonTransformer());
		
		after((req, res) -> {
			res.type("application/json");
		});
	}
}

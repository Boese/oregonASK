package org.oregonask.controllers;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.halt;
import static spark.Spark.options;
import static spark.Spark.put;

import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.type.Type;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.oregonask.entities.IEntity;
import org.oregonask.entities.User;
import org.oregonask.exceptions.DataAccessLayerException;
import org.oregonask.utils.HibernateUtil;
import org.oregonask.utils.JsonTransformer;
import org.oregonask.utils.ReturnMessage;

import spark.Request;

import com.fasterxml.jackson.databind.ObjectMapper;


public class MainController {
	private static final int TIME_OUT = (60*60*1000);
	private static final String KEY = "OREGONASK47KEY83_20_14";
	private final ObjectMapper mapper = new ObjectMapper();
	
	private Session session;
	private Transaction tx;
	
	private Map<TimerToken,String> tokens = new HashMap<TimerToken,String>();
	private Timer timer = new Timer();
	
	protected void startOperation() throws HibernateException {
		session = HibernateUtil.getSessionFactory().openSession();
		tx = session.beginTransaction();
	}
	
	protected void handleException(HibernateException e) throws DataAccessLayerException {
		HibernateUtil.rollback(tx);
		throw new DataAccessLayerException(e);
	}
	
	private class TimerToken extends TimerTask {
		@Override
		public void run() {
			tokens.remove(this);
		}
	}
	
	public MainController() {
		options("/api/*", (request, response) -> {
			return 200;
		});
		// api/login
		get("/api/login", (request, response) -> {
			// Check username:password
			// if success generate token
			// Remove token after 1 hour -> force re-login
			try {
				String req = request.headers("Authorization");
				req = req.replace("Base", "").trim();
				byte[] decoded = Base64.getDecoder().decode(req.getBytes());
				String auth = new String(decoded);
				String[] auths = auth.split(":");
				String email = auths[0];
				String pass = auths[1];
				
				startOperation();
				User user = (User) session.createQuery("from User where email = :email")
						.setParameter("email", email)
						.uniqueResult();
				tx.commit();
				
				UUID random = UUID.randomUUID();
				pass = BCrypt.hashpw(pass, user.getSalt());
				if(pass.equals(user.getPassword())) {
					TimerToken timertoken = new TimerToken();
					timer.schedule(timertoken, TIME_OUT);
					tokens.put(timertoken, random.toString());
					JSONObject tokenjson = new JSONObject();
					tokenjson.put("Token", random.toString());
					return tokenjson;
				}
			} catch(Exception e) {
				e.printStackTrace();
			} finally {
				HibernateUtil.close(session);
			}
			return new ReturnMessage("failed");
		});
		
		put("api/login", (request, response) -> {
			// Check username:password:key
			// if success create new account
			try {
				startOperation();
				String req = request.headers("Authorization");
				req = req.replace("Base", "").trim();
				byte[] decoded = Base64.getDecoder().decode(req.getBytes());
				String auth = new String(decoded);
				String[] auths = auth.split(":");
				String email = auths[0];
				String pass = auths[1];
				String key = auths[2];
				if(!key.equalsIgnoreCase(KEY))
					throw new Exception();
				
				User user = new User();
				user.setEmail(email);
				String salt = BCrypt.gensalt(12);
				String password = BCrypt.hashpw(pass, salt);
				user.setPassword(password);
				user.setSalt(salt);
				session.saveOrUpdate(user);
				tx.commit();
				response.status(201);
				return new ReturnMessage("success");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				HibernateUtil.close(session);
			}
			return new ReturnMessage("failed");
		}, new JsonTransformer());
		
		// authorized
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
							} else if(ob.getValue().isCollectionType() && ob.getKey().toString().contains("Info")) {
								String tableName = ob.getKey().toString();
								tableName = Character.toUpperCase(tableName.charAt(0)) + tableName.substring(1);
								r.put(tableName, "Set");
							}
						}
						
						tx.commit();
						return r;
						
						// Normal get /api/Entity/id -> return single entity where id=:id
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

		// authorized
		// api/* -> create or update *
		put("/api/*", (request, response) -> {
			try {
				startOperation();
				Class<?> clazz = Class.forName("org.oregonask.entities." + request.splat()[0]);
				Object obj = mapper.readValue(request.body(), clazz);
				if(obj.getClass().isInstance(User.class))
					throw new Exception();
				session.saveOrUpdate(obj);
				tx.commit();
				response.status(201);
				return new ReturnMessage("success");
			} catch (Exception e) {
				e.printStackTrace();
				return new ReturnMessage("failed");
			} finally {
				HibernateUtil.close(session);
			}
		}, new JsonTransformer());

		// authorized
		// api/*/:id -> delete * where id=:id
		delete("/api/*/:id", (request, response) -> {
			try {
				startOperation();
				Object obj = session.createQuery("from " + request.splat()[0] + " where id=" + request.params(":id")).uniqueResult();
				session.delete(obj);
				tx.commit();
				return new ReturnMessage("success");
			} catch (Exception e) {
				e.printStackTrace();
				return new ReturnMessage("failed");
			} finally {
				HibernateUtil.close(session);
			}
		}, new JsonTransformer());
		before((req,res) -> {
			if(!req.requestMethod().equalsIgnoreCase("options"))
				authService(req);
		});
		after((req, res) -> {
			res.type("application/json");
			res.header("Access-Control-Allow-Headers", req.headers("Access-Control-Request-Headers"));
			res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET,PUT,DELETE,OPTIONS");
		});
	}
	
	private void authService(Request req) {
		Object token = req.headers("Token");
		if(token != null) {
			token = token.toString().replace('"',' ').trim();
			if(!tokens.containsValue(token.toString())) {
				halt(401);
			}
		} else {
			if(!req.pathInfo().equals("/api/login")) {
				halt(401);
			}
		}
	}
}

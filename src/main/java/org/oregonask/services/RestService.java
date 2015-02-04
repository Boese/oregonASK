package org.oregonask.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.Type;
import org.json.JSONObject;
import org.oregonask.entities.DataTableInfo;
import org.oregonask.entities.IEntity;
import org.oregonask.entities.IUpdateLastEditBy;
import org.oregonask.utils.HibernateUtil;
import org.oregonask.utils.JsonTransformer;
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
			Class<?> clazz = HibernateUtil.getClass(params[0]);
			switch(params.length) {
			// Normal get /api/Entity -> return all entities
			case 1:
				// Returns list of tables with top 3 fields
				if(params[0].equalsIgnoreCase("initialize"))
					return getTables();
				
				// Normal get all entities
				session.beginTransaction();
				objects = session.createQuery("from " + clazz.getSimpleName()).list();
				session.getTransaction().commit();
				return objects;
				
			case 2:
				// get /api/Entity/new -> return entity properties
				if(params[1].equalsIgnoreCase("new"))
					return getNewModel(clazz);

				// Normal get /api/Entity/id -> return single entity where id=:id
				session.beginTransaction();
				object = session.createQuery("from " + clazz.getSimpleName() + " where id=" + params[1]).uniqueResult();
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
			Class<?> clazz = HibernateUtil.getClass(request.splat()[0]);
			session.beginTransaction();
			Object obj = mapper.readValue(request.body(), clazz);
			try {
			((IUpdateLastEditBy) obj).setLastEditBy(email);
			((IUpdateLastEditBy) obj).setTimeStamp((new java.sql.Timestamp(Calendar.getInstance().getTime().getTime())).toString());
			} catch(Exception e) {}
			session.merge(obj);
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
			Class<?> clazz = HibernateUtil.getClass(request.splat()[0]);
			session.beginTransaction();
			Object obj = session.createQuery("from " + clazz.getSimpleName() + " where id=" + request.params(":id")).uniqueResult();
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
	
	// Helper functions
	private Object getNewModel(Class<?> clazz) {
		JSONObject object = null;
		Map<String,Object> properties = new HashMap<String,Object>();
		try {
			object = new JSONObject(new JsonTransformer().render(clazz.newInstance()));
			
			// get a JsonObject of all properties
			List<String> propertyNames = new ArrayList<String>(Arrays.asList(JSONObject.getNames(object)));
			
			for (String propertyName : propertyNames) {
				Type type = getType(propertyName, clazz);
				// One to Many
				if(type != null && type.isCollectionType()) {
					String className = propertyName;
			    	if(className.endsWith("S"))
			    		className = className.substring(0,className.length()-1);
			    	className = className.replace("_", "");
			    	Class<?> clazzJoin = HibernateUtil.getClass(className);
			    	JSONObject joinObject = new JSONObject(new JsonTransformer().render(clazzJoin.newInstance()));
			    	Map<String,Object> joinProperties = new HashMap<String,Object>();
			    	for (String name : JSONObject.getNames(joinObject)) {
						joinProperties.put(name, "");
					}
			    	Object[] temp = new Object[1];
			    	temp[0] = joinProperties;
			    	properties.put(propertyName, temp);
				}
				else {
					// Many to One
					if(propertyName.equalsIgnoreCase("SCHOOL_ID") || propertyName.equalsIgnoreCase("SPONSOR_ID")) {
						Map<String,Object> temp = new HashMap<String,Object>();
			    		temp.put("id", "");
			    		properties.put(propertyName.replace("_ID", ""), temp);
					}
					// Property
					else
						properties.put(propertyName, "");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return properties;
	}
	
	private Type getType(String property, Class<?> clazz) {
		ClassMetadata metadata = HibernateUtil.getSessionFactory().getClassMetadata(clazz);
		String[] names = metadata.getPropertyNames();
		Type[] types = metadata.getPropertyTypes();
		int i = 0;
		for (String name : names) {
			if(name.equalsIgnoreCase(property.replace("_", "")))
				return types[i]; i++;
		}
		
		return null;
	}
	
	private Object getTables() {
		List<DataTableInfo> dataBaseTableInfos = new ArrayList<DataTableInfo>();
		dataBaseTableInfos.add(new DataTableInfo().setModel("School").setFirst("NAME").setSecond("CITY").setThird("COUNTY"));
		dataBaseTableInfos.add(new DataTableInfo().setModel("Sponsor").setFirst("NAME").setSecond("AGR_NUMBER").setThird("SPONSOR_TYPE"));
		dataBaseTableInfos.add(new DataTableInfo().setModel("Summerfood").setFirst("SITE_NAME").setSecond("SITE_NUMBER").setThird("CITY"));
		dataBaseTableInfos.add(new DataTableInfo().setModel("Nutrition").setFirst("SITE_NAME").setSecond("SITE_NUMBER").setThird("CITY"));
		dataBaseTableInfos.add(new DataTableInfo().setModel("Program").setFirst("NAME").setSecond("LICENSE_NUMBER").setThird("CITY"));
		return dataBaseTableInfos;
	}
}

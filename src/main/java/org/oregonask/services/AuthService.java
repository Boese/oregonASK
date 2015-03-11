package org.oregonask.services;

import static spark.Spark.halt;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.hibernate.Session;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.oregonask.entities.User;
import org.oregonask.utils.HibernateUtil;
import org.oregonask.utils.ReturnMessage;

import spark.Request;

public class AuthService {
	private static final AuthService INSTANCE = new AuthService();
	private static final int TIME_OUT = (8*60*60*1000);
	private static final String HASHED_CREATE_KEY = "$2a$12$SL4bU5/cgfR1Y1YwFbVbEuKHMF47VZDK4Fk2Ry74ETaEfXL5i7la6";
	
	private Map<TimerToken,UserSession> tokens = new HashMap<TimerToken,UserSession>();
	private Timer timer = new Timer();
	
	private AuthService() {}
	
	public static AuthService getInstance() {
		return AuthService.INSTANCE;
	}
	
	private class TimerToken extends TimerTask {
		@Override
		public void run() {
			tokens.remove(this);
		}
	}
	
	private class UserSession {
		private String token;
		private String email;
		
		UserSession(String token, String email) {
			this.setToken(token);
			this.setEmail(email);
		}
		
		public String getToken() {
			return token;
		}
		public void setToken(String token) {
			this.token = token;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
	}
	
	public Object login(Request request) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
		String req = request.headers("Authorization");
		req = req.replace("Base", "").trim();
		byte[] decoded = Base64.getDecoder().decode(req.getBytes());
		String auth = new String(decoded);
		String[] auths = auth.split(":");
		String email = auths[0];
		String pass = auths[1];
		
		session.beginTransaction();
		User user = (User) session.createQuery("from User where EMAIL = :email")
				.setParameter("email", email)
				.uniqueResult();
		session.getTransaction().commit();
		
		if(user == null)
			return null;
		
		UUID random = UUID.randomUUID();
		String randomS = random.toString();
		pass = BCrypt.hashpw(pass, user.getSalt());
		if(pass.equals(user.getPassword())) {
			TimerToken timertoken = new TimerToken();
			timer.schedule(timertoken, TIME_OUT);
			UserSession userSession = new UserSession(randomS,email);
			tokens.put(timertoken, userSession);
			JSONObject tokenjson = new JSONObject();
			tokenjson.put("Token", randomS);
			System.out.println("New user logged in : " + email);
			return tokenjson;
		}
		else
			return null;
		} catch(Exception e) {
			e.printStackTrace();
			HibernateUtil.rollback(session.getTransaction());
			return null;
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	public ReturnMessage createAccount(Request request) {
		Session session = HibernateUtil.getSessionFactory().openSession();
		try {
		String req = request.headers("Authorization");
		req = req.replace("Base", "").trim();
		byte[] decoded = Base64.getDecoder().decode(req.getBytes());
		String auth = new String(decoded);
		String[] auths = auth.split(":");
		String email = auths[0];
		String pass = auths[1];
		String key = auths[2];
		
		if(!BCrypt.checkpw(key, HASHED_CREATE_KEY))
			return null;
		
		User user = new User();
		user.setEmail(email);
		String salt = BCrypt.gensalt(10);
		String password = BCrypt.hashpw(pass, salt);
		user.setPassword(password);
		user.setSalt(salt);
		
		session.beginTransaction();
		session.saveOrUpdate(user);
		session.getTransaction().commit();
		
		return new ReturnMessage("success");
		} catch(Exception e) {
			e.printStackTrace();
			HibernateUtil.rollback(session.getTransaction());
			return new ReturnMessage("failed");
		} finally {
			HibernateUtil.close(session);
		}
	}
	
	public void authorize(Request req) {
		Object token = req.headers("Token");
		if(token != null) {
			token = token.toString().replace('"',' ').trim();
			if(getUserEmail(token.toString()) == null) {
				halt(401);
			}
		} else {
			if(!req.pathInfo().equals("/api/login")) {
				halt(401);
			}
		}
	}
	
	public String getUserEmail(String token) {
		String email = null;
		for (UserSession user : tokens.values()) {
			if(user.getToken().equals(token)) {
				email = user.getEmail(); break;
			}
		}
		return email;
	}
}

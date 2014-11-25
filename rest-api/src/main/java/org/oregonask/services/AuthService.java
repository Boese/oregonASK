package org.oregonask.services;

import static spark.Spark.halt;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;
import org.oregonask.entities.User;
import org.oregonask.utils.ReturnMessage;

import spark.Request;

public class AuthService {
	private static final int TIME_OUT = (60*60*1000);
	private static final String KEY = "OREGONASK47KEY83_20_14";
	private final HibernateService hibService = new HibernateService();
	
	private Map<TimerToken,String> tokens = new HashMap<TimerToken,String>();
	private Timer timer = new Timer();
	
	public AuthService() {}
	
	private class TimerToken extends TimerTask {
		@Override
		public void run() {
			tokens.remove(this);
		}
	}
	
	public JSONObject login(Request request) {
		try {
		String req = request.headers("Authorization");
		req = req.replace("Base", "").trim();
		byte[] decoded = Base64.getDecoder().decode(req.getBytes());
		String auth = new String(decoded);
		String[] auths = auth.split(":");
		String email = auths[0];
		String pass = auths[1];
		
		hibService.startOperation();
		User user = (User) hibService.getSession().createQuery("from User where email = :email")
				.setParameter("email", email)
				.uniqueResult();
		hibService.getTx().commit();
		
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
		else
			throw new Exception();
		} catch(Exception e) {
			return null;
		}
	}
	
	public ReturnMessage createAccount(Request request) {
		try {
		hibService.startOperation();
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
		hibService.getSession().saveOrUpdate(user);
		hibService.getTx().commit();
		return new ReturnMessage("success");
		} catch(Exception e) {
			return new ReturnMessage("failed");
		}
	}
	
	public void authorize(Request req) {
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

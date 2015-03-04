package org.oregonask.controllers;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.post;
import static spark.Spark.put;

import org.json.JSONArray;
import org.json.JSONObject;
import org.oregonask.mysqlService.DatabaseDriver;
import org.oregonask.services.AuthService;
import org.oregonask.services.ContactService;
import org.oregonask.utils.JsonTransformer;
import org.oregonask.utils.ReturnMessage;

public class MySqlCtrl {
	private ContactService contactService = new ContactService();
	private DatabaseDriver driver = new DatabaseDriver();
	
	public MySqlCtrl() {
		
		// ********non-authorized******** //
		options("/*", (request, response) -> {
			return 200;
		});
		// api/login -> login
		get("/api/login", (request, response) -> {
			return AuthService.getInstance().login(request);
		});
		
		// api/login -> create account
		put("api/login", (request, response) -> {
			return AuthService.getInstance().createAccount(request);
		},  new JsonTransformer());
		
		// ********authorized******** //
		
		// --------------CONSTANT CONTACTS API---------------->
		// GET api/contactsAPI
		get("/api/contactsAPI/*", (request, response) -> {
			return contactService.getRequest(request);
		});
		
		// PUT api/contactsAPI
		put("/api/contactsAPI/*", (request,response) -> {
			return contactService.putRequest(request);
		});
		
		// POST api/contactsAPI
		post("/api/contactsAPI/*", (request,response) -> {
			return contactService.postRequest(request);
		});
		
		// DELETE api/contactsAPI
		delete("/api/contactsAPI/*", (request,response) -> {
			return contactService.deleteRequest(request);
		});
		// --------------------------------------------------->
		
		// --------------REST API---------------->
		// api/* -> return all *
		// api/* /:id -> return * with id
		get("/api/*", (request, response) -> {
			String[] params = request.splat()[0].split("/");
			
			switch(params.length) {
			case 1: 
				if(params[0].equalsIgnoreCase("initialize"))
					return driver.getTables();
				else
					return driver.get(params[0]);
			case 2:
				if(params[1].equalsIgnoreCase("new"))
					return driver.getProperties(params[0].toString());
				
				else
					return driver.getOne(params[0], Integer.parseInt(params[1]));
			}
			return new ReturnMessage("failed");
		});
	
		// authorized
		// api/* -> create or update *
		put("/api/*", (request, response) -> {
			String[] params = request.splat()[0].split("/");
			String token = request.headers("Token").toString().replace('"',' ').trim();
			String email = AuthService.getInstance().getUserEmail(token);
			
			if(params[0].equalsIgnoreCase("create_table"))
				return driver.addTable(request.body());
			else if(params[0].equalsIgnoreCase("alter_table"))
				return driver.alterTable(request.body());
			else if(params[0].equalsIgnoreCase("search_database"))
				return driver.search(request.body());
			else
				return driver.post(request.body(), request.splat()[0]);
		});
	
		// authorized
		// api/*/:id -> delete * where id=:id
		delete("/api/*", (request, response) -> {
			String[] params = request.splat()[0].split("/");
			
			if(params[0].equalsIgnoreCase("delete_table"))
				return driver.deleteTable(params[1]);
			else {
				int id = Integer.parseInt(params[0]);
				Object table = request.splat()[0];
				return driver.delete(id, table);
			}
		});
		// --------------------------------------------------->
		
		before((req,res) -> {
			// Authorize all requests (ignore options case)
			if(!req.requestMethod().equalsIgnoreCase("options"))
				AuthService.getInstance().authorize(req);
		});
		after((req, res) -> {
			res.type("application/json");
			res.header("Access-Control-Allow-Headers", req.headers("Access-Control-Request-Headers"));
			res.header("Access-Control-Allow-Origin", "*");
	        res.header("Access-Control-Allow-Methods", "GET,PUT,DELETE,OPTIONS");
		});
	}
}

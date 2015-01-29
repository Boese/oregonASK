package org.oregonask.controllers;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.put;
import static spark.Spark.post;

import org.oregonask.services.AuthService;
import org.oregonask.services.ContactService;
import org.oregonask.services.RestService;
import org.oregonask.utils.JsonTransformer;


public class MainController {
	private ContactService contactService = new ContactService();
	
	public MainController() {
		
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
			return new RestService().get(request);
		}, new JsonTransformer());

		// authorized
		// api/* -> create or update *
		put("/api/*", (request, response) -> {
			return new RestService().put(request);
		}, new JsonTransformer());

		// authorized
		// api/*/:id -> delete * where id=:id
		delete("/api/*/:id", (request, response) -> {
			return new RestService().delete(request);
		}, new JsonTransformer());
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

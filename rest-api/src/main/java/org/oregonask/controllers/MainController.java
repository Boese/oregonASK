package org.oregonask.controllers;

import static spark.Spark.after;
import static spark.Spark.before;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.options;
import static spark.Spark.put;

import org.oregonask.services.AuthService;
import org.oregonask.services.RestService;
import org.oregonask.utils.JsonTransformer;


public class MainController {
	private final AuthService authService = new AuthService();
	private final RestService restService = new RestService();
	
	public MainController() {
		options("/api/*", (request, response) -> {
			return 200;
		});
		// api/login -> login
		get("/api/login", (request, response) -> {
			return authService.login(request);
		});
		
		// api/login -> create account
		put("api/login", (request, response) -> {
			return authService.createAccount(request);
		},  new JsonTransformer());
		
		// authorized
		// api/* -> return all *
		// api/* /:id -> return * with id
		get("/api/*", (request, response) -> {
			return restService.get(request);
		}, new JsonTransformer());

		// authorized
		// api/* -> create or update *
		put("/api/*", (request, response) -> {
			return restService.put(request);
		}, new JsonTransformer());

		// authorized
		// api/*/:id -> delete * where id=:id
		delete("/api/*/:id", (request, response) -> {
			return restService.delete(request);
		}, new JsonTransformer());
		before((req,res) -> {
			// Authorize all requests (ignore options case)
			if(!req.requestMethod().equalsIgnoreCase("options"))
				authService.authorize(req);
		});
		after((req, res) -> {
			res.type("application/json");
			res.header("Access-Control-Allow-Headers", req.headers("Access-Control-Request-Headers"));
			res.header("Access-Control-Allow-Origin", "*");
            res.header("Access-Control-Allow-Methods", "GET,PUT,DELETE,OPTIONS");
		});
	}
	
	
}

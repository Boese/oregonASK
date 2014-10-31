package org.oregonask.controllers;

import static spark.Spark.after;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.put;

import org.apache.log4j.Logger;
import org.oregonask.entities.IEntity;
import org.oregonask.services.AbstractService;
import org.oregonask.utils.JsonTransformer;
import org.oregonask.utils.ReturnMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractController {
	
	private static final String API_CONTEXT = "/api";
	private final ObjectMapper mapper = new ObjectMapper();
	
	private static Logger logger = Logger.getLogger(AbstractController.class);
	
	public AbstractController(AbstractService abstractService, Class<?> clazz, String path) {
		
		get(API_CONTEXT + "/" + path, "application/json", (request, response) -> {
			
			try {
				return abstractService.findAll();
			} catch (Exception ex) {
			    logger.error("GET /api/" + path + " failed." + ex);
				ex.printStackTrace();
				return new ReturnMessage("failed");
			}
			
		}, new JsonTransformer());
		
		get(API_CONTEXT + "/" + path + "/:id", "application/json", (request, response) -> {

			try {
				return abstractService.find(Integer.parseInt(request.params(":id")));
			} catch (Exception ex) {
			    logger.error("GET /api/" + path + "/:id" + " failed." + ex);
				ex.printStackTrace();
				return new ReturnMessage("failed");
			}
			
		}, new JsonTransformer());
		
		post(API_CONTEXT + "/" + path, "application/json", (request, response) -> {
			
			try {
				Object obj = mapper.readValue(request.body(), clazz);
				abstractService.save(obj);
				response.status(201);
				return new ReturnMessage("success");
			} catch (Exception ex) {
			    logger.error("POST /api/" + path + " failed." + ex);
				ex.printStackTrace();
				//CHANGED FROM FAILED TO ERROR MESSAGE
				return new ReturnMessage("POST /api/" + path + " failed." + ex);
			}
			
		}, new JsonTransformer());
		
		put(API_CONTEXT + "/" + path + "/:id", "application/json", (request, response) -> {

			try {
				int id = Integer.parseInt(request.params(":id"));
				IEntity newObj = (IEntity) mapper.readValue(request.body(), clazz);
				IEntity obj = (IEntity) abstractService.find(id);
				obj.deepCopy(newObj);
				abstractService.update(obj);
				response.status(201);
				return new ReturnMessage("success");
			} catch (Exception ex) {
			    logger.error("PUT /api/" + path + "/:id" + " failed." + ex);
				ex.printStackTrace();
				return new ReturnMessage("failed");
			}
			
		}, new JsonTransformer());
		
		delete(API_CONTEXT + "/" + path + "/:id", "application/json", (request, response) -> {
			
			try {
				int id = Integer.parseInt(request.params(":id"));
				abstractService.delete(id);
				return new ReturnMessage("failed");
			} catch (Exception ex) {
			    logger.error("DELETE /api/" + path + "/:id" + " failed." + ex);
				ex.printStackTrace();
				return new ReturnMessage("failed");
			}
			
		}, new JsonTransformer());
		
		after((req, res) -> {
			res.type("application/json");
		});
	}

}

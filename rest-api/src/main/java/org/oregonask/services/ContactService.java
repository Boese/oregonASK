package org.oregonask.services;

import java.sql.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONObject;

import spark.Request;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

// CONSTANT CONTACTS API SERVICE
public class ContactService {
	private static final String API_KEY = "n7pgx4r72v4gaz3xutqfreuk";
	private static final String ACCESS_TOKEN = "2dd73999-fa01-407d-848f-2ae5c7bee3e0";
	private JSONObject contacts;
	private JSONArray lists;
	private JSONObject emailCampaigns;
	private ResyncContacts resyncContacts;
	private Timer timer = new Timer();
	private final long resyncTime = 1800000;	// 30 min
	
	public ContactService() {
		resyncContacts = new ResyncContacts();
		timer.scheduleAtFixedRate(resyncContacts, 0, new Date(resyncTime).getTime());
	}
	
	private class ResyncContacts extends TimerTask {
		@Override
		public void run() {
			initializeContacts();
			System.out.println("synced contacts");
			initializeLists();
			System.out.println("synced lists");
			initializeEmailCampaigns();
			System.out.println("synced email campaigns");
		}
	}
	
	// Generate query parameters
	private Map<String,Object> getParams(Map<String,String[]> params) {
		//queryParams
		Map<String,Object> queryParams = new HashMap<String,Object>();
		//default queryParams
		queryParams.put("api_key", API_KEY);
		queryParams.put("access_token", ACCESS_TOKEN);
		//optional queryParams
		for (Map.Entry<String, String[]> queries : params.entrySet()) {
			queryParams.put(queries.getKey(), queries.getValue()[0]);
		}
		return queryParams;
	}
	
	// Initialize Lists
	private void initializeLists() {
		try {
			JSONArray tempLists = new JSONArray();
			Map<String,String[]> params = new HashMap<String,String[]>();
			HttpResponse<JsonNode> jsonResponse = Unirest.get("https://api.constantcontact.com/v2/lists")
					  .header("accept", "application/json")
					  .queryString(getParams(params))
					  .asJson();
			tempLists = new JSONArray(jsonResponse.getBody().toString());
			lists = tempLists;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	// Initialize Email Campaigns
	private void initializeEmailCampaigns() {
		try {
			JSONObject tempCampaigns = new JSONObject();
			String next = "";
			Map<String,String[]> params = new HashMap<String,String[]>();
			String[] temp2 = new String[1];
			temp2[0] = "50";
			params.put("limit", temp2);
			int count = 0;
			while(next != null) {
				HttpResponse<JsonNode> jsonResponse = Unirest.get("https://api.constantcontact.com/v2/emailmarketing/campaigns")
						  .header("accept", "application/json")
						  .queryString(getParams(params))
						  .asJson();
				JSONObject o = new JSONObject(jsonResponse.getBody().toString());
				next = null;
				int i = 0;
				try {
				next = o.getJSONObject("meta").getJSONObject("pagination").getString("next_link");
				i = next.indexOf("=") + 1;
				} catch(Exception e) {}
				
				if(next != null){
					params.remove("limit");
					next = next.substring(i);
					String[] temp = new String[1];
					temp[0] = next;
					params.put("next", temp);
				}
				if(count == 0)
					tempCampaigns = new JSONObject(jsonResponse.getBody().toString());
				else {
					JSONArray ar = new JSONObject(jsonResponse.getBody().toString()).getJSONArray("results");
					for(int x = 0; x < ar.length();x++) {
						tempCampaigns.append("results", ar.get(x));
					}
				}
				count++;
			}
			emailCampaigns = tempCampaigns;
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
	
	// Initialize Contacts
	private void initializeContacts() {
		try {
			JSONObject tempContacts = new JSONObject();
			String next = "";
			Map<String,String[]> params = new HashMap<String,String[]>();
			String[] temp2 = new String[1];
			temp2[0] = "500";
			params.put("limit", temp2);
			int count = 0;
			while(next != null) {
				HttpResponse<JsonNode> jsonResponse = Unirest.get("https://api.constantcontact.com/v2/contacts")
						  .header("accept", "application/json")
						  .queryString(getParams(params))
						  .asJson();
				JSONObject o = new JSONObject(jsonResponse.getBody().toString());
				next = null;
				int i = 0;
				try {
				next = o.getJSONObject("meta").getJSONObject("pagination").getString("next_link");
				i = next.indexOf("=") + 1;
				} catch(Exception e) {}
				
				if(next != null){
					params.remove("limit");
					next = next.substring(i);
					String[] temp = new String[1];
					temp[0] = next;
					params.put("next", temp);
				}
				if(count == 0)
					tempContacts = new JSONObject(jsonResponse.getBody().toString());
				else {
					JSONArray ar = new JSONObject(jsonResponse.getBody().toString()).getJSONArray("results");
					for(int x = 0; x < ar.length();x++) {
						tempContacts.append("results", ar.get(x));
					}
				}
				count++;
			}
			contacts = tempContacts;
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
	
	private String getCustomRequest(Request request) {
		String response = "";
		try {
			String url= request.pathInfo();
			url = url.replace("/api/contactsAPI/", "");
			HttpResponse<JsonNode> jsonResponse = Unirest.get("https://api.constantcontact.com/v2/" + url)
					  .header("accept", "application/json")
					  .queryString(getParams(request.queryMap().toMap()))
					  .asJson();
			if(jsonResponse.getStatus() != 200)
				throw new Exception("invalid url");
			
			if(jsonResponse.getBody().isArray())
				response = new JSONArray(jsonResponse.getBody().toString()).toString();
			else
				response = new JSONObject(jsonResponse.getBody().toString()).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	// GET (all contacts,lists,and campaigns are pre-loaded)
	public String getRequest(Request request) {
		String response = "";
		if(request.splat()[0].equalsIgnoreCase("contacts"))
			response = contacts.toString();
		else if(request.splat()[0].equalsIgnoreCase("lists"))
			response = lists.toString();
		else if(request.splat()[0].equalsIgnoreCase("campaigns"))
			response = emailCampaigns.toString();
		else
			response = getCustomRequest(request);
		return response;
	}
	
	// PUT
	public String putRequest(Request request) {
		String response = "";
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.put("https://api.constantcontact.com/v2/contacts")
					  .header("accept", "application/json")
					  .queryString(getParams(request.queryMap().toMap()))
					  .asJson();
			if(jsonResponse.getStatus() != 200)
				throw new Exception("invalid url");
			
			if(jsonResponse.getBody().isArray())
				response = new JSONArray(jsonResponse.getBody().toString()).toString();
			else
				response = new JSONObject(jsonResponse.getBody().toString()).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	// POST
	public String postRequest(Request request) {
		String response = "";
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.post("https://api.constantcontact.com/v2/contacts")
					  .header("accept", "application/json")
					  .queryString(getParams(request.queryMap().toMap()))
					  .asJson();
			if(jsonResponse.getStatus() != 200)
				throw new Exception("invalid url");
			
			if(jsonResponse.getBody().isArray())
				response = new JSONArray(jsonResponse.getBody().toString()).toString();
			else
				response = new JSONObject(jsonResponse.getBody().toString()).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	
	// DELETE
	public String deleteRequest(Request request) {
		String response = "";
		try {
			HttpResponse<JsonNode> jsonResponse = Unirest.delete("https://api.constantcontact.com/v2/contacts")
					  .header("accept", "application/json")
					  .queryString(getParams(request.queryMap().toMap()))
					  .asJson();
			if(jsonResponse.getStatus() != 200)
				throw new Exception("invalid url");
			
			if(jsonResponse.getBody().isArray())
				response = new JSONArray(jsonResponse.getBody().toString()).toString();
			else
				response = new JSONObject(jsonResponse.getBody().toString()).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
}

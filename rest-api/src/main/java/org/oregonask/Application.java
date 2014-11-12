package org.oregonask;

import static spark.Spark.before;
import static spark.SparkBase.setIpAddress;
import static spark.SparkBase.setPort;
import static spark.SparkBase.staticFileLocation;

import org.apache.log4j.xml.DOMConfigurator;
import org.oregonask.controllers.MainController;
import org.oregonask.utils.HibernateUtil;

import spark.Filter;
import spark.Request;
import spark.Response;


public class Application {
    
    static{
        DOMConfigurator.configure("log4j.xml");
    }
    
    private static void enableCORS(final String origin, final String methods, final String headers) {
	    before(new Filter() {
	        @Override
	        public void handle(Request request, Response response) {
	            response.header("Access-Control-Allow-Origin", origin);
	            response.header("Access-Control-Request-Method", methods);
	            response.header("Access-Control-Allow-Headers", headers);
	        }
	    });
	}

	public static void main(String[] args) {
	    
		setIpAddress("localhost");
		setPort(8080);
		staticFileLocation("/public/app");
		enableCORS("*", "*", "*");
		
		HibernateUtil.getSessionFactory();
		new MainController();
	}
}

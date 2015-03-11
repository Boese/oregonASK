package org.oregonask;

import static spark.SparkBase.setPort;
import org.oregonask.controllers.MySqlCtrl;


public class Application {

	public static void main(String[] args) {
	    
		//Heroku assigns different port each time, hence reading it from process.
        ProcessBuilder process = new ProcessBuilder();
        Integer port;
        if (process.environment().get("PORT") != null) {
            port = Integer.parseInt(process.environment().get("PORT"));
        } else {
            port = 8080;
        }
        setPort(port);
        
		//staticFileLocation("/public/app");
		
		
		//HibernateUtil.getSessionFactory();
		
		//new MainController();
        new MySqlCtrl();
	}
}

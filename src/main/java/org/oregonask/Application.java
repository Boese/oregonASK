package org.oregonask;

import static spark.SparkBase.setPort;
import static spark.SparkBase.staticFileLocation;

import org.apache.log4j.xml.DOMConfigurator;
import org.oregonask.controllers.MainController;
import org.oregonask.utils.HibernateUtil;


public class Application {
    
    static{
        DOMConfigurator.configure("log4j.xml");
    }

	public static void main(String[] args) {
	    
		
        setPort(Integer.parseInt(System.getenv("PORT")));
        
		staticFileLocation("/public/app");
		
		
		HibernateUtil.getSessionFactory();
		new MainController();
	}
}

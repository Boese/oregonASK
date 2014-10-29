package org.oregonask;

import static spark.SparkBase.setIpAddress;
import static spark.SparkBase.setPort;
import static spark.SparkBase.staticFileLocation;

import org.apache.log4j.xml.DOMConfigurator;
import org.oregonask.controllers.NutritionController;
import org.oregonask.controllers.SchoolController;
import org.oregonask.controllers.SponsorController;
import org.oregonask.services.NutritionService;
import org.oregonask.services.SchoolService;
import org.oregonask.services.SponsorService;
import org.oregonask.utils.HibernateUtil;

public class Application {
    
    static{
        DOMConfigurator.configure("log4j.xml");
    }

	public static void main(String[] args) {
	    
		setIpAddress("localhost");
		setPort(8080);
		staticFileLocation("/public/app");
		
		HibernateUtil.getSessionFactory();
		
		new NutritionController(new NutritionService());
		new SchoolController(new SchoolService());
		new SponsorController(new SponsorService());
	}

}

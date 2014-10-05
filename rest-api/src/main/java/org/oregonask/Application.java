package org.oregonask;

import static spark.SparkBase.setIpAddress;
import static spark.SparkBase.setPort;
import static spark.SparkBase.staticFileLocation;

import org.apache.log4j.xml.DOMConfigurator;
import org.oregonask.controllers.ActiveDateController;
import org.oregonask.controllers.CongressionalDistrictController;
import org.oregonask.controllers.CountyController;
import org.oregonask.controllers.EducationServiceDistrictController;
import org.oregonask.controllers.EthnicityController;
import org.oregonask.controllers.EthnicityCountController;
import org.oregonask.controllers.GradeCountController;
import org.oregonask.controllers.KidsServedController;
import org.oregonask.controllers.LocationController;
import org.oregonask.controllers.ProgramController;
import org.oregonask.controllers.ProgramTypeController;
import org.oregonask.controllers.ProviderController;
import org.oregonask.controllers.SchoolController;
import org.oregonask.controllers.SchoolDistrictController;
import org.oregonask.controllers.StudentCountController;
import org.oregonask.services.ActiveDateService;
import org.oregonask.services.CongressionalDistrictService;
import org.oregonask.services.CountyService;
import org.oregonask.services.EducationServiceDistrictService;
import org.oregonask.services.EthnicityCountService;
import org.oregonask.services.EthnicityService;
import org.oregonask.services.GradeCountService;
import org.oregonask.services.KidsServedService;
import org.oregonask.services.LocationService;
import org.oregonask.services.ProgramService;
import org.oregonask.services.ProgramTypeService;
import org.oregonask.services.ProviderService;
import org.oregonask.services.SchoolDistrictService;
import org.oregonask.services.SchoolService;
import org.oregonask.services.StudentCountService;
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
		
		new ActiveDateController(new ActiveDateService());
		new CongressionalDistrictController(new CongressionalDistrictService());
		new CountyController(new CountyService());
		new EducationServiceDistrictController(new EducationServiceDistrictService());
		new EthnicityController(new EthnicityService());
		new EthnicityCountController(new EthnicityCountService());
		new GradeCountController(new GradeCountService());
		new KidsServedController(new KidsServedService());
		new LocationController(new LocationService());
		new ProgramController(new ProgramService());
		new ProgramTypeController(new ProgramTypeService());
		new ProviderController(new ProviderService());
		new SchoolController(new SchoolService());
		new SchoolDistrictController(new SchoolDistrictService());
		new StudentCountController(new StudentCountService());
	}

}

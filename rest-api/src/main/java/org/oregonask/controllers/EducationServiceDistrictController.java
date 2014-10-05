package org.oregonask.controllers;

import org.oregonask.entities.EducationServiceDistrict;
import org.oregonask.services.AbstractService;

public class EducationServiceDistrictController extends AbstractController {
	
	public EducationServiceDistrictController(AbstractService abstractService) {
		super(abstractService, EducationServiceDistrict.class, "educationservicedistricts");
	}

}

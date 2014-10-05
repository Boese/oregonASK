package org.oregonask.controllers;

import org.oregonask.entities.SchoolDistrict;
import org.oregonask.services.AbstractService;

public class SchoolDistrictController extends AbstractController {
	
	public SchoolDistrictController(AbstractService abstractService) {
		super(abstractService, SchoolDistrict.class, "schooldistricts");
	}

}

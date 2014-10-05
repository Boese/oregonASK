package org.oregonask.controllers;

import org.oregonask.entities.CongressionalDistrict;
import org.oregonask.services.AbstractService;

public class CongressionalDistrictController extends AbstractController {
	
	public CongressionalDistrictController(AbstractService abstractService) {
		super(abstractService, CongressionalDistrict.class, "congressionaldistricts");
	}

}

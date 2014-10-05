package org.oregonask.controllers;

import org.oregonask.entities.Ethnicity;
import org.oregonask.services.AbstractService;

public class EthnicityController extends AbstractController {
	
	public EthnicityController(AbstractService abstractService) {
		super(abstractService, Ethnicity.class, "ethnicities");
	}

}

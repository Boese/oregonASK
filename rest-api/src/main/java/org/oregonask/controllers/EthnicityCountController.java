package org.oregonask.controllers;

import org.oregonask.entities.EthnicityCount;
import org.oregonask.services.AbstractService;

public class EthnicityCountController extends AbstractController {
	
	public EthnicityCountController(AbstractService abstractService) {
		super(abstractService, EthnicityCount.class, "ethnicitycount");
	}

}

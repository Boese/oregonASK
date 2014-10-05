package org.oregonask.controllers;

import org.oregonask.entities.County;
import org.oregonask.services.AbstractService;

public class CountyController extends AbstractController {

	public CountyController(AbstractService abstractService) {
		super(abstractService, County.class, "counties");
	}
}

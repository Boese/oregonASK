package org.oregonask.controllers;

import org.oregonask.entities.ActiveDate;
import org.oregonask.services.AbstractService;

public class ActiveDateController extends AbstractController {
	
	public ActiveDateController(AbstractService abstractService) {
		super(abstractService, ActiveDate.class, "activedates");
	}

}

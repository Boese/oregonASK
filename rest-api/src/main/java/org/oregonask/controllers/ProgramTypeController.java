package org.oregonask.controllers;

import org.oregonask.entities.ProgramType;
import org.oregonask.services.AbstractService;

public class ProgramTypeController extends AbstractController {
	
	public ProgramTypeController(AbstractService abstractService) {
		super(abstractService, ProgramType.class, "programtypes");
	}

}

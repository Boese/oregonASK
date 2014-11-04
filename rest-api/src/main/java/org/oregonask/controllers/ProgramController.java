package org.oregonask.controllers;

import org.oregonask.entities.Program;
import org.oregonask.services.AbstractService;

public class ProgramController extends AbstractController {
	public ProgramController(AbstractService abstractService) {
		super(abstractService,Program.class,"programs");
	}
}

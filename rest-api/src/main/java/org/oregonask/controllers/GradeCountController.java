package org.oregonask.controllers;

import org.oregonask.entities.GradeCount;
import org.oregonask.services.AbstractService;

public class GradeCountController extends AbstractController {
	
	public GradeCountController(AbstractService abstractService) {
		super(abstractService, GradeCount.class, "gradecount");
	}

}

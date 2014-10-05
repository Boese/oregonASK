package org.oregonask.controllers;

import org.oregonask.entities.StudentCount;
import org.oregonask.services.AbstractService;

public class StudentCountController extends AbstractController {
	
	public StudentCountController(AbstractService abstractService) {
		super(abstractService, StudentCount.class, "studentcount");
	}

}

package org.oregonask.controllers;

import org.oregonask.entities.School;
import org.oregonask.services.AbstractService;

public class SchoolController extends AbstractController {
	public SchoolController(AbstractService abstractService) {
		super(abstractService, School.class, "schools");
	}
}

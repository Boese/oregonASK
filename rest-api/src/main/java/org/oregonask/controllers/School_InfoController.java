package org.oregonask.controllers;

import org.oregonask.entities.School_Info;
import org.oregonask.services.AbstractService;

public class School_InfoController extends AbstractController {

	public School_InfoController(AbstractService abstractService) {
		super(abstractService, School_Info.class, "school_infos");
	}

}

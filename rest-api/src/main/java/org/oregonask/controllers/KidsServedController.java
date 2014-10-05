package org.oregonask.controllers;

import org.oregonask.entities.KidsServed;
import org.oregonask.services.AbstractService;

public class KidsServedController extends AbstractController {
	
	public KidsServedController(AbstractService abstractService) {
		super(abstractService, KidsServed.class, "kidsserved");
	}

}

package org.oregonask.controllers;

import org.oregonask.entities.Summerfood;
import org.oregonask.services.AbstractService;

public class SummerfoodController extends AbstractController {
	public SummerfoodController(AbstractService abstractService) {
		super(abstractService,Summerfood.class,"summerfoods");
	}
}

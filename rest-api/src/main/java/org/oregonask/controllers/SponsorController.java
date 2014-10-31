package org.oregonask.controllers;

import org.oregonask.entities.Sponsor;
import org.oregonask.services.AbstractService;

public class SponsorController extends AbstractController {

	public SponsorController(AbstractService abstractService) {
		super(abstractService, Sponsor.class, "sponsor");
	}

}

package org.oregonask.controllers;

import org.oregonask.entities.Location;
import org.oregonask.services.AbstractService;

public class LocationController extends AbstractController {
	
	public LocationController(AbstractService abstractService) {
		super(abstractService, Location.class, "locations");
	}
}

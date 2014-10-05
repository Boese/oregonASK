package org.oregonask.controllers;

import org.oregonask.entities.Provider;
import org.oregonask.services.AbstractService;

public class ProviderController extends AbstractController {
	
	public ProviderController(AbstractService abstractService) {
		super(abstractService, Provider.class, "providers");
	}
}

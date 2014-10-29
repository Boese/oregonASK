package org.oregonask.controllers;

import org.oregonask.entities.Nutrition;
import org.oregonask.services.AbstractService;

public class NutritionController extends AbstractController {
	public NutritionController(AbstractService abstractService) {
		super(abstractService, Nutrition.class, "nutritions");
	}
}

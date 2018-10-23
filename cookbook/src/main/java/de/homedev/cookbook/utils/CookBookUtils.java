package de.homedev.cookbook.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import de.homedev.cookbook.dto.CookBookDto;
import de.homedev.cookbook.dto.RecipeDto;

/**
 * 
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *        Cook Book Utils
 */
public final class CookBookUtils {

	private CookBookUtils() {
	}

	/**
	 * calculates all possible recipes for available ingredients
	 * 
	 * @param availableIngredientsList
	 * @param database
	 *            - Recipes database (Database with recipes in cook books )
	 * @return possible recipes lists with cook book reference
	 */
	public static Map<CookBookDto, List<RecipeDto>> calculatePossibleRecipes(final List<String> availableIngredientsList, final List<CookBookDto> database) {
		Collections.sort(availableIngredientsList, String.CASE_INSENSITIVE_ORDER);
		Map<CookBookDto, List<RecipeDto>> result = new HashMap<>();
		for (CookBookDto cookBookDto : database) {
			for (RecipeDto recipe : cookBookDto.getRecipesList()) {
				if (hasAllIngredients(recipe, availableIngredientsList)) {
					List<RecipeDto> list = result.get(cookBookDto);
					if (list != null) {
						list.add(recipe);
					} else {
						list = new ArrayList<>(100);
						list.add(recipe);
						result.put(cookBookDto, list);
					}
				}
			}
		}
		return result;
	}

	/**
	 * checks if recipe has all available ingredients
	 * 
	 * @param recipe
	 *            - Recipe
	 * @param availableIngredientsList
	 *            - available ingredients
	 * @return true or false
	 */
	private static boolean hasAllIngredients(final RecipeDto recipe, final List<String> availableIngredientsList) {
		for (String ingredient : recipe.getIngredients()) {
			if (Collections.binarySearch(availableIngredientsList, ingredient) < 0) {
				return false;
			}
		}
		return true;
	}

}

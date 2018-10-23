package de.homedev.cookbook.dto;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *        Recipe Dto Object
 */
public class RecipeDto {
	private final Long id;
	private final List<String> ingredients;

	public RecipeDto(Long id, List<String> ingredients) {
		super();
		this.id = id;
		this.ingredients = ingredients;
	}

	public RecipeDto(Long id, String... ingredients) {
		this(id, Arrays.asList(ingredients));
	}

	public Long getId() {
		return id;
	}

	public List<String> getIngredients() {
		return ingredients;
	}

	@Override
	public String toString() {
		return "RecipeDto [id=" + id + ", ingredients=" + ingredients + "]";
	}

}

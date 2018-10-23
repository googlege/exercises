package de.homedev.cookbook.dto;

import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *        CookBook Dto Object
 */
public class CookBookDto {
	private final Long id;
	private final List<RecipeDto> recipesList;

	public CookBookDto(Long id, List<RecipeDto> recipesList) {
		super();
		this.id = id;
		this.recipesList = recipesList;
	}

	public CookBookDto(Long id, RecipeDto... recipes) {
		this(id, Arrays.asList(recipes));
	}

	public Long getId() {
		return id;
	}

	public List<RecipeDto> getRecipesList() {
		return recipesList;
	}

	/**
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	/**
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CookBookDto other = (CookBookDto) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

package de.homedev.cookbook.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import de.homedev.cookbook.dto.CookBookDto;
import de.homedev.cookbook.dto.RecipeDto;
import de.homedev.cookbook.utils.CookBookUtils;

/**
 * 
 * @author Mikhalev, Viatcheslav
 * @email slava.mikhalev@gmail.com
 * 
 *
 */
public class CookBookUtilsTest {
	private static Logger log = Logger.getLogger(CookBookUtilsTest.class);

	private List<String> availableIngredientsList = null;
	private List<CookBookDto> database = null;

	@Before
	public void init() {
		this.availableIngredientsList = Arrays.asList(new String[] { "0", "2", "4", "6", "8" });
		List<CookBookDto> database = new ArrayList<>(10);
		database.add(new CookBookDto(0l, new RecipeDto(0l, "0", "6", "8"), new RecipeDto(1l, "0", "1", "3")));
		database.add(new CookBookDto(1l, new RecipeDto(2l, "0", "1"), new RecipeDto(3l, "0", "2", "3"), new RecipeDto(4l, "0", "5", "3", "9")));
		database.add(new CookBookDto(3l, new RecipeDto(5l, "0", "3")));
		database.add(new CookBookDto(4l, new RecipeDto(6l, "0", "8")));
		this.database = database;
	}

	@Test
	public void databaseInit() {
		Assert.assertNotNull(this.availableIngredientsList);
		Assert.assertNotNull(this.database);
	}

	@Test
	public void calculatePosibleRecipesTest() {
		Map<CookBookDto, List<RecipeDto>> result = CookBookUtils.calculatePossibleRecipes(availableIngredientsList, database);
		for (Entry<CookBookDto, List<RecipeDto>> entry : result.entrySet()) {
			CookBookDto key = entry.getKey();
			List<RecipeDto> value = entry.getValue();
			log.debug("CookBookDto id=" + key.getId());
			for (RecipeDto r : value) {
				log.debug(r.toString());
			}
		}
		Assert.assertNotNull(result);
		List<RecipeDto> l1 = result.get(new CookBookDto(0l, new RecipeDto[] {}));
		List<RecipeDto> l2 = result.get(new CookBookDto(4l, new RecipeDto[] {}));
		Assert.assertNotNull(l1);
		Assert.assertNotNull(l2);
		Assert.assertEquals(1, l1.size());
		Assert.assertEquals(new Long(0), l1.get(0).getId());
		Assert.assertEquals(1, l2.size());
		Assert.assertEquals(new Long(6), l2.get(0).getId());
	}
}

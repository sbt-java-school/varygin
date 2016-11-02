package lesson24.service;

import lesson24.db.DaoFactory;
import lesson24.db.Model;
import lesson24.db.components.IngredientsDao;
import lesson24.db.components.RecipesDao;
import lesson24.db.components.RecipesToIngredientsDao;
import lesson24.db.shema.Recipe;
import lesson24.db.shema.Unit;
import lesson24.exceptions.BusinessException;
import lesson24.services.IngredientService;
import lesson24.services.RecipeService;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestCreatingRecipe {
    @Mock
    public List<IngredientService> mockedIngredientServiceList;

    @Test
    @Ignore
    public void transactions() throws Exception {
        when(mockedIngredientServiceList.size()).thenThrow(new BusinessException("Test!"));
        try {
            RecipeService recipeService = new RecipeService("test", "descr");
            recipeService.setIngredients(mockedIngredientServiceList);
            recipeService.save();
            fail();
        } catch (BusinessException e) {
            try (DaoFactory factory = new DaoFactory()) {
                Model recipesDao = factory.create(RecipesDao.class);
                Optional<List<?>> listOptional = recipesDao.getList("name", "test");
                assertFalse(listOptional.isPresent());
            }
        }
    }

    @Test(expected = BusinessException.class)
    public void wrongName() {
        RecipeService recipeService = new RecipeService("", "descr");
        recipeService.save();
    }

    @Test(expected = BusinessException.class)
    public void wrongDescription() {
        RecipeService recipeService = new RecipeService("test", "");
        recipeService.save();
    }

    @Test
    public void createAndDelete() {
        RecipeService recipeService = new RecipeService("test", "descr");
        Unit unit = new Unit(1L, "test1", "ttt");
        List<IngredientService> ingredients = Arrays.asList(
                new IngredientService("test1", unit, "10"),
                new IngredientService("test2", unit, "15"),
                new IngredientService("test3", unit, "20")
        );
        ingredients.forEach(IngredientService::save);
        recipeService.setIngredients(ingredients);
        recipeService.save();

        try (DaoFactory factory = new DaoFactory()) {
            IngredientsDao ingredientsDao = factory.create(IngredientsDao.class);
            Optional<List<?>> ingList = ingredientsDao.getList("id", ingredients.get(0).getIngredient().getId().toString());
            assertTrue(ingList.isPresent());

            Model recipesDao = factory.create(RecipesDao.class);
            Optional<List<?>> listOptional = recipesDao.getList("name", "test");
            assertTrue(listOptional.isPresent());
            Recipe recipe = (Recipe) listOptional.get().get(0);

            RecipesToIngredientsDao recipesToIngredients = factory.create(RecipesToIngredientsDao.class);
            Optional<List<?>> rtiList = recipesToIngredients.getList("recipe_id", recipe.getId().toString());
            assertTrue(rtiList.isPresent());

            //Удаление
            RecipeService recipeService1 = new RecipeService(recipe);
            recipeService1.remove();

            Optional<List<?>> listOptional1 = recipesDao.getList("name", "test");
            assertFalse(listOptional1.isPresent());

            Optional<List<?>> rtiList1 = recipesToIngredients.getList("recipe_id", recipe.getId().toString());
            assertFalse(rtiList1.isPresent());

            ingredients.forEach(IngredientService::remove);
            Optional<List<?>> ingList1 = ingredientsDao.getList("id", ingredients.get(0).getIngredient().getId().toString());
            assertFalse(ingList1.isPresent());
        }
    }
}

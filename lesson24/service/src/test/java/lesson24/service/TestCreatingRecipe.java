package lesson24.service;

import lesson24.db.DaoFactory;
import lesson24.db.Model;
import lesson24.db.components.IngredientsDao;
import lesson24.db.components.RecipesDao;
import lesson24.db.components.RecipesToIngredientsDao;
import lesson24.db.shema.Recipe;
import lesson24.db.shema.RecipesToIngredients;
import lesson24.db.shema.Unit;
import lesson24.exceptions.BusinessException;
import lesson24.services.Ingredients;
import lesson24.services.Recipes;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TestCreatingRecipe {
    @Mock
    public List<Ingredients> mockedIngredientsList;

    @Test
    @Ignore
    public void transactions() throws Exception {
        when(mockedIngredientsList.size()).thenThrow(new BusinessException("Test!"));
        try {
            Recipes recipes = new Recipes("test", "descr");
            recipes.setIngredients(mockedIngredientsList);
            recipes.save();
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
        Recipes recipes = new Recipes("", "descr");
        recipes.save();
    }

    @Test(expected = BusinessException.class)
    public void wrongDescription() {
        Recipes recipes = new Recipes("test", "");
        recipes.save();
    }

    @Test
    public void createAndDelete() {
        Recipes recipes = new Recipes("test", "descr");
        Unit unit = new Unit(1L, "test1", "ttt");
        List<Ingredients> ingredients = Arrays.asList(
                new Ingredients("test1", unit, "10"),
                new Ingredients("test2", unit, "15"),
                new Ingredients("test3", unit, "20")
        );
        ingredients.forEach(Ingredients::save);
        recipes.setIngredients(ingredients);
        recipes.save();

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
            Recipes recipes1 = new Recipes(recipe);
            recipes1.remove();

            Optional<List<?>> listOptional1 = recipesDao.getList("name", "test");
            assertFalse(listOptional1.isPresent());

            Optional<List<?>> rtiList1 = recipesToIngredients.getList("recipe_id", recipe.getId().toString());
            assertFalse(rtiList1.isPresent());

            ingredients.forEach(Ingredients::remove);
            Optional<List<?>> ingList1 = ingredientsDao.getList("id", ingredients.get(0).getIngredient().getId().toString());
            assertFalse(ingList1.isPresent());
        }
    }
}

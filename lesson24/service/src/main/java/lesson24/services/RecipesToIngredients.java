package lesson24.services;

import lesson24.db.DaoFactory;
import lesson24.db.components.RecipesToIngredientsDao;
import lesson24.db.shema.Ingredient;
import lesson24.exceptions.BusinessException;

import java.util.Objects;

public class RecipesToIngredients {
    public static void removeByIngredient(Ingredients ingredients) {
        Ingredient ingredient = ingredients.getIngredient();
        Objects.requireNonNull(ingredient);
        try (DaoFactory factory = new DaoFactory()) {
            RecipesToIngredientsDao recipesToIngredients = factory.create(RecipesToIngredientsDao.class);
            recipesToIngredients.removeByKey("ingredient_id", ingredient.getId());
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
}

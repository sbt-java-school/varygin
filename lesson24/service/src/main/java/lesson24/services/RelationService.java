package lesson24.services;

import lesson24.db.DaoFactory;
import lesson24.db.components.RecipesToIngredientsDao;
import lesson24.db.shema.Ingredient;
import lesson24.exceptions.BusinessException;

import java.util.Objects;

public class RelationService {

    /**
     * Метод осущствляет запрос на удаление всех связей
     * рецепта по идентификатору ингредиента
     *
     * @param ingredientService сервис ингредиента
     */
    public static void removeByIngredient(IngredientService ingredientService) {
        Ingredient ingredient = ingredientService.getIngredient();

        Objects.requireNonNull(ingredient);
        try (DaoFactory factory = new DaoFactory()) {
            RecipesToIngredientsDao recipesToIngredients = factory.create(RecipesToIngredientsDao.class);
            recipesToIngredients.removeByKey("ingredient_id", ingredient.getId());
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
}

package lesson24.services;

import lesson24.db.DaoFactory;
import lesson24.db.components.RecipesToIngredientsDao;
import lesson24.db.shema.Ingredient;
import lesson24.db.shema.Unit;
import lesson24.exceptions.BusinessException;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.*;

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

    public static List<IngredientService> getIngredientsByRecipeId(Long recipe_id) {
        try (DaoFactory factory = new DaoFactory()) {
            RecipesToIngredientsDao recipesToIngredients = factory.create(RecipesToIngredientsDao.class);
            List<Map<String, Object>> resultList = recipesToIngredients.getByRecipeId(recipe_id);
            return resultList.stream()
                    .map(resultMap -> {
                        Ingredient ingredient = new Ingredient(
                                ((Number) resultMap.get("ingredient_id")).longValue(),
                                (String) resultMap.get("ingredient_name")
                        );
                        Unit unit = new Unit(
                                ((Number) resultMap.get("unit_id")).longValue(),
                                (String) resultMap.get("unit_name"),
                                (String) resultMap.get("short_name")
                        );
                        return new IngredientService(
                                ingredient,
                                unit,
                                resultMap.get("amount").toString()
                        );
                    }).collect(toList());
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }
}

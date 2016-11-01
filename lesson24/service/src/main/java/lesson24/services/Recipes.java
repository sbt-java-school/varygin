package lesson24.services;

import lesson24.db.components.TransactionRequest;
import lesson24.db.shema.Recipe;
import lesson24.db.shema.RecipesToIngredients;
import lesson24.db.DaoFactory;
import lesson24.db.Model;
import lesson24.db.components.RecipesDao;
import lesson24.db.components.RecipesToIngredientsDao;
import lesson24.exceptions.BusinessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.util.stream.Collectors.toList;
import static org.apache.commons.lang.StringUtils.isEmpty;

@Repository
public class Recipes {
    private final Recipe recipe;
    private List<Ingredients> ingredients;

    public Recipes(Recipe value) {
        this.recipe = value;
    }

    public Recipes(String name, String description) {
        this.recipe = new Recipe(name, description);
    }

    /**
     * Сохраняем рецепт в базу: если рецепт новый - добавляем ингредиенты
     */
    public void save() {
        validate();
        try (DaoFactory factory = new DaoFactory()) {
            factory.create(TransactionRequest.class).action(() -> {
                Model recipesDao = factory.create(RecipesDao.class);
                RecipesToIngredientsDao recipesToIngredientsDao = factory.create(RecipesToIngredientsDao.class);
                List<RecipesToIngredients> recipesToIngredientsList;
                if (recipe.getId() != null) {
                    recipesDao.update(recipe);
                    recipesToIngredientsList = getUpdateRecipeToIngredientsList(recipesToIngredientsDao);
                } else {
                    recipe.setId(recipesDao.create(recipe));
                    recipesToIngredientsList = getRecipeToIngredientsList();
                }
                recipesToIngredientsDao.create(recipesToIngredientsList);
            });
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    public void remove() {
        if (recipe.getId() == null) {
            throw new BusinessException("Рецепт не найден");
        }
        try (DaoFactory factory = new DaoFactory()) {
            factory.create(TransactionRequest.class).action(() -> {
                Model recipesDao = factory.create(RecipesDao.class);
                Model recipesToIngredientsDao = factory.create(RecipesToIngredientsDao.class);
                if (!recipesDao.remove(recipe.getId())) {
                    throw new BusinessException("Невозможно удалить рецеп, попробуйте ещё раз");
                }
                if (ingredients != null && ingredients.size() > 0) {
                    recipesToIngredientsDao.remove(recipe.getId());
                }
            });
        } catch (Exception e) {
            throw new BusinessException(e);
        }
    }

    private List<RecipesToIngredients> getUpdateRecipeToIngredientsList(Model dao) {
        List<RecipesToIngredients> recipeToIngredientsList = getRecipeToIngredientsList();
        if (recipeToIngredientsList == null) {
            return null;
        }
        Optional<List<?>> listOptional = dao.getList("recipe_id", recipe.getId().toString());
        if (!listOptional.isPresent()) {
            return recipeToIngredientsList;
        }
        List<RecipesToIngredients> existList = listOptional.get().stream()
                .map(item -> (RecipesToIngredients) item)
                .collect(toList());

        recipeToIngredientsList.removeAll(existList);
        return recipeToIngredientsList;
    }

    private List<RecipesToIngredients> getRecipeToIngredientsList() {
        if (ingredients == null || ingredients.size() == 0) {
            return null;
        }
        return ingredients.stream()
                .map(item -> new RecipesToIngredients(
                        recipe.getId(),
                        item.getIngredient().getId(),
                        item.getAmount(),
                        item.getUnit().getId()
                )).collect(toList());
    }

    private void validate() {
        if (isEmpty(recipe.getName())) {
            throw new BusinessException("Введите название рецепта");
        }
        if (isEmpty(recipe.getDescription())) {
            throw new BusinessException("Введите описание рецепта");
        }
    }

    public List<Ingredients> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredients> ingredients) {
        this.ingredients = ingredients;
    }

    public void setFields(String name, String description) {
        recipe.setName(name);
        recipe.setDescription(description);
    }
}

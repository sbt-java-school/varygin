package lesson24.services;

import lesson24.dao.Recipe;
import lesson24.db.DaoFactory;
import lesson24.db.Model;
import lesson24.db.components.RecipesDao;
import lesson24.exceptions.BusinessException;

import java.util.List;

import static org.apache.commons.lang.StringUtils.isEmpty;

public class Recipes {
    private final Recipe recipe;
    private List<Ingredients> ingredients;

    public Recipes(Recipe value) {
        this.recipe = value;
    }

    public Recipes(String name, String description) {
        this.recipe = new Recipe(name, description);
    }

    public void save() {
        validate();
        try (DaoFactory factory = new DaoFactory()) {
            Model recipesDao = factory.create(RecipesDao.class);
            if (recipe.getId() != null) {
                recipesDao.update(recipe);
            } else {
                recipesDao.create(recipe);
            }
            Long recipeID = recipe.getId();
            //TODO: добавляем ингредиенты к рецепту
        } catch (Exception e) {
            throw new BusinessException(e);
        }
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

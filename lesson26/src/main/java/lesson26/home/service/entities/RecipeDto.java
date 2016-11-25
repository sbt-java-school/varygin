package lesson26.home.service.entities;

import lesson26.home.dao.entities.Recipe;
import lesson26.home.dao.entities.Relation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

import static java.util.stream.Collectors.toList;

/**
 * Предназначен для ограничения доступа представления
 * к объекту Recipe базы данных и организации валидации
 * полей соответствующей таблицы
 *
 * @see Recipe
 */
public class RecipeDto {
    private Long id;
    @NotNull(message = "Введите название рецепта")
    @Size(min = 1, max = 100,
            message = "Название рецепта не должно превышать 100 символов")
    private String name;
    @NotNull(message = "Введите способ приготовления")
    @Size(min = 1, max = 1000,
            message = "Описание не должено превышать более 1000 символов")
    private String description;
    private String image;
    private List<IngredientDto> ingredients;

    public RecipeDto() {
    }

    public RecipeDto(Recipe recipe, List<Relation> relations) {
        this(recipe);

        if (Objects.nonNull(relations) && !relations.isEmpty()) {
            setIngredients(relations.stream()
                    .map(IngredientDto::new).collect(toList()));
        }
    }

    public RecipeDto(Recipe recipe) {
        this(
                recipe.getId(),
                recipe.getName(),
                recipe.getDescription(),
                recipe.getImage()
        );
    }

    public RecipeDto(Long id, String name, String description, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<IngredientDto> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<IngredientDto> ingredients) {
        this.ingredients = ingredients;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

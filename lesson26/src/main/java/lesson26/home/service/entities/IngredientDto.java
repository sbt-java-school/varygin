package lesson26.home.service.entities;

import lesson26.home.dao.entities.Ingredient;
import lesson26.home.dao.entities.Relation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Предназначен для ограничения доступа представления
 * к объекту Ingredient базы данных и организации валидации
 * полей соответствующей таблицы
 *
 * @see Ingredient
 */
public class IngredientDto {
    private Long id;
    @NotNull(message = "Введите название ингредиента")
    @Size(min = 1, max = 100, message = "Название ингредиента " +
            "не должно превышать 100 символов")
    private String name;

    public IngredientDto() {
    }

    public IngredientDto(Ingredient ingredient) {
        this(ingredient.getId(), ingredient.getName());
    }

    public IngredientDto(Relation relation) {
        this(
                relation.getId(),
                relation.getIngredient()
                        .getName() +
                        " (" + relation.getUnit().getName().toLowerCase() + ") " +
                        ": " + relation.getAmount()
        );
    }

    public IngredientDto(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return name;
    }

}

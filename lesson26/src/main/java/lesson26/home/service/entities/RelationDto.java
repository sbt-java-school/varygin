package lesson26.home.service.entities;

import lesson26.home.dao.entities.Relation;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

/**
 * Предназначен для ограничения доступа представления
 * к объекту Relation базы данных и организации валидации
 * полей соответствующей таблицы
 *
 * @see Relation
 */
public class RelationDto {
    private Long id;
    @NotNull(message = "Не выбран рецепт")
    private Long recipeId;
    @NotNull(message = "Не выбран ингредиент")
    private Long ingredientId;
    @NotNull(message = "Необходимо выбрать количество ингредиента")
    @Digits(message = "Укажите корректное количество ингредиента",
            integer = 10000, fraction = 2)
    private Integer amount;
    @NotNull(message = "Не выбрана единица измерения")
    private Long unitId;

    public RelationDto() {
    }

    public RelationDto(Long id, Long recipeId, Long ingredientId,
                       Integer amount, Long unitId) {
        this.id = id;
        this.recipeId = recipeId;
        this.ingredientId = ingredientId;
        this.amount = amount;
        this.unitId = unitId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(Long recipeId) {
        this.recipeId = recipeId;
    }

    public Long getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Long ingredientId) {
        this.ingredientId = ingredientId;
    }

    public Long getUnitId() {
        return unitId;
    }

    public void setUnitId(Long unitId) {
        this.unitId = unitId;
    }
}

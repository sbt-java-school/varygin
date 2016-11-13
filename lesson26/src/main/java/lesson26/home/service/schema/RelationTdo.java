package lesson26.home.service.schema;

import lesson26.home.dao.schema.Relation;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;

public class RelationTdo {
    private Long id;
    @NotNull(message = "Не выбран рецепт")
    private Long recipeId;
    @NotNull(message = "Не выбран ингредиент")
    private Long ingredientId;
    @NotNull(message = "Необходимо выбрать количество ингредиента")
    @Digits(message = "Укажите корректное количество ингредиента",
            integer = 10000, fraction = 2)
    private Double amount;
    @NotNull(message = "Не выбрана единица измерения")
    private Long unitId;

    public RelationTdo() {
    }

    public RelationTdo(Long id) {
        this.id = id;
    }

    public RelationTdo(Long recipeId, Long ingredientId,
                       Double amount, Long unitId) {
        this(null, recipeId, ingredientId, amount, unitId);
    }

    public RelationTdo(Long id, Long recipeId,
                       Long ingredientId, Double amount, Long unitId) {
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

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
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

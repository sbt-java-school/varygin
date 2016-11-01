package lesson24.db.shema;

public class RecipesToIngredients {
    @TableField
    private Long recipe_id;
    @TableField
    private Long ingredient_id;
    @TableField
    private Integer amount;
    @TableField
    private Long unit_id;

    public RecipesToIngredients(Long recipe_id, Long ingredient_id, Integer amount, Long unit_id) {
        this.recipe_id = recipe_id;
        this.ingredient_id = ingredient_id;
        this.amount = amount;
        this.unit_id = unit_id;
    }

    public Long getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(Long recipe_id) {
        this.recipe_id = recipe_id;
    }

    public Long getIngredient_id() {
        return ingredient_id;
    }

    public void setIngredient_id(Long ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Long getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(Long unit_id) {
        this.unit_id = unit_id;
    }

    @Override
    public int hashCode() {
        int result = getRecipe_id().hashCode();
        result = 31 * result + getIngredient_id().hashCode();
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RecipesToIngredients that = (RecipesToIngredients) o;

        return getRecipe_id().equals(that.getRecipe_id()) &&
                getIngredient_id().equals(that.getIngredient_id());
    }
}

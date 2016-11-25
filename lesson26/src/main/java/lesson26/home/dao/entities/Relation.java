package lesson26.home.dao.entities;

import javax.persistence.*;

/**
 * Модель таблицы взаимодействия между
 * таблицей рецептов и ингредиентов
 */
@Entity
@Table(name = "recipes_ingredients")
public class Relation {
    private Long id;
    private Recipe recipe;
    private Ingredient ingredient;
    private Integer amount;
    private Unit unit;

    public Relation() {
    }

    public Relation(Recipe recipe, Ingredient ingredient,
                    Integer amount, Unit unit) {
        this.recipe = recipe;
        this.ingredient = ingredient;
        this.amount = amount;
        this.unit = unit;
    }

    @Id
    @GeneratedValue
    @Column(unique = true, nullable = false)
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

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "unit_id")
    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "recipe_id", nullable = false)
    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ingredient_id", nullable = false)
    public Ingredient getIngredient() {
        return ingredient;
    }

    public void setIngredient(Ingredient ingredient) {
        this.ingredient = ingredient;
    }
}

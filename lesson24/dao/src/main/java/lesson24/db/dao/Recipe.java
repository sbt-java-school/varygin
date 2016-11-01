package lesson24.db.dao;

import java.util.ArrayList;
import java.util.List;

public class Recipe {
    private Long id;
    @TableField
    private String name;
    @TableField
    private String description;

    private List<Ingredient> ingredients;

    public Recipe() {
    }

    public Recipe(String name, String description) {
        this(null, name, description);
    }

    public Recipe(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredients = new ArrayList<>();
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

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}

package lesson26.home.service.schema;

import lesson26.home.dao.schema.Ingredient;

public class IngredientTdo {
    private Long id;
    private String name;

    public IngredientTdo() {
    }

    public IngredientTdo(Ingredient ingredient) {
        this(ingredient.getId(), ingredient.getName());
    }

    public IngredientTdo(Long id, String name) {
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

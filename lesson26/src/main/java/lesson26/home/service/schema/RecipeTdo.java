package lesson26.home.service.schema;

import lesson26.home.dao.schema.Recipe;
import lesson26.home.dao.schema.Relation;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class RecipeTdo {
    private Long id;
    @NotNull(message = "Название рецепта должно быть задано")
    @Size(min = 10, max = 100,
            message = "Название рецепта должно быть задано (от 10 до 100 символов)")
    private String name;
    @NotNull(message = "Описание рецепта должно быть задано")
    @Size(min = 10, max = 255,
            message = "Описание рецепта должно быть задано (от 10 до 255 символов)")
    private String description;
    private Map<Long, String> ingredients;

    public RecipeTdo() {
    }

    public RecipeTdo(Long id) {
        this.id = id;
    }

    public RecipeTdo(Recipe recipe) {
        this(recipe.getId(), recipe.getName(), recipe.getDescription());
        List<Relation> relations = recipe.getRelations();
        if (Objects.nonNull(relations) && !relations.isEmpty()) {
            this.ingredients = relations.stream()
                    .collect(Collectors.toMap(
                            Relation::getId,
                            item -> item.getIngredient().getName()
                                    + ": " + item.getAmount()
                                    + " " + item.getUnit().getShortName()
                    ));
        }
    }

    public RecipeTdo(String name, String description) {
        this(null, name, description);
    }

    public RecipeTdo(Long id, String name, String description) {
        this(id, name, description, null);
    }

    public RecipeTdo(Long id, String name,
                     String description, Map<Long, String> ingredients) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
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

    public Map<Long, String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(Map<Long, String> ingredients) {
        this.ingredients = ingredients;
    }
}

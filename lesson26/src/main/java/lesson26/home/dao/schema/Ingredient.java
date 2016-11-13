package lesson26.home.dao.schema;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * Модель таблицы ингредиентов
 */
@Entity
@Table(name = "ingredients")
public class Ingredient {
    private Long id;
    @NotNull(message = "Название ингредиента должно быть задано")
    private String name;
    private List<Relation> relations;

    public Ingredient() {
    }

    public Ingredient(Long id) {
        this.id = id;
    }

    public Ingredient(String name) {
        this.name = name;
    }

    public Ingredient(Long id, String name) {
        this.id = id;
        this.name = name;
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

    @Column(unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @OneToMany(mappedBy = "ingredient")
    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }
}

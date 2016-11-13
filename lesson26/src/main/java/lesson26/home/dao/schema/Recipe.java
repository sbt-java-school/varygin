package lesson26.home.dao.schema;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

/**
 * Модель таблицы рецептов
 */
@Entity
@Table(name = "recipes")
public class Recipe {
    private Long id;
    private String name;
    private String description;
    private List<Relation> relations;

    public Recipe() {
        relations = new ArrayList<>();
    }

    public Recipe(Long id) {
        this.id = id;
    }

    public Recipe(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public Recipe(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    @Column(nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    @Column(length = 1000)
    public void setDescription(String description) {
        this.description = description;
    }

    public void addRelation(Relation relation) {
        getRelations().add(relation);
    }

    @OneToMany(mappedBy = "recipe", fetch = FetchType.LAZY)
    public List<Relation> getRelations() {
        return relations;
    }

    public void setRelations(List<Relation> relations) {
        this.relations = relations;
    }
}

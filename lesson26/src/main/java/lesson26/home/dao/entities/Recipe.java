package lesson26.home.dao.entities;

import javax.persistence.*;
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
    private String image;
    private List<Relation> relations;

    public Recipe() {
    }

    public Recipe(Long id) {
        this.id = id;
    }

    public Recipe(Long id, String name,
                  String description, String image) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.image = image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

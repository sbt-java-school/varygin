package lesson24.db.shema;

import static org.apache.commons.lang.StringUtils.isNotBlank;

public class Ingredient {
    private Long id;
    @TableField
    private String name;

    public Ingredient() {
    }

    public Ingredient(String name) {
        this(null, name);
    }

    public Ingredient(Long id, String name) {
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

    public boolean isValid() {
        return isNotBlank(name);
    }

    @Override
    public String toString() {
        return name;
    }
}

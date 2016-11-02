package lesson24.db.shema;

import org.apache.commons.lang.StringUtils;

import static org.apache.commons.lang.StringUtils.*;

public class Recipe {
    private Long id;
    @TableField
    private String name;
    @TableField
    private String description;

    public Recipe() {
    }

    public Recipe(String name, String description) {
        this(null, name, description);
    }

    public Recipe(Long id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
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

    public boolean isValid() {
        return isNotBlank(name) && isNotBlank(description);
    }
}

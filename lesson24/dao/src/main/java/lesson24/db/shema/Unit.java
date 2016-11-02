package lesson24.db.shema;

import static org.apache.commons.lang.StringUtils.isNotBlank;

public class Unit {
    private Long id;
    @TableField
    private String name;
    @TableField
    private String short_name;

    public Unit(String name, String short_name) {
        this(null, name, short_name);
    }

    public Unit(Long id, String name, String short_name) {
        this.id = id;
        this.name = name;
        this.short_name = short_name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getShort_name() {
        return short_name;
    }

    public void setShort_name(String short_name) {
        this.short_name = short_name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isValid() {
        return isNotBlank(name) && id != null;
    }

    @Override
    public String toString() {
        return name;
    }
}

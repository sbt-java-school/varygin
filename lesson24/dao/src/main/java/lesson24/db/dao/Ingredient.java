package lesson24.db.dao;

import org.apache.commons.lang.text.StrBuilder;

public class Ingredient {
    private Long id;
    @TableField
    private String name;

    private Unit unit;
    private Integer amount;

    public Ingredient() {
    }

    public Ingredient(String name) {
        this(null, name);
    }

    public Ingredient(Long id, String name) {
        this(id, name, null, null);
    }

    public Ingredient(Long id, String name, Integer amount, Unit unit) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
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

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        StrBuilder tmp = new StrBuilder(name);
        if (amount != null && unit != null) {
            tmp.append(": ")
                    .append(amount.toString())
                    .append(" ")
                    .append(unit.getShort_name());
        }
        return tmp.toString();
    }
}

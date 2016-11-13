package lesson26.home.service.schema;

import lesson26.home.dao.schema.Unit;

public class UnitTdo {
    private Long id;
    private String name;

    public UnitTdo() {
    }

    public UnitTdo(Unit unit) {
        this(unit.getId(), unit.getName());
    }

    public UnitTdo(String name) {
        this(null, name);
    }

    public UnitTdo(Long id, String name) {
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

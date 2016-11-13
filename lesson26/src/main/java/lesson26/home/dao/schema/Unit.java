package lesson26.home.dao.schema;

import javax.persistence.*;

import static org.apache.commons.lang.StringUtils.isNotBlank;

/**
 * Модель таблицы единиц измерения
 */
@Entity
@Table(name = "UNITS")
public class Unit {
    private Long id;
    private String name;
    private String shortName;

    public Unit() {
    }

    public Unit(Long id) {
        this.id = id;
    }

    public Unit(String name, String shortName) {
        this.name = name;
        this.shortName = shortName;
    }

    public Unit(Long id, String name, String shortName) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
    }

    @Id
    @GeneratedValue
    @Column(nullable = false, unique = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "name", unique = true, nullable = false)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "short_name", nullable = false)
    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }

}

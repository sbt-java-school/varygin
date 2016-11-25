package lesson26.home.service.entities;

import lesson26.home.dao.entities.Unit;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Объект для вывода единиц измерения в представлении
 * Предназначен для ограничения доступа представления
 * к объектам базы данных
 *
 * @see Unit
 */
public class UnitDto {
    private Long id;
    @NotNull(message = "Введите название единицы измерения")
    @Size(min = 1, max = 20, message = "Название единицы измерения " +
            "не должно превышать 20 символов")
    private String name;
    @NotNull(message = "Укажите сокращённое название")
    @Size(min = 1, max = 10, message = "Сокращённое название единицы измерения " +
            "не должно превышать 10 символов")
    private String shortName;

    public UnitDto() {
    }

    public UnitDto(Unit unit) {
        this(unit.getId(), unit.getName(), unit.getShortName());
    }

    public UnitDto(Long id, String name, String shortName) {
        this.id = id;
        this.name = name;
        this.shortName = shortName;
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

    public String getShortName() {
        return shortName;
    }

    public void setShortName(String shortName) {
        this.shortName = shortName;
    }
}

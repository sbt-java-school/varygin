package lesson24.db.components;

/**
 * Перечисление названий таблиц базы данных
 */
enum Tables {
    RECIPES("recipes"),
    INGREDIENTS("ingredients"),
    RELATIONS("recipes_to_ingredients"),
    UNITS("units");

    private final String name;

    Tables(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}

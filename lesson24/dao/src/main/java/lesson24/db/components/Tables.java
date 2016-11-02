package lesson24.db.components;

/**
 *
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

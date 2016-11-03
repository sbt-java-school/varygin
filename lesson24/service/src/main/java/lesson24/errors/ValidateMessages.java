package lesson24.errors;

public enum ValidateMessages {
    RECIPE_NOT_VALID("Введите название и описание рецепта"),
    RECIPE_NOT_FOUND("Рецепт не найден"),
    RECIPE_REMOVE_ERROR("Ошибка при удалении рецепта из базы данных. Повторите попытку позже."),

    INGREDIENT_EXIST("Ингредиент с таким названием уже существует"),
    INGREDIENT_NOT_SELECTED("Не выберан ингредиент"),
    INGREDIENT_REMOVE_ERROR("Ошибка при удалении ингредиента из базы данных. Повторите попытку позже."),

    NAME_LENGTH_ERROR("Превышено максимальное количество символов в названии (50)"),
    DESCRIPTION_LENGTH_ERROR("Превышено максимальное количество символов в описании (200)"),

    UNIT_NOT_SELECTED("Не выберана единица изерения"),

    AMOUNT_FORMAT("Введите корректное количество ингредиента");

    private final String message;

    ValidateMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}

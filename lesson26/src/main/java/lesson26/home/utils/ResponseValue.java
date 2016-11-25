package lesson26.home.utils;

import java.io.Serializable;

/**
 * Класс для формирования ответа сервера на ajax запрос
 */
public class ResponseValue implements Serializable {
    /** Результат выполнения запроса: 1 - успешно, 0 - ошибка */
    private final String success;

    /** Объект с результатом выполнения запроса */
    private final Object value;

    /** Текст ошибок при выполнении запроса */
    private final String errors;

    ResponseValue(String success, Object value, String errors) {
        this.success = success;
        this.value = value;
        this.errors = errors;
    }

    public String getSuccess() {
        return success;
    }

    public Object getValue() {
        return value;
    }

    public String getErrors() {
        return errors;
    }
}

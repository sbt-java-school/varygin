package lesson24.exceptions;

import lesson24.errors.ValidateMessages;

/**
 * Класс для обработки ошибок
 */
public class BusinessException extends RuntimeException {
    private static final String DEFAULT_MESSAGE = "Проблемы с подключением к базе данных, повторите попытку";

    public BusinessException() {
    }

    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessException(Throwable cause) {
        this(DEFAULT_MESSAGE, cause);
    }

    public BusinessException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public BusinessException(ValidateMessages validateMessages) {
        this(validateMessages.getMessage());
    }
}

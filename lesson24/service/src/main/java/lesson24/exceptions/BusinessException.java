package lesson24.exceptions;

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
}

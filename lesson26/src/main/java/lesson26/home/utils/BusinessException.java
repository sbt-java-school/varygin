package lesson26.home.utils;

import java.util.Collections;
import java.util.List;

/**
 * Класс для обработки ошибок
 */
public class BusinessException extends RuntimeException {
    private List<String> errors;

    public BusinessException(String message) {
        this(Collections.singletonList(message));
    }

    public BusinessException(Throwable cause) {
        this(cause.getMessage(), cause);
    }

    public BusinessException(List<String> errors) {
        this.errors = errors;
    }

    public BusinessException(String message, Throwable cause) {
        super(cause);
        this.errors = Collections.singletonList(message);
    }

    public List<String> getErrors() {
        return errors;
    }
}

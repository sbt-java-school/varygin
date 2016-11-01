package lesson24.view.views.exceptions;

public class BusinessExceptions extends RuntimeException {
    public BusinessExceptions() {
    }

    public BusinessExceptions(String message) {
        super(message);
    }

    public BusinessExceptions(String message, Throwable cause) {
        super(message, cause);
    }

    public BusinessExceptions(Throwable cause) {
        super(cause);
    }

    public BusinessExceptions(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

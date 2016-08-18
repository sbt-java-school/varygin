package home.lesson5;

import java.io.IOException;

/**
 * Created by LL on 18.08.2016.
 */
public class BusinessExceptions extends IOException {

    private static final String DEFAULT_MESSAGE = "Wrong url address. Try again: ";

    public BusinessExceptions() {
        this(DEFAULT_MESSAGE);
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
}

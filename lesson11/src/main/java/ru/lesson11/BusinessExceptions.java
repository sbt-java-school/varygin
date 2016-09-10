package ru.lesson11;

import java.io.IOException;

public class BusinessExceptions extends IOException {
    private static final String DEFAULT_MESSAGE = "Wrong url address";

    public BusinessExceptions() {
        this(DEFAULT_MESSAGE);
    }

    public BusinessExceptions(String message) {
        super(message);
    }
}

package com.github.prerna.exception;

public class UserNotFoundException extends Exception {
    private static final long SERIALVERSIONID = 1L;

    public UserNotFoundException(String message) {
        super(message);
    }
}

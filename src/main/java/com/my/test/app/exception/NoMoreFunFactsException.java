package com.my.test.app.exception;

public class NoMoreFunFactsException extends RuntimeException {
    public NoMoreFunFactsException() {
        super();
    }

    public NoMoreFunFactsException(String message) {
        super(message);
    }

    public NoMoreFunFactsException(String message, Throwable cause) {
        super(message, cause);
    }
}

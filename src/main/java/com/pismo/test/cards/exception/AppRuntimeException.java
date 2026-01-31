package com.pismo.test.cards.exception;


public class AppRuntimeException extends RuntimeException {
    private final String id;

    public AppRuntimeException(String id, String message) {
        super(message);
        this.id = id;
    }

    public AppRuntimeException(String accountId, String message, Throwable cause) {
        super(message, cause);
        this.id = accountId;
    }

    public String getAccountId() {
        return id;
    }
}

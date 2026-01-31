package com.pismo.test.cards.exception;


public class AppBusinessException extends Exception {

    private final String id;
    private final int status;

    public AppBusinessException(String message, String id, int status) {
        super(message);
        this.id = id;
        this.status = status;
    }

    public AppBusinessException(String message, String id, Throwable cause, int status) {
        super(message, cause);
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "BusinessException{" + "id='" + id + '\'' + ", message='" + getMessage() + '\'' + ", cause=" + getCause() + '}';
    }
}


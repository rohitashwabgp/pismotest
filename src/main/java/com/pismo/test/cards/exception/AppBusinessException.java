package com.pismo.test.cards.exception;


public class AppBusinessException extends Exception {

    private final String id;
    private final long status;

    public AppBusinessException(String message, String id, long status) {
        super(message);
        this.id = id;
        this.status = status;
    }

    public AppBusinessException(String message, String id, Throwable cause, long status) {
        super(message, cause);
        this.id = id;
        this.status = status;
    }

    public String getId() {
        return id;
    }

    public long getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return "BusinessException{" + "id='" + id + '\'' + ", message='" + getMessage() + '\'' + ", cause=" + getCause() + '}';
    }
}


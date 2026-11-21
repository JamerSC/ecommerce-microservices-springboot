package com.ecommerce.order.exception;

public class UnauthorizedAuthException extends RuntimeException {
    public UnauthorizedAuthException() {
        super();
    }

    public UnauthorizedAuthException(String message) {
        super(message);
    }

    public UnauthorizedAuthException(String message, Throwable cause) {
        super(message, cause);
    }

    public UnauthorizedAuthException(Throwable cause) {
        super(cause);
    }

    protected UnauthorizedAuthException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

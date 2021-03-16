package com.exyfi.cqrs.common.exceptions;

public class IllegalEnterAttemptException extends RuntimeException{
    public IllegalEnterAttemptException(String message) {
        super(message);
    }

    public IllegalEnterAttemptException(String message, Throwable cause) {
        super(message, cause);
    }
}

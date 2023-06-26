package com.acme.exceptions;

public class DatabaseConnectionError extends RuntimeException {
    public DatabaseConnectionError(Throwable e) {
        super(e);
    }

    public DatabaseConnectionError(String err) {
        super(err);
    }
}

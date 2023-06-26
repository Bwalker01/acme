package com.acme.exceptions;

public class InvalidProductException extends RuntimeException {
    public InvalidProductException(String errorMessage) {
        super(String.format("Invalid Product: %s",errorMessage));
    }
}

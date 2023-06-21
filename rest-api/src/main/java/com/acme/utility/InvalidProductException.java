package com.acme.utility;

public class InvalidProductException extends RuntimeException {
    public InvalidProductException(String errorMessage) {
        super(String.format("Invalid Product: %s",errorMessage));
    }
}

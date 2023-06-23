package com.acme.exceptions;

public class InvalidDiscountBundleException extends RuntimeException {
    public InvalidDiscountBundleException(String errorMessage) {
        super(String.format("Invalid Discount Bundle: %s",errorMessage));
    }
}

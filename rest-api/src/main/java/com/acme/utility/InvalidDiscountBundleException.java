package com.acme.utility;

public class InvalidDiscountBundleException extends RuntimeException {
    public InvalidDiscountBundleException(String errorMessage) {
        super(String.format("Invalid Discount Bundle: %s",errorMessage));
    }
}

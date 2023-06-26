package com.acme.dataobjects;

import static com.acme.utility.GeneralUtil.round;

import com.acme.exceptions.InvalidProductException;

public class Product {
    private final String barcode;
    private final String name;
    private final double price;

    public Product(String barcode, String name, double price) {
        validateProduct(barcode, name, price);
        this.barcode = barcode;
        this.name = name;
        this.price = round(price);
    }

    private static void validateProduct(String barcode, String name, double price) {
        if (barcode == null || barcode.length() == 0) {
            throw new InvalidProductException("product has no barcode.");
        } else if (name == null || name.length() == 0) {
            throw new InvalidProductException("product has no name.");
        } else if (price < 0.001) {
            throw new InvalidProductException("product has no price.");
        } else if (!barcode.matches("0?[0-9]{12}")) {
            throw new InvalidProductException("product has invalid barcode.");
        }
    }

    public String getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getPriceString() {
        return String.format("£%.2f", this.price);
    }
}

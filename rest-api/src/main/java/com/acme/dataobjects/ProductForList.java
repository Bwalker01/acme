package com.acme.dataobjects;

import com.acme.exceptions.InvalidProductException;

public class ProductForList {


    private final String name;
    private final double price;
    private final int quantity;




    public ProductForList(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }


    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getPriceString() {
        return String.format("£%.2f", this.price);
    }
}



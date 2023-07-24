package com.acme.dataobjects;

public class OutputProduct {
    private final String name;
    private double price;
    private int quantity;

    public OutputProduct(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }


       public double getPrice() {
        return price;
    }

        public int getQuantity() {
        return quantity;
    }


        public String getName() {
        return name;
    }
}

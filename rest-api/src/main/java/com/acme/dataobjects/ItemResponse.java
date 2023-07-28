package com.acme.dataobjects;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemResponse {
    private final ArrayList<OutputProduct> items;
    private double total;

    public ItemResponse(HashMap<Product, Integer> inputItems, HashMap<DiscountBundle, Integer> inputDiscount){
        
        

        this.items = new ArrayList<OutputProduct>();

        
        for (Product product : inputItems.keySet()) {
            items.add(new OutputProduct(product.getName(), product.getPrice() * inputItems.get(product), inputItems.get(product)));
        }

        for (DiscountBundle discountBundle : inputDiscount.keySet()) {
            items.add(new OutputProduct(discountBundle.getName(), discountBundle.getPrice() * inputItems.get(discountBundle), inputItems.get(discountBundle)));
        }

        this.total = 0;
        for (OutputProduct product : items) {
            total += product.getPrice();
        }
    }

       public ItemResponse(HashMap<Product, Integer> inputItems){
        this.items = new ArrayList<OutputProduct>();

        
        for (Product product : inputItems.keySet()) {
            items.add(new OutputProduct(product.getName(), product.getPrice() * inputItems.get(product), inputItems.get(product)));
        }

        this.total = 0;
        for (OutputProduct product : items) {
            total += product.getPrice();
        }
    }
}





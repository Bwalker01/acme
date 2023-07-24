package com.acme.dataobjects;

import java.util.ArrayList;
import java.util.HashMap;

public class ItemResponse {
    private final HashMap<Product, Integer> items;


    private final double total;

    public ItemResponse(HashMap<Product, Integer> listOfItems, double total){
        this.items = listOfItems;
        this.total = total;
    }



        public HashMap<Product, Integer> getItem() {
        return items;
    }


       public double getPrice() {
        return total;
    }
    
}

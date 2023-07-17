package com.acme.dataobjects;

import java.util.ArrayList;

public class ItemResponse {
    private final ArrayList<Product> items;


    private final double total;

    public ItemResponse(ArrayList<Product> listOfItems, double total){
        this.items = listOfItems;
        this.total = total;
    }



        public ArrayList<Product> getItem() {
        return items;
    }


       public double getPrice() {
        return total;
    }
    
}

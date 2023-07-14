package com.acme.dataobjects;

import java.util.ArrayList;

public class ItemResponse {
    private final ArrayList<ProductForList> items;


    private final double total;

    public ItemResponse(ArrayList<ProductForList> listOfItems, double total){
        this.items = listOfItems;
        this.total = total;
    }



        public ArrayList<ProductForList> getItem() {
        return items;
    }


       public double getPrice() {
        return total;
    }
    
}

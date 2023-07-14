package com.acme.dataobjects;

public class ItemResponse {
    private final Product item;


    private final int price;

    public ItemResponse(Product item, int price){
        this.item = item;
        this.price = price;
    }



        public Product getItem() {
        return item;
    }


       public int getPrice() {
        return price;
    }
    
}

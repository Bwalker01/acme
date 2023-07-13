package com.acme.database;

import com.acme.dataobjects.DiscountBundle;
import com.acme.dataobjects.Product;

public interface DAOInterface {
    public Product fetchItem(String barcode);
    public DiscountBundle checkForBundle(Product product, int quantity);
}

package com.acme.database;

import com.acme.dataobjects.DiscountBundle;
import com.acme.dataobjects.Product;

public interface DBInterface {
    public Product fetchItem();
    public DiscountBundle fetchBundle();
}

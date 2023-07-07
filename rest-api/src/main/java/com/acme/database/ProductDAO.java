package com.acme.database;

import com.acme.dataobjects.DiscountBundle;
import static com.acme.dataobjects.DiscountType.*;
import com.acme.dataobjects.DiscountType;
import com.acme.dataobjects.Product;

public class ProductDAO implements DAOInterface {
    private DBController db;

    public ProductDAO() {
        String URL = System.getenv("DB_URL");
        String USERNAME = System.getenv("DB_USERNAME");
        String PASSWORD = System.getenv("DB_PASSWORD");

        this.db = new DBController(URL, USERNAME, PASSWORD);
    }

    public ProductDAO(DBController dbController) {
        this.db = dbController;
    }

    public Product fetchItem(String barcode) {
        db.connect();
        String[] results = db.getItem(barcode);
        db.disconnect();
        return new Product(results[0], results[1], Double.parseDouble(results[2]));
    }

    public DiscountBundle checkForBundle(Product product, int quantity) {
        db.connect();
        String[] results = db.getDiscount(product.getBarcode(), quantity);
        db.disconnect();
        if (results == null) {
            return null;
        }
        DiscountType type;
        if (Boolean.parseBoolean(results[3])) {
            type = PERCENTAGE;
        } else {
            type = PRICE;
        }
        return new DiscountBundle(Integer.parseInt(results[0]), product, Integer.parseInt(results[2]), type, Double.parseDouble(results[4]));
    }
}

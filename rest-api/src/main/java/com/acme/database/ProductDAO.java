package com.acme.database;

import com.acme.dataobjects.Product;

public class ProductDAO {
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
}

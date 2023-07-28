package com.acme.database;

import java.util.ArrayList;
import java.util.Arrays;

import com.acme.exceptions.DatabaseConnectionError;

import java.sql.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class DBController {
    private final String url;
    private final String username;
    private final String password;
    private Connection connection;
    private final String[] invalidChars = {"(", ")", ";", "$", ","};

    public DBController(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
    }

    private static ArrayList<String> readResults(ResultSet rs) {
        try {
            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();
            ArrayList<String> results = new ArrayList<>(); 
            for (int column = 1; column  <= columnCount; ++column) {
                results.add(rs.getString(column));
            }
            return results;
        } catch (SQLException e) {
            throw new DatabaseConnectionError(e);
        }
    }

    public Boolean connect() {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
            return true;
        } catch (SQLException e) {
            throw new DatabaseConnectionError(e);
        }
    }

    public Boolean disconnect() {
        try {
            this.connection.close();
            return true;
        } catch (SQLException e) {
            throw new DatabaseConnectionError(e);
        }
    }

    public String[] getItem(String barcode) {
        if (Arrays.stream(invalidChars).anyMatch(barcode::contains)) {
            throw new DatabaseConnectionError("Barcode contains invalid characters");
        }
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                String.format("SELECT barcode, name, price FROM products WHERE barcode = '%s'", barcode) 
            );
            if (!rs.next()) {
                throw new DatabaseConnectionError("ResultSet is empty.");
            }
            return readResults(rs).toArray(new String[0]);
        } catch (SQLException e) {
            throw new DatabaseConnectionError(e);
        }
    }

    public String[] getDiscount(String barcode) {
        if (Arrays.stream(invalidChars).anyMatch(barcode::contains)) {
            throw new DatabaseConnectionError("Barcode contains invalid characters");
        }
        try {
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery(
                String.format("SELECT id, barcode, quantity, is_percentage, discount_amount FROM discounts WHERE barcode = '%s'", barcode)
            );
            if (rs.next()) {
                return readResults(rs).toArray(new String[0]);
            }
            return null;
        } catch (SQLException e) {
            throw new DatabaseConnectionError(e);
        }
    }
}

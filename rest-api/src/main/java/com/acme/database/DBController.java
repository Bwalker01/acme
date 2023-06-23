package com.acme.database;

import java.util.Arrays;
import java.sql.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.acme.utility.DatabaseConnectionError;

public class DBController {
    private final String url;
    private final String username;
    private final String password;
    private Connection connection;

    public DBController(String url, String username, String password) {
        this.url = url;
        this.username = username;
        this.password = password;
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

    public ResultSet getItem(String barcode) {
        String[] invalidChars = {"(", ")", ";", "$", ","};
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
            return rs;
        } catch (SQLException e) {
            throw new DatabaseConnectionError(e);
        }
    }
}

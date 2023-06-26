package com.acme.utility;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.acme.exceptions.DatabaseConnectionError;

import io.github.cdimascio.dotenv.Dotenv;
import io.github.cdimascio.dotenv.DotenvException;

public class GeneralUtil {
    public static double round(double x) {
        int y = (int) Math.round(x * 100);
        return (double) y / 100.0;
    }

    public static String[] readResults(ResultSet rs) {
        try {
            String[] results = { rs.getString(1), rs.getString(2), rs.getString(3) };
            return results;
        } catch (SQLException e) {
            throw new DatabaseConnectionError(e);
        }
    }

    public static String loadEnvVar(String var) {
        try {
            Dotenv env = Dotenv.load();
            return env.get(var);
        } catch (DotenvException e) {
            return System.getenv(var);
        }
    }
}

package com.acme.utility;

public class GeneralUtil {
    public static double round(double x) {
        int y = (int) Math.round(x * 100);
        return (double) y / 100.0;
    }
}

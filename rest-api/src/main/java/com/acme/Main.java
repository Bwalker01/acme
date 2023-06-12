package com.acme;

import static spark.Spark.get;

public class Main 
{
    public static void main( String[] args )
    {
        get("/items", (request, response) -> {
            return "The item is an apple.";
        });

        get("/items/:name", (request, response) -> {
            return "The item is: " + request.params(":name");
        });
    }
}

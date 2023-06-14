package com.acme;

import static spark.Spark.get;
import static spark.Spark.port;

public class Main 
{
    public static void main( String[] args )
    {
        /*Initialising Listening Port*/
        port(getHerokuAssignedPort());

        /*Setting the routes*/
        get("/products", (request, response) -> {
            return "The product is an apple.";
        });
    }

    static int getHerokuAssignedPort() {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        } 
        return 4567;  
    }
}

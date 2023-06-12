package com.acme;

import static spark.Spark.get;

import io.github.cdimascio.dotenv.Dotenv;

public class Main 
{
    public static void main( String[] args )
    {
        /*Initialising Stuff*/
        Dotenv dotenv = Dotenv.configure().load();
        getHerokuAssignedPort(dotenv);

        /*Setting the routes*/
        get("/items", (request, response) -> {
            return "The item is an apple.";
        });
    }

    static int getHerokuAssignedPort(Dotenv dotenv) {
        ProcessBuilder processBuilder = new ProcessBuilder();
        if (processBuilder.environment().get("PORT") != null) {
            return Integer.parseInt(processBuilder.environment().get("PORT"));
        }
        return Integer.parseInt(dotenv.get("DEFAULT_PORT", "4567"));  
    }
}

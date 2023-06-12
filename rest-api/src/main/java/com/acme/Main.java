package com.acme;

import static spark.Spark.get;
import static spark.Spark.port;

import java.io.File;

import io.github.cdimascio.dotenv.Dotenv;

public class Main 
{
    public static void main( String[] args )
    {
        /*Initialising Listening Port*/
        Boolean isLocal = new File(".env").exists();
        Dotenv dotenv = isLocal ? Dotenv.configure().load() : null;
        port(isLocal ? Integer.parseInt(dotenv.get("DEFAULT_PORT", "4567")) : getHerokuAssignedPort());

        /*Setting the routes*/
        get("/items", (request, response) -> {
            return "The item is an apple.";
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

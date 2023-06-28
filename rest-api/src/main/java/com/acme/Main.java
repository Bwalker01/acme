package com.acme;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import com.acme.dataobjects.CreditCard;
import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.text.html.HTMLDocument.Iterator;
import javax.xml.namespace.QName;

import com.google.gson.JsonArray;


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
        get("/items", (request, response) -> {
            return "The item is an apple.";
        });

        get("/items/:name", (request, response) -> {
            return "The item is: " + request.params(":name");
        });

        post("/creditCard", (request, response) -> {
            // Gson g = new Gson();  
            // g.toJson(request.body()); 

            String defaultRequestsBody = request.body();
          
            String requestBody = defaultRequestsBody.replaceAll("\"" , "").replaceAll("\\{" , "").replaceAll("\\}" , "");
            String[] allParams = requestBody.split(",");

            HashMap<String,String> itemList = new HashMap<String,String>();
        
            for (String pair : allParams) {
                String[] keyValue = pair.split(":");
                itemList.put(keyValue[0],keyValue[1]);
            }
               
            // CreditCard usersCard = new CreditCard();
            

            // return request.body().get("postcode").toString();
            // return request.body();

            response.type("application/json");
            System.out.println(itemList.get("amount"));
            return new Gson().toJsonTree(itemList);
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

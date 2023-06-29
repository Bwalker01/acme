package com.acme;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import com.acme.dataobjects.CreditCard;
import com.acme.dataobjects.Product;
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

import javax.servlet.http.Part;
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

        // post("/barcode", (request, response) -> {
        //     String barcode = "123456789055";
        //     Product test = fetchItem();
        //     response.body(barcode);
            
            
            
        //     return response.body();

        // });

        post("/creditCard", (request, response) -> {
            response.type("application/json");

            CreditCard usersCard = new Gson().fromJson(request.body(), CreditCard.class);

            System.out.println(usersCard.getAmount());
            System.out.println(usersCard.getCreditCardNumber());
            System.out.println(usersCard.getExpiryDate());
            System.out.println(usersCard.getCvc());
            System.out.println(usersCard.getAddress());
            System.out.println(usersCard.getPostcode());
            System.out.println(usersCard.getAccountHolderName());
            return new Gson().toJsonTree(usersCard);
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

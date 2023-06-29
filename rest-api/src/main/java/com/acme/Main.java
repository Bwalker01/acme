package com.acme;

import static spark.Spark.get;
import static spark.Spark.port;
import static spark.Spark.post;

import com.acme.dataobjects.CreditCard;
import com.acme.dataobjects.Product;
import com.google.gson.Gson;

// import java.io.BufferedReader;
// import java.io.DataOutputStream;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.io.UnsupportedEncodingException;
// import java.net.HttpURLConnection;
// import java.net.URL;
// import java.net.URLEncoder;
// import java.net.http.HttpClient;
// import java.util.HashMap;
// import java.util.List;
// import java.util.Map;

// import javax.servlet.http.Part;
// import javax.swing.text.html.HTMLDocument.Iterator;
// import javax.xml.namespace.QName;

// import com.google.gson.JsonArray;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;




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

            String postUrl  = "https://acme2pos.azurewebsites.net/payments";// put in your url
            Gson gson = new Gson();

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(postUrl);
            StringEntity postingString = new StringEntity(gson.toJson(usersCard));//gson.tojson() converts the creditCard object back to a to json
            post.setEntity(postingString);
            post.setHeader("Content-type", "application/json");

            String authKey = "";//auth key removed for safety need to find away to make more secure
            post.setHeader("x-authkey", authKey);

            HttpResponse  responses = httpClient.execute(post);
            HttpEntity entity = responses.getEntity();
            String responseString = EntityUtils.toString(entity, "UTF-8");
            System.out.println(responseString);


            return responseString;

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

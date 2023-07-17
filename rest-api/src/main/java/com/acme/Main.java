package com.acme;
import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.delete;

import java.util.ArrayList;
import com.acme.dataobjects.Barcodes;
import com.acme.dataobjects.CreditCard;
import com.acme.dataobjects.ItemResponse;
import com.acme.dataobjects.Product;
import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.acme.database.ProductDAO;




public class Main
{
    public static double totalPrice;
    public static int quantity;
    public static void main( String[] args )
    {
        /*Initialising Listening Port*/
        port(getHerokuAssignedPort());
        

        /*setting global variables/objects*/
        ArrayList<Product> listOfItems = new ArrayList<Product>(); 
        // Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        Gson gson = new Gson();

        /*Setting the routes*/
        post("/barcode", (request, response) -> {
            ProductDAO productDatabase = new ProductDAO();
            Barcodes barcode = gson.fromJson(request.body(), Barcodes.class);
            if(!barcode.getBarcode().equals("END")){
                for (Product product : listOfItems) {
                    if (product.getBarcode().equals(barcode.getBarcode())) {
                        product.increaseItem();
                        ItemResponse finalResponse = new ItemResponse(listOfItems, calculateListPrice(listOfItems));
                        return gson.toJsonTree(finalResponse);
                    }
                }
                listOfItems.add(productDatabase.fetchItem(barcode.getBarcode()));
                ItemResponse finalResponse = new ItemResponse(listOfItems, calculateListPrice(listOfItems));
                return gson.toJsonTree(finalResponse);
            }
            ItemResponse finalResponse = new ItemResponse(listOfItems, calculateListPrice(listOfItems));
            // listOfItems.clear();
            return gson.toJsonTree(finalResponse);
        });

        delete("/remove", (request, response) -> {
            if(listOfItems.size()>=1){
                Product product = listOfItems.get(listOfItems.size()-1);
                if(product.getQuantity() > 1){
                    product.decreaseItem();
                } else {
                    listOfItems.remove(listOfItems.size()-1);
                }
            }
            ItemResponse finalResponse = new ItemResponse(listOfItems, calculateListPrice(listOfItems));
            return gson.toJsonTree(finalResponse);
        });

        post("/creditCard", (request, response) -> {
            response.type("application/json");

            CreditCard usersCard = gson.fromJson(request.body(), CreditCard.class);

            String postUrl = "https://acme2pos.azurewebsites.net/payments";// put in your url
            
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(postUrl);
            StringEntity postingString = new StringEntity(gson.toJson(usersCard));//gson.tojson() converts the creditCard object back to a to json
            post.setEntity(postingString);
            post.setHeader("Content-type", "application/json");

            String authKey = System.getenv("XAUTH_KEY");
            System.out.println(authKey);//auth key removed for safety need to find away to make more secure
            post.setHeader("x-authkey", authKey);

            HttpResponse responses = httpClient.execute(post);
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

    static double calculateListPrice(ArrayList<Product> items) {
        double total = 0;
        for (Product product : items) {
            total += product.getTotalPrice();
        }
        return total;
    }




}

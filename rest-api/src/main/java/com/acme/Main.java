package com.acme;

import static spark.Spark.port;
import static spark.Spark.post;
import static spark.Spark.delete;
import static spark.Spark.after;

import java.util.HashMap;

import com.acme.dataobjects.Barcodes;
import com.acme.dataobjects.CreditCard;
import com.acme.dataobjects.DiscountBundle;
import com.acme.dataobjects.ItemResponse;
import com.acme.dataobjects.OutputProduct;
import com.acme.dataobjects.Product;
import com.google.gson.Gson;

import spark.Filter;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import com.acme.database.ProductDAO;

public class Main {
    public static double totalPrice;
    public static int quantity;

    public static void main(String[] args) {
        /* Initialising Listening Port */
        port(getHerokuAssignedPort());

        Gson gson = new Gson();

        HashMap<Product, Integer> items = new HashMap<>();
        ProductDAO productDatabase = new ProductDAO();
        // HashMap<DiscountBundle, Integer> discountBundleMap = new HashMap<>();

        after((Filter) (request, response) -> {
            response.header("Access-Control-Allow-Origin", "*");
            response.header("Access-Control-Allow-Methods", "POST,DELETE");
            response.header("Access-Control-Allow-Headers", "Content-Type");
            response.header("Access-Control-Allow-Credentials", "true");
            response.header("Content-Type", "application/json");
        });

        /* Setting the routes */
        post("/barcode", (request, response) -> {
            String barcode = gson.fromJson(request.body(), Barcodes.class).getBarcode();
            if (barcode.equals("END")) {
                System.out.println("end");
                // for (Product key : items.keySet()) {
                // DiscountBundle discountBundle = productDatabase.checkForBundle(key);

                // if(!discountBundle.equals(null)){
                // int amount = discountBundle.getQuantity();

                // int calcAmount = amount % quantity;

                // int finalAmount = (quantity - calcAmount) / amount;

                // discountBundleMap.put(discountBundle, finalAmount);
                // }

                // ItemResponse responses = new ItemResponse(items);

                // System.out.println(gson.toJsonTree(responses));

                // }
            } else {
                for (Product key : items.keySet()) {
                    if (key.getBarcode().equals(barcode)) {
                        items.replace(key, items.get(key) + 1);
                        ItemResponse finalResponse = new ItemResponse(items);
                        response.status(200);
                        return gson.toJsonTree(finalResponse);
                    }
                }
                items.put(productDatabase.fetchItem(barcode), 1);
                ItemResponse finalResponse = new ItemResponse(items);
                response.status(200);
                return gson.toJsonTree(finalResponse);
            }
            return null;
        });

        delete("/barcode", (request, response) -> {
            String barcode = gson.fromJson(request.body(), Barcodes.class).getBarcode();
            if (items.size() >= 1) {
                for (Product product : items.keySet()) {
                    if (product.getBarcode().equals(barcode)) {
                        if (items.get(product) == 1) {
                            items.remove(product);
                        } else {
                            items.replace(product, items.get(product) - 1);
                        }

                    }
                }
            }
            ItemResponse finalResponse = new ItemResponse(items);
            response.status(200);
            return gson.toJsonTree(finalResponse);
        });

        post("/creditCard", (request, response) -> {
            response.status(200);
            response.type("application/json");

            CreditCard usersCard = gson.fromJson(request.body(), CreditCard.class);

            String postUrl = "https://acme2pos.azurewebsites.net/payments";// put in your url

            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost post = new HttpPost(postUrl);
            StringEntity postingString = new StringEntity(gson.toJson(usersCard));// gson.tojson() converts the
                                                                                  // creditCard object back to a to json
            post.setEntity(postingString);
            post.setHeader("Content-type", "application/json");

            String authKey = System.getenv("XAUTH_KEY");
            System.out.println(authKey);// auth key removed for safety need to find away to make more secure
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

    static double calculateListPrice(HashMap<Product, Integer> items) {
        double total = 0;

        for (Product key : items.keySet()) {
            total += key.getPrice() * items.get(key);
        }
        return total;
    }
}

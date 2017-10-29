package com.app.retailers.api.yelp.retailers;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import se.akerfeldt.okhttp.signpost.OkHttpOAuthConsumer;
import se.akerfeldt.okhttp.signpost.SigningInterceptor;

/**
 * Created by J.EL on 10/11/2017.
 */

public class YelpService {

    public static void findRetailers(String location,
                                       Callback callback) {

        //create oauth_signature using signpost
        OkHttpOAuthConsumer consumer =  new OkHttpOAuthConsumer(Constants.YELP_CONSUMER_KEY, Constants.YELP_CONSUMER_SECRET);
        consumer.setTokenWithSecret(Constants.YELP_TOKEN, Constants.YELP_TOKEN_SECRET);

        //creste okhttp client to create and send requests
        OkHttpClient client =  new OkHttpClient.Builder()
                .addInterceptor(new SigningInterceptor(consumer))
                .build();

        //build the request url
        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.YELP_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.YELP_LOCATION_QUERY_PARAMETER, location);
        String url = urlBuilder.build().toString();

        //create the request using the new url
        Request request = new Request.Builder()
                .url(url)
                .build();

        //call the request asynchronously
        Call call = client.newCall(request);
        call.enqueue(callback);

    }

    public ArrayList<Retailer> processResults(Response response){
        ArrayList<Retailer> retailers = new ArrayList<>();

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject yelpJSON = new JSONObject(jsonData);
                JSONArray businessesJSON = yelpJSON.getJSONArray("businesses");
                for (int i = 0; i < businessesJSON.length(); i++) {
                    JSONObject retailerJSON = businessesJSON.getJSONObject(i);
                    String name = retailerJSON.getString("name");
                    String phone = retailerJSON.optString("display_phone", "Phone not available");
                    String website = retailerJSON.getString("url");
                    double rating = retailerJSON.getDouble("rating");
                    String imageUrl = retailerJSON.getString("image_url");
                    double latitude = retailerJSON.getJSONObject("location")
                            .getJSONObject("coordinate").getDouble("latitude");
                    double longitude = retailerJSON.getJSONObject("location")
                            .getJSONObject("coordinate").getDouble("longitude");
                    ArrayList<String> address = new ArrayList<>();
                    JSONArray addressJSON = retailerJSON.getJSONObject("location")
                            .getJSONArray("display_address");
                    for (int y = 0; y < addressJSON.length(); y++) {
                        address.add(addressJSON.get(y).toString());
                    }

                    ArrayList<String> categories = new ArrayList<>();
                    JSONArray categoriesJSON = retailerJSON.getJSONArray("categories");
                    Log.i("categories", categoriesJSON + "");

                    for (int y = 0; y < categoriesJSON.length(); y++) {
                        categories.add(categoriesJSON.getJSONArray(y).get(0).toString());
                    }

                    Retailer retailer = new Retailer(name, phone, website, rating, imageUrl, address,
                            latitude, longitude, categories);
                    retailers.add(retailer);
                }
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch (JSONException e){
            e.printStackTrace();
        }
        return retailers;
    }
}

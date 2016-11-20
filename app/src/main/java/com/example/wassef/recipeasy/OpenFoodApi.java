package com.example.wassef.recipeasy;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.FileNotFoundException;

public class OpenFoodApi {

    private final String URL = "https://www.openfood.ch/api/v1/products";
    private final String USER_AGENT = "Mozilla/5.0";
    private RestAPI rest = null;

    public OpenFoodApi(RestAPI rest) {
        this.rest = rest;
    }

    public String getProductName(String barcode){
        String prodName = null;
        try {
            RestAPI r = new RestAPI();
            String jsonStr = r.get(URL, "?barcodes[]="+barcode+"&locale=en");
            JSONObject json = new JSONObject(jsonStr);
            Log.d("hellooo", jsonStr);
            JSONArray arr = json.getJSONArray("data");
            prodName = ((JSONObject) arr.get(0)).getJSONObject("attributes")
                    .getJSONObject("name-translations")
                    .getString("fr");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return prodName;
    }
}
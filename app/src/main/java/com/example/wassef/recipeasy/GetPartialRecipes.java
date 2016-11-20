package com.example.wassef.recipeasy;

/**
 * Created by franciscogirbaleiras on 11/19/16.
 */

import android.text.TextUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class GetPartialRecipes {

    private final String FoodAPIKey = "WBTxTBYms1mshaelQNEJzDAnYItOp1fYX2vjsnu8JZkjMAKNQb";

    private String title;

    public GetPartialRecipes(String title) {
        this.title = title;
    }

    public String getPartialRecipes() {
        // The final list will include the name of the product
        return processFoodAPI();
    }

    private String processFoodAPI() {
        try {
            String url = "https://spoonacular-recipe-food-nutrition-v1.p.mashape.com/food/site/search?query=" + URLEncoder.encode(title, "UTF-8");

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("X-Mashape-Key", FoodAPIKey);
            con.setRequestProperty("Accept", "application/json");

            // optional default is GET
            con.setRequestMethod("GET");
            con.setDoInput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            List<String> response = new ArrayList<>();

            while ((inputLine = in.readLine()) != null) {
                response.add(inputLine);
            }
            in.close();

            String str = TextUtils.join("", response);

            return str;
        } catch(IOException ex) {
            throw new RuntimeException("Problem");
        }
    }

}

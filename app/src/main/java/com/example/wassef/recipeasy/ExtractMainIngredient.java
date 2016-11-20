package com.example.wassef.recipeasy;

/**
 * Created by franciscogirbaleiras on 11/19/16.
 */

import android.text.TextUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ExtractMainIngredient {

    private final String ConceptAPIKey = "SvaagqyaZUCcDqYtmvQFeXmwTAJYs2Ep";
    private final Integer numberOfConcepts = 1;

    private String productName;

    public ExtractMainIngredient(String productName) {
        this.productName = productName;
    }

    public String getMainIngredients() {
        // The final list will include the name of the product
        return processConceptGraphAPI();
    }

    private String processConceptGraphAPI() {
        try {
            String url = "https://concept.research.microsoft.com/api/Concept/ScoreByCross?instance=" + URLEncoder.encode(productName, "UTF-8") + "&topK=" + numberOfConcepts + "&api_key=" + ConceptAPIKey;

            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

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

            List<String> keysList = new ArrayList<String>();
            try {
                JSONObject obj1 = new JSONObject(str);

                Iterator keysToCopyIterator = obj1.keys();
                while (keysToCopyIterator.hasNext()) {
                    String key = (String) keysToCopyIterator.next();
                    keysList.add(key);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            String str1 = TextUtils.join(" ", keysList);

            return str1;
        } catch(IOException ex) {
            throw new RuntimeException("Problem");
        }
    }
}

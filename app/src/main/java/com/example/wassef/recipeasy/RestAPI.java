package com.example.wassef.recipeasy;

/**
 * Created by franciscogirbaleiras on 11/20/16.
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RestAPI {
    private final String USER_AGENT = "Mozilla/5.0";

    public RestAPI() {}

    public String get(String url, String path) throws FileNotFoundException, Exception  {
        //String encodedUrl = URLEncoder.encode(path, "UTF-8");
        URL obj = new URL(url+path);

        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept", "application/xml");

        int resCode = con.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + url + path);
        System.out.println("Response Code : " + resCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));

        String line;
        StringBuffer response = new StringBuffer();
        while ((line = in.readLine()) != null) {
            response.append(line);
        }
        in.close();
        return response.toString();
    }

    public String post(String baseUrl, String param) throws Exception {
        try {
            URL obj = new URL(baseUrl);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestProperty("Ocp-Apim-Subscription-Key", param);
            con.setRequestProperty("Accept", "text/plain");

            con.setRequestMethod("POST");
            con.setDoInput(true);

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
            String inputLine;
            StringBuilder response = new StringBuilder();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            return response.toString();
        } catch(IOException ex) {
            throw new RuntimeException("Problem");
        }
    }
}
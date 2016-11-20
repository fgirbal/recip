package com.example.wassef.recipeasy;

import android.os.AsyncTask;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import java.io.StringReader;
import java.net.URLEncoder;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class BingTranslateAPI extends AsyncTask<String,Void,String>
{
    MainActivity parent;

    public BingTranslateAPI(MainActivity parent){
        this.parent = parent;
    }

    protected void onPreExecute() {
        //display progress dialog.
    }

    @Override
    protected String doInBackground(String... inputText) {
        RestAPI r = new RestAPI();
        String subscriptionKey = "c154831f5a23406d9470a191bb5772ae";
        String subscriptionUrl = "https://api.cognitive.microsoft.com/sts/v1.0/issueToken";
        String bingAPIurl = "http://api.microsofttranslator.com/v2/Http.svc/Translate";
        String result = null;

        try {
            String accessCode = r.post(subscriptionUrl, subscriptionKey);
            String param = "?appid=Bearer%20" + URLEncoder.encode(accessCode, "UTF-8") + "&text="+ URLEncoder.encode(inputText[0], "UTF-8") +"&from=fr&to=en";
            result = r.get(bingAPIurl ,param);

            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            InputSource src = new InputSource();
            src.setCharacterStream(new StringReader(result));

            Document doc = builder.parse(src);
            String str1 = doc.getElementsByTagName("string").item(0).getTextContent();

            return str1;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    protected void onPostExecute(String result) {
        // dismiss progress dialog and update ui
        parent.translateData(result);
    }
}
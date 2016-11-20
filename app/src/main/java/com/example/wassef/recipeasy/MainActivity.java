package com.example.wassef.recipeasy;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.vision.barcode.Barcode;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView barcodeResult;
    protected String title;
    private String translatedData;

    protected ArrayList<String> recipe_titles;
    protected ArrayList<String> recipe_urls;
    protected ArrayList<String> recipe_images_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recipe_titles = new ArrayList<>();
        recipe_urls = new ArrayList<>();
        recipe_images_url = new ArrayList<>();

        barcodeResult = (TextView)findViewById(R.id.barcode_result);

        TrustAllSSL n = new TrustAllSSL();
        n.nuke();
    }

    public void scanBarcode(View v){
        Intent intent = new Intent(this,Scan_Barcode_Activity.class);
        startActivityForResult(intent,0);
    }

    public void postTitleFood(String data) {
        BingTranslateAPI bt = new BingTranslateAPI(this);
        bt.execute(data);
    }

    public void translateData(String data) {
        translatedData = data;

        DownloadPartialRecipes pc = new DownloadPartialRecipes(this);
        pc.execute(data);
    }

    public void postListData(String data) {
        list_parser(data);
        if(recipe_titles.size() != 0) {
            barcodeResult.setText(barcodeResult.getText()+" ; "+recipe_titles+" ; ");
        } else {
            DownloadSimilarWords pc = new DownloadSimilarWords(this);
            pc.execute(translatedData);
        }
    }

    public void postData(String data) {
        translatedData = data;

        DownloadPartialRecipes pc = new DownloadPartialRecipes(this);
        pc.execute(data);
    }

    void list_parser(String data){

        recipe_urls.clear();
        recipe_titles.clear();
        recipe_images_url.clear();

        try {
            JSONObject obj1 = new JSONObject(data);

            if (obj1.isNull("Recipes")) {
                return ;
            }

            JSONArray array = obj1.getJSONArray("Recipes");

            for (int i = 0; i < array.length(); i++) {
                recipe_titles.add(array.getJSONObject(i).getString("name"));
                recipe_images_url.add(array.getJSONObject(i).getString("image"));
                recipe_urls.add(array.getJSONObject(i).getString("link"));
            }

        } catch (JSONException ex) {
            throw new RuntimeException();
        }

    }
     @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
         if (requestCode == 0) {
             if (resultCode == CommonStatusCodes.SUCCESS) {
                 if (data != null) {
                     Barcode barcode = data.getParcelableExtra("barcode");
                     String barcode_result = barcode.displayValue;
                     DownloadFoodTitle bt = new DownloadFoodTitle(this);
                     bt.execute(barcode_result);
                     barcodeResult.setText("Barcode found: " + barcode_result);


                     Intent intent = new Intent(MainActivity.this, ListActivity.class);
                     intent.putExtra("titles",recipe_titles);
                     intent.putExtra("urls",recipe_urls);
                     intent.putExtra("image_url",recipe_images_url);
                     startActivity(intent);


                 } else {
                     barcodeResult.setText("No barcode found");
                 }
             } else {
                 super.onActivityResult(requestCode, resultCode, data);
             }
         }
     }
}

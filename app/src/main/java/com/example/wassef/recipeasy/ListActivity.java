package com.example.wassef.recipeasy;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * Created by wassef on 20/11/16.
 */
public class ListActivity extends Activity {

    ListView list;
    WebView mWebview;
    String[] titles = {
            "Google Plus",
            "Twitter",
            "Windows",
            "Bing",
            "Itunes",
            "Wordpress",
            "Drupal"
    } ;
    String image_url = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQUrYtBfTMdygFPV2oOVRpNu9xluvhEIb3EkwTIx_izPGkMDW18_1z0b4PE";
    String[] urls = {
            image_url,
            image_url,
            image_url,
            image_url,
            image_url,
            image_url,
            image_url
    };

    String[] image_urls = {
            image_url,
            image_url,
            image_url,
            image_url,
            image_url,
            image_url,
            image_url
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_view);

        Bundle b = getIntent().getExtras();
        ArrayList<String> ss = b.getStringArrayList("titles");
        titles = ss.toArray(new String[ss.size()]);
        ss = b.getStringArrayList("urls");
        urls = ss.toArray(new String[ss.size()]);
        ss = b.getStringArrayList("image_url");
        image_urls = ss.toArray(new String[ss.size()]);


        CustomList adapter = new
                CustomList(this, titles, image_urls);
        list=(ListView)findViewById(R.id.list);
        list.setAdapter(adapter);
        ListView vg = (ListView) findViewById(R.id.list);
        vg.invalidate();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Toast.makeText(getApplicationContext(), "You Clicked at " +urls[+ position], Toast.LENGTH_SHORT).show();
                mWebview  = new WebView(ListActivity.this);

                mWebview.getSettings().setJavaScriptEnabled(true); // enable javascript


                mWebview.setWebViewClient(new WebViewClient() {
                    public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                        Toast.makeText(ListActivity.this, description, Toast.LENGTH_SHORT).show();
                    }
                });

                mWebview .loadUrl(urls[position]);
                setContentView(mWebview );
            }
        });

    }
}

package com.example.wassef.recipeasy;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by wassef on 20/11/16.
 */
public class CustomList extends ArrayAdapter<String> {
    private final Activity context;
    private final String[] web;
    private final String[] urls;

    public CustomList(Activity context,
                      String[] web, String[] urls) {
        super(context, R.layout.single_list, web);
        this.context = context;
        this.web = web;
        this.urls = urls;

    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.single_list, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.txt);

        ImageView imageView = (ImageView) rowView.findViewById(R.id.img);
        txtTitle.setText(web[position]);

        DownloadImages a = new DownloadImages(imageView);
        a.execute(urls[position]);

        return rowView;
    }
}
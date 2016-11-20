package com.example.wassef.recipeasy;

/**
 * Created by franciscogirbaleiras on 11/20/16.
 */

import android.os.AsyncTask;

/*
 * The parent app needs to have a postData(List<String> result) method (or change)
 */

public class DownloadFoodTitle extends AsyncTask<String,Void,String>
{
    MainActivity parent;

    public DownloadFoodTitle(MainActivity parent){
        this.parent = parent;
    }

    protected void onPreExecute() {
        //display progress dialog.
    }

    @Override
    protected String doInBackground(String... inputText) {
        OpenFoodApi ofAPI = new OpenFoodApi(new RestAPI());
        String val = ofAPI.getProductName(inputText[0]);
        return val;
    }

    protected void onPostExecute(String result) {
        // dismiss progress dialog and update ui
        parent.title = result;
        parent.postTitleFood(result);
    }
}
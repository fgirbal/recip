package com.example.wassef.recipeasy;

import android.os.AsyncTask;

/**
 * Created by franciscogirbaleiras on 11/19/16.
 */

/*
 * The parent app needs to have a postData(List<String> result) method (or change)
 */

public class DownloadPartialRecipes extends AsyncTask<String,Void,String>
{
    MainActivity parent;

    public DownloadPartialRecipes(MainActivity parent){
        this.parent = parent;
    }

    protected void onPreExecute() {
        //display progress dialog.
    }

    @Override
    protected String doInBackground(String... inputText) {
        GetPartialRecipes m = new GetPartialRecipes(inputText[0]);
        String val = m.getPartialRecipes();
        return val;
    }

    protected void onPostExecute(String result) {
        // dismiss progress dialog and update ui
        parent.postListData(result);
    }
}
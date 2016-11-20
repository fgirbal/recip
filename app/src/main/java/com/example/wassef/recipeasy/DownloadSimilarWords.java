package com.example.wassef.recipeasy;

/**
 * Created by franciscogirbaleiras on 11/20/16.
 */

import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by franciscogirbaleiras on 11/19/16.
 */

/*
 * The parent app needs to have a postData(String result) method (or change)
 */
public class DownloadSimilarWords extends AsyncTask<String,Void,String>
{
    MainActivity parent;

    public DownloadSimilarWords(MainActivity parent){
        this.parent = parent;
    }

    protected void onPreExecute() {
        //display progress dialog.
    }

    @Override
    protected String doInBackground(String... inputText) {
        String rawInput = inputText[0];
        String[] splitInput = rawInput.split(" ");
        List<String> connectedWords = new ArrayList<>();

        for(String word: splitInput) {
            ExtractMainIngredient m = new ExtractMainIngredient(word);
            String val = m.getMainIngredients();
            connectedWords.add(val);
        }

        String result = TextUtils.join(" ", connectedWords);
        return result;
    }

    protected void onPostExecute(String result) {
        // dismiss progress dialog and update ui
        parent.postData(result);
    }
}
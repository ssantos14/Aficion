package com.example.android.aficion.data;

import android.os.AsyncTask;
import android.util.Log;

import com.example.android.aficion.ScoresFeedFragment;
import com.example.android.aficion.utils.NetworkUtils;
import com.example.android.aficion.utils.OpenJsonUtils;

import java.net.URL;

/**
 * Created by Sylvana on 3/26/2018.
 */

public class FetchScoresTask extends AsyncTask<String,Void,String[]>{
    @Override
    protected String[] doInBackground(String... strings) {
        URL scoresUrl = NetworkUtils.buildScoresUrl();
        try{
            String scoresJsonResponse = NetworkUtils.getResponseFromScoresUrl(scoresUrl);
            String[] scoresData = OpenJsonUtils.getScoresFromJson(scoresJsonResponse);
            return scoresData;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String[] scoresData) {
        if(scoresData != null){
            ScoresFeedFragment.mScoresAdapter.setScoresCursor(scoresData);
        }
    }
}

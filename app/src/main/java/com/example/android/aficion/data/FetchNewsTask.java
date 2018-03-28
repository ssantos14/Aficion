package com.example.android.aficion.data;

import android.os.AsyncTask;

import com.example.android.aficion.FeedActivity;
import com.example.android.aficion.NewsFeedFragment;
import com.example.android.aficion.utils.NetworkUtils;
import com.example.android.aficion.utils.OpenJsonUtils;

import java.net.URL;

/**
 * Created by Sylvana on 3/26/2018.
 */

public class FetchNewsTask extends AsyncTask <String,Void,String[]>{


    @Override
    protected String[] doInBackground(String... strings) {
        URL newsUrl = NetworkUtils.buildNewsUrl();
        try{
            String newsJsonResponse = NetworkUtils.getResponseFromUrl(newsUrl);
            String[] newsData = OpenJsonUtils.getNewsFromJson(newsJsonResponse);
            return newsData;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(String[] newsData) {
        if(newsData != null){
            NewsFeedFragment.mNewsAdapter.setNewsCursor(newsData);
        }
    }
}

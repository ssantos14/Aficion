package com.example.android.aficion.data;

import android.content.Context;
import android.os.AsyncTask;

import com.example.android.aficion.HighlightsFeedFragment;
import com.example.android.aficion.utils.YoutubeDataUtils;
import com.google.api.services.youtube.model.SearchResult;

import java.util.List;

/**
 * Created by Sylvana on 3/27/2018.
 */

public class FetchHighlightsTask extends AsyncTask<Context,Void,String[]> {
    @Override
    protected String[] doInBackground(Context... params) {
        List<SearchResult> highlightsInfo = YoutubeDataUtils.getHighlightsInfo(params[0]);
        return YoutubeDataUtils.convertSearchResultsToArray(highlightsInfo);
//        URL highlightsUrl = NetworkUtils.buildHighlightsUrl();
//        try{
//            String highlightsJson = NetworkUtils.getResponseFromUrl(highlightsUrl);
//            String[] highlightsData = OpenJsonUtils.getHighlightsFromJson(highlightsJson);
//            return highlightsData;
//        }catch (Exception e){
//            e.printStackTrace();
//        }
//        return null;
    }

    @Override
    protected void onPostExecute(String[] highlightsData) {
        if(highlightsData != null){
            HighlightsFeedFragment.mHighlightsAdapter.setHighlightsCursor(highlightsData);
        }
    }
}

package com.example.android.aficion.sync;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.util.Log;

import com.example.android.aficion.data.AficionProvider;
import com.example.android.aficion.data.NewsColumns;
import com.example.android.aficion.utils.NetworkUtils;
import com.example.android.aficion.utils.OpenJsonUtils;
import com.example.android.aficion.utils.YoutubeDataUtils;
import com.google.api.services.youtube.model.SearchResult;

import java.net.URL;
import java.util.List;

/**
 * Created by Sylvana on 3/27/2018.
 */

public class SyncDataTask {

    synchronized public static void syncData(Context context, String newsParameter, String[] youtubeParameter){
        try{
            ContentResolver contentResolver = context.getContentResolver();
            URL newsUrl = NetworkUtils.buildNewsUrl(newsParameter);
            String jsonNewsResponse = NetworkUtils.getResponseFromUrl(newsUrl);
            ContentValues[] newsData = OpenJsonUtils.getNewsFromJson(jsonNewsResponse);
            if(newsData != null && newsData.length != 0){
                contentResolver.delete(AficionProvider.News.NEWS_CONTENT_URI, null,null);
                contentResolver.bulkInsert(AficionProvider.News.NEWS_CONTENT_URI,newsData);
            }
            URL scoresURL = NetworkUtils.buildScoresUrl();
            String jsonScoresResponse = NetworkUtils.getResponseFromFantasyDataUrl(scoresURL);
            ContentValues[] scoresData = OpenJsonUtils.getScoresFromJson(jsonScoresResponse);
            if(scoresData != null && scoresData.length != 0){
                contentResolver.delete(AficionProvider.Scores.SCORES_CONTENT_URI,null,null);
                contentResolver.bulkInsert(AficionProvider.Scores.SCORES_CONTENT_URI,scoresData);
            }
            //add for loop for each team
            if(youtubeParameter != null){
                for(int i=0; i<youtubeParameter.length;i++){
                    List<SearchResult> highlightVideosResponse = YoutubeDataUtils.getHighlightsInfo(context,youtubeParameter[i]);
                    ContentValues[] highlightsData = YoutubeDataUtils.getContentValuesFromSearchResults(highlightVideosResponse);
                    if(highlightsData != null && highlightsData.length != 0){
                        if(i==0){
                            contentResolver.delete(AficionProvider.Highlights.HIGHLIGHTS_CONTENT_URI,null,null);
                        }
                        contentResolver.bulkInsert(AficionProvider.Highlights.HIGHLIGHTS_CONTENT_URI,highlightsData);
                    }
                }
            }else{
                String parameter = "FC Barcelona|Real Madrid|FC Bayern Munich|Paris Saint-Germain|Manchester City FC|Juventus";
                List<SearchResult> highlightVideosResponse = YoutubeDataUtils.getHighlightsInfo(context,parameter);
                ContentValues[] highlightsData = YoutubeDataUtils.getContentValuesFromSearchResults(highlightVideosResponse);
                if(highlightsData != null && highlightsData.length != 0){
                    contentResolver.delete(AficionProvider.Highlights.HIGHLIGHTS_CONTENT_URI,null,null);
                    contentResolver.bulkInsert(AficionProvider.Highlights.HIGHLIGHTS_CONTENT_URI,highlightsData);
                }
            }
            URL teamsURL = NetworkUtils.buildTeamsUrl();
            String jsonTeamsResponse = NetworkUtils.getResponseFromFantasyDataUrl(teamsURL);
            ContentValues[] teamsData = OpenJsonUtils.getTeamsFromJson(jsonTeamsResponse);
            if(teamsData != null && teamsData.length != 0){
                contentResolver.delete(AficionProvider.Teams.TEAMS_CONTENT_URI,null,null);
                contentResolver.bulkInsert(AficionProvider.Teams.TEAMS_CONTENT_URI,teamsData);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}

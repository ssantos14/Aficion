package com.example.android.aficion.utils;

import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sylvana on 3/26/2018.
 */

public class OpenJsonUtils {

    public static String[] getNewsFromJson(String newsJson) throws JSONException{
        String[] newsData;
        String articleTitle;
        String articleUrl;
        String articleImageUrl;

        JSONObject response = new JSONObject(newsJson);
        JSONArray articles = response.getJSONArray("articles");
        int numberOfArticles = articles.length();
        newsData = new String[numberOfArticles];
        for(int i=0; i < numberOfArticles; i++){
            JSONObject article = articles.getJSONObject(i);
            articleTitle = article.getString("title");
            //articleUrl = article.getString("url");
            //articleImageUrl = article.getString("urlToImage");
            //newsData[i] = articleTitle + articleUrl + articleImageUrl;
            newsData[i] = articleTitle;
        }
        return newsData;
    }

    public static String[] getScoresFromJson(String scoresJson) throws JSONException{
        String[] scoresData;
        String awayTeam;
        String homeTeam;
        String awayTeamGoals;
        String homeTeamGoals;

        JSONArray games = new JSONArray(scoresJson);
        int numberOfGames = games.length();
        scoresData = new String[numberOfGames];
        for(int i=0; i < numberOfGames; i++){
            JSONObject game = games.getJSONObject(i);
            awayTeam = game.getString("AwayTeamName");
            homeTeam = game.getString("HomeTeamName");
            awayTeamGoals = game.getString("AwayTeamScore");
            homeTeamGoals = game.getString("HomeTeamScore");
            scoresData[i] = awayTeam + " vs " + homeTeam;
        }
        return scoresData;
    }
}

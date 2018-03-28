package com.example.android.aficion.utils;

import android.content.ContentValues;
import android.content.Context;

import com.example.android.aficion.data.NewsColumns;
import com.example.android.aficion.data.ScoresColumns;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Sylvana on 3/26/2018.
 */

public class OpenJsonUtils {

    public static ContentValues[] getNewsFromJson(String newsJson) throws JSONException{
        ContentValues[] newsData;
        String articleTitle;
        String articleUrl;
        String articleImageUrl;

        JSONObject response = new JSONObject(newsJson);
        JSONArray articles = response.getJSONArray("articles");
        int numberOfArticles = articles.length();
        newsData = new ContentValues[numberOfArticles];
        for(int i=0; i < numberOfArticles; i++){
            JSONObject article = articles.getJSONObject(i);
            articleTitle = article.getString("title");
            articleUrl = article.getString("url");
            articleImageUrl = article.getString("urlToImage");

            ContentValues articleContentValues = new ContentValues();
            articleContentValues.put(NewsColumns.TITLE,articleTitle);
            articleContentValues.put(NewsColumns.ARTICLE_URL,articleUrl);
            articleContentValues.put(NewsColumns.IMAGE_URL,articleImageUrl);
            newsData[i] = articleContentValues;
        }
        return newsData;
    }

    public static ContentValues[] getScoresFromJson(String scoresJson) throws JSONException{
        ContentValues[] scoresData;
        String awayTeam;
        String homeTeam;
        String awayTeamGoals;
        String homeTeamGoals;

        JSONArray games = new JSONArray(scoresJson);
        int numberOfGames = games.length();
        scoresData = new ContentValues[numberOfGames];
        for(int i=0; i < numberOfGames; i++){
            JSONObject game = games.getJSONObject(i);
            awayTeam = game.getString("AwayTeamName");
            homeTeam = game.getString("HomeTeamName");
            awayTeamGoals = game.getString("AwayTeamScore");
            homeTeamGoals = game.getString("HomeTeamScore");

            ContentValues gameContentValues = new ContentValues();
            gameContentValues.put(ScoresColumns.AWAY_TEAM,awayTeam);
            gameContentValues.put(ScoresColumns.HOME_TEAM,homeTeam);
            gameContentValues.put(ScoresColumns.AWAY_TEAM_GOALS,awayTeamGoals);
            gameContentValues.put(ScoresColumns.HOME_TEAM_GOALS,homeTeamGoals);
            scoresData[i] = gameContentValues;
        }
        return scoresData;
    }
}

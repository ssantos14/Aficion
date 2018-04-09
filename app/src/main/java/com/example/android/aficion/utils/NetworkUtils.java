package com.example.android.aficion.utils;

import android.content.Context;
import android.net.Uri;

import com.example.android.aficion.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

/**
 * Created by Sylvana on 3/26/2018.
 */

public class NetworkUtils {

    private static final String NEWS_BASE_URL = "https://newsapi.org/v2/everything?";
    private static final String SCORES_BASE_URL = "https://api.fantasydata.net/v3/soccer/scores";
    private static final String TEAMS_BASE_URL = "https://api.fantasydata.net/v3/soccer/scores/json/Teams";

    public static URL buildNewsUrl(String qParameter, Context context){
        Uri.Builder uriBuilder = Uri.parse(NEWS_BASE_URL).buildUpon()
                .appendQueryParameter("pageSize","25")
                .appendQueryParameter("sortBy","publishedAt")
                .appendQueryParameter("language","en")
                .appendQueryParameter("domains","espnfc.com")
                .appendQueryParameter("apiKey",context.getString(R.string.news_api_key));
        if(qParameter == null){
            uriBuilder.appendQueryParameter("q","FC Barcelona OR Real Madrid OR FC Bayern Munich " +
                    "OR Paris Saint-Germain OR Manchester City FC OR Juventus NOT live");
        }else {
            uriBuilder.appendQueryParameter("q",qParameter);
        }
        Uri builtUri = uriBuilder.build();
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildScoresUrl(){
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(date);
        Uri builtUri = Uri.parse(SCORES_BASE_URL).buildUpon()
                .appendPath("JSON")
                .appendPath("GamesByDate")
                .appendPath(formattedDate)
                .build();
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildTeamsUrl(){
        Uri builtUri = Uri.parse(TEAMS_BASE_URL).buildUpon().build();
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static String getResponseFromUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

    public static String getResponseFromFantasyDataUrl(URL url, Context context) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setRequestProperty("Ocp-Apim-Subscription-Key",context.getString(R.string.fantasy_data_api_key));
            InputStream in = urlConnection.getInputStream();

            Scanner scanner = new Scanner(in);
            scanner.useDelimiter("\\A");

            boolean hasInput = scanner.hasNext();
            if (hasInput) {
                return scanner.next();
            } else {
                return null;
            }
        } finally {
            urlConnection.disconnect();
        }
    }

}

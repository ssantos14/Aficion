package com.example.android.aficion.utils;

import android.net.Uri;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Sylvana on 3/26/2018.
 */

public class NetworkUtils {

    private static final String NEWS_BASE_URL = "https://newsapi.org/v2/everything?";
    private static final String SCORES_BASE_URL = "https://api.fantasydata.net/v3/soccer/scores";

    public static URL buildNewsUrl(){
        Uri builtUri = Uri.parse(NEWS_BASE_URL).buildUpon()
                .appendQueryParameter("q", "messi")
                .appendQueryParameter("from","2018-03-27")
                .appendQueryParameter("sortBy","popularity")
                .appendQueryParameter("apiKey","c9e9020b9f7f44ec9f2f61b5cfbf3763")
                .build();
        URL url = null;
        try{
            url = new URL(builtUri.toString());
        }catch (MalformedURLException e){
            e.printStackTrace();
        }
        return url;
    }

    public static URL buildScoresUrl(){
        Uri builtUri = Uri.parse(SCORES_BASE_URL).buildUpon()
                .appendPath("JSON")
                .appendPath("GamesByDate")
                .appendPath("2018-03-14")
                .build();
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

    public static String getResponseFromScoresUrl(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            urlConnection.setRequestProperty("Ocp-Apim-Subscription-Key","53ec4afb9f6b41d79f6ee6395e8714d3");
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

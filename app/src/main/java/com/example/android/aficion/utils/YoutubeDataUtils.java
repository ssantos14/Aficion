package com.example.android.aficion.utils;

import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

import com.example.android.aficion.data.AficionProvider;
import com.example.android.aficion.data.HighlightsColumns;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.HttpRequest;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.common.io.BaseEncoding;

import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sylvana on 3/27/2018.
 */

public class YoutubeDataUtils {
    public static List<SearchResult> getHighlightsInfo(final Context context){
        YouTube youtube;
        YouTube.Search.List search;

        try {
            HttpTransport transport = AndroidHttp.newCompatibleTransport();
            JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
            youtube = new YouTube.Builder(transport, jsonFactory, new HttpRequestInitializer() {
                @Override
                public void initialize(HttpRequest request) throws IOException {
                    String packageName = context.getPackageName();
                    String SHA1;
                    try {
                        Signature[] signatures = context.getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures;
                        for (Signature signature: signatures) {
                            MessageDigest md;
                            md = MessageDigest.getInstance("SHA-1");
                            md.update(signature.toByteArray());
                            SHA1 = BaseEncoding.base16().encode(md.digest());
                            request.getHeaders().set("X-Android-Package", packageName);
                            request.getHeaders().set("X-Android-Cert",SHA1);
                        }
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    } catch (NoSuchAlgorithmException e) {
                        e.printStackTrace();
                    }
                }
            }).setApplicationName("Aficion").build();
            search = youtube.search().list("id,snippet");
            String apiKey = "AIzaSyDgtCNva4-A2Sd9M7aTuLzBPd2FlkUysl4";
            search.setKey(apiKey);
            search.setQ("messi");
            search.setType("video");
            search.setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)");
            search.setMaxResults((long)25);
            SearchListResponse searchResponse = search.execute();
            List<SearchResult> searchResultList = searchResponse.getItems();
            return searchResultList;
        } catch (GoogleJsonResponseException e) {
            System.err.println("There was a service error: " + e.getDetails().getCode() + " : "
                    + e.getDetails().getMessage());
        } catch (IOException e) {
            System.err.println("There was an IO error: " + e.getCause() + " : " + e.getMessage());
        } catch (Throwable t) {
            t.printStackTrace();
        }
        return null;
    }

    public static ContentValues[] getContentValuesFromSearchResults(List<SearchResult> highlightsResults){
        int numberOfVideos = highlightsResults.size();
        ContentValues[] videosInfo = new ContentValues[numberOfVideos];
        for(int i=0; i<numberOfVideos; i++){
            SearchResult video = highlightsResults.get(i);
            String videoTitle = video.getSnippet().getTitle();
            String videoThumbnail = video.getSnippet().getThumbnails().getDefault().getUrl();
            String videoId = video.getId().getVideoId();

            ContentValues videoContentValues = new ContentValues();
            videoContentValues.put(HighlightsColumns.VIDEO_TITLE,videoTitle);
            videoContentValues.put(HighlightsColumns.VIDEO_ID,videoId);
            videoContentValues.put(HighlightsColumns.THUMBNAIL_URL,videoThumbnail);
            videosInfo[i] = videoContentValues;
        }
        return videosInfo;
    }

}

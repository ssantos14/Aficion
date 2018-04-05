package com.example.android.aficion.sync;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.aficion.FeedActivity;
import com.example.android.aficion.data.AficionProvider;
import com.example.android.aficion.data.ScoresColumns;
import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.Trigger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Sylvana on 4/4/2018.
 */

public class SyncDataUtils {

    public static final String NEWS_PARAMETER_EXTRA = "news_parameters";
    public static final String HIGHLIGHTS_PARAMETER_EXTRA = "highlights_parameters";

    private static final int SYNC_INTERVAL_MINS = 180;
    private static final int SYNC_INTERVAL_SECONDS = (int) TimeUnit.MINUTES.toSeconds(SYNC_INTERVAL_MINS);
    private static final int SYNC_FLEXTIME_SECONDS = SYNC_INTERVAL_SECONDS;

    private static boolean sInitialized;

    private static final String SYNC_TAG = "sync_all_data";

    synchronized public static void scheduleFirebaseJobDispatcherSync(@NonNull final Context context) {
        if(sInitialized) return;
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(context));
        Job syncDataJob = dispatcher.newJobBuilder()
                .setService(SyncDataFirebaseJobService.class)
                .setTag(SYNC_TAG)
                .setConstraints(Constraint.ON_ANY_NETWORK)
                .setLifetime(Lifetime.FOREVER)
                .setRecurring(true)
                .setTrigger(Trigger.executionWindow(
                        SYNC_INTERVAL_SECONDS,
                        SYNC_INTERVAL_SECONDS + SYNC_FLEXTIME_SECONDS))
                .setReplaceCurrent(true)
                .build();
        dispatcher.schedule(syncDataJob);
        Log.d(SyncDataUtils.class.getSimpleName(),"INITIALIZED JOB SERVICE");
        sInitialized = true;
    }

    public static void startImmediateSync(@NonNull final Context context) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String newsQueryParameter = getNewsQueryParameter(sharedPreferences);
        String[] youtubeQueryParameter = getYoutubeQueryParameter(sharedPreferences);
        Intent intentToSync = new Intent(context, SyncDataIntentService.class);
        intentToSync.putExtra(NEWS_PARAMETER_EXTRA,newsQueryParameter);
        intentToSync.putExtra(HIGHLIGHTS_PARAMETER_EXTRA,youtubeQueryParameter);
        context.startService(intentToSync);
    }

    public static String getNewsQueryParameter(SharedPreferences sharedPreferences){
        Map<String,?> prefs = sharedPreferences.getAll();
        List<String> teamsFollowing = new ArrayList<String>();
        for (Map.Entry<String, ?> entry : prefs.entrySet()) {
            if(entry.getValue().toString() == "true"){
                teamsFollowing.add(entry.getKey());
            }
        }
        String queryParameter = "";
        if(teamsFollowing.size() > 0){
            Object[] teams = teamsFollowing.toArray();
            for(int i =0; i<teams.length; i++){
                queryParameter = queryParameter + teams[i];
                if(i < teams.length-1){
                    queryParameter = queryParameter + " OR ";
                }else{
                    queryParameter = queryParameter + " NOT live";
                }
            }
            Log.d(FeedActivity.class.getSimpleName(),"NEWS QUERY PARAMETER BUILT: " + queryParameter);
        }else{
            queryParameter = null;
        }
        return queryParameter;
    }

    public static String[] getYoutubeQueryParameter(SharedPreferences sharedPreferences){
        Map<String,?> prefs = sharedPreferences.getAll();
        List<String> teamsFollowing = new ArrayList<String>();
        for (Map.Entry<String, ?> entry : prefs.entrySet()) {
            if(entry.getValue().toString() == "true"){
                teamsFollowing.add(entry.getKey());
            }
        }
        String[] queryParameter;
        if(teamsFollowing.size() > 0){
            Object[] teams = teamsFollowing.toArray();
            queryParameter = new String[teamsFollowing.size()];
            for(int i =0; i<teams.length; i++){
                queryParameter[i] = teams[i].toString() + " Highlights";
                Log.d(FeedActivity.class.getSimpleName(),"HIGHLIGHTS QUERY PARAMETER BUILT: " + queryParameter[i]);
            }
        }else{
            queryParameter = null;
        }
        return queryParameter;
    }


}

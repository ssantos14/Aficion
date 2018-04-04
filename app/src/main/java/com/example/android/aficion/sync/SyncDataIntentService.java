package com.example.android.aficion.sync;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import com.example.android.aficion.FeedActivity;

/**
 * Created by Sylvana on 3/27/2018.
 */

public class SyncDataIntentService extends IntentService{
    public SyncDataIntentService() {super("SyncDataIntentService");}

    @Override
    protected void onHandleIntent(Intent intent) {
        String newsParameter = intent.getStringExtra(FeedActivity.NEWS_PARAMETER_EXTRA);
        String[] youtubeParameter = intent.getStringArrayExtra(FeedActivity.HIGHLIGHTS_PARAMETER_EXTRA);
        SyncDataTask.syncData(this, newsParameter, youtubeParameter);
    }
}

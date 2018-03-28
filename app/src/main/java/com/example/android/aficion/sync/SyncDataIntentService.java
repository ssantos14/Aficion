package com.example.android.aficion.sync;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Sylvana on 3/27/2018.
 */

public class SyncDataIntentService extends IntentService{
    public SyncDataIntentService() {super("SyncDataIntentService");}

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d(SyncDataIntentService.class.getSimpleName(),"got to intent service");
        SyncDataTask.syncData(this);
    }
}

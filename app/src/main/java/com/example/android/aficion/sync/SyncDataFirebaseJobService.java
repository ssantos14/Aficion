package com.example.android.aficion.sync;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

/**
 * Created by Sylvana on 4/4/2018.
 */

public class SyncDataFirebaseJobService extends JobService {
    private AsyncTask mBackgroundSyncTask;

    @Override
    public boolean onStartJob(final JobParameters jobParameters) {
        mBackgroundSyncTask = new AsyncTask() {
            @Override
            protected Object doInBackground(Object[] objects) {
                Context context = SyncDataFirebaseJobService.this;
                SyncDataUtils.startImmediateSync(context);
                Log.d(SyncDataFirebaseJobService.class.getSimpleName(),"SYNCED DATA FROM JOBSERVICE");
                return null;
            }

            @Override
            protected void onPostExecute(Object object) {
                jobFinished(jobParameters,false);
            }
        };
        mBackgroundSyncTask.execute();
        return true;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        if(mBackgroundSyncTask != null){
            mBackgroundSyncTask.cancel(true);
        }
        return true;
    }
}

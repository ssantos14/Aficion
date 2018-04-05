package com.example.android.aficion.widget;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.preference.PreferenceManager;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.aficion.FeedActivity;
import com.example.android.aficion.R;
import com.example.android.aficion.data.AficionProvider;
import com.example.android.aficion.data.ScoresColumns;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Sylvana on 4/4/2018.
 */

public class StackWidgetService extends RemoteViewsService {
    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new StackRemoteViewsFactory(this.getApplicationContext());
    }
}

class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {
    Context mContext;
    Cursor mCursor;

    public StackRemoteViewsFactory(Context applicationContext) {
        mContext = applicationContext;
    }

    @Override
    public void onCreate() {}

    //called on start and when notifyAppWidgetViewDataChanged is called
    @Override
    public void onDataSetChanged() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        String selection = FeedActivity.getCursorSelection(sharedPreferences);
        if (mCursor != null) mCursor.close();
        mCursor = mContext.getContentResolver().query(
                AficionProvider.Scores.SCORES_CONTENT_URI,
                null,
                selection,
                null,
                null
        );
    }

    @Override
    public void onDestroy() {
        mCursor.close();
    }

    @Override
    public int getCount() {
        if (mCursor == null) return 0;
        return mCursor.getCount();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if (mCursor == null || mCursor.getCount() == 0) return null;
        mCursor.moveToPosition(position);
        String team1 = mCursor.getString(0);
        String team2 = mCursor.getString(1);
        String team1Goals = mCursor.getString(2);
        String team2Goals = mCursor.getString(3);
        if(team1Goals.equals("null")){
            team1Goals = "0";
        }
        if(team2Goals.equals("null")){
            team2Goals = "0";
        }
        String teams = team1 + " vs " + team2;
        String score = team1Goals + " - " + team2Goals;
        RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_stack_view_item);
        views.setTextViewText(R.id.teams_text_view, teams);
        views.setTextViewText(R.id.score_text_view, score);
        Intent startAppIntent = new Intent(mContext,FeedActivity.class);
        views.setOnClickFillInIntent(R.id.teams_text_view, startAppIntent);
        return views;

    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }
}

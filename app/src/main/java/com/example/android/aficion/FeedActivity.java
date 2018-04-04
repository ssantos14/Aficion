package com.example.android.aficion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.util.Log;
import android.view.View;

import com.example.android.aficion.data.AficionProvider;
import com.example.android.aficion.data.ScoresColumns;
import com.example.android.aficion.sync.SyncDataIntentService;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FeedActivity extends AppCompatActivity implements NavigationBarFragment.OnFeedClickListener,
        LoaderManager.LoaderCallbacks<Cursor>,SharedPreferences.OnSharedPreferenceChangeListener{
    private static final int NEWS_LOADER_ID = 12;
    private static final int SCORES_LOADER_ID = 99;
    private static final int HIGHLIGHTS_LOADER_ID = 47;
    public static final String NEWS_PARAMETER_EXTRA = "news_parameters";
    public static final String HIGHLIGHTS_PARAMETER_EXTRA = "highlights_parameters";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        startImmediateSync(sharedPreferences);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);
        NewsFeedFragment newsFeedFragment = new NewsFeedFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.feed_container, newsFeedFragment).commit();
        getSupportLoaderManager().initLoader(NEWS_LOADER_ID,null,this);
    }


    @Override
    public void onFeedSelected(int clickedItemId) {
        switch (clickedItemId) {
            case R.id.navigation_news:
                NewsFeedFragment newsFeedFragment = new NewsFeedFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.feed_container,newsFeedFragment).commit();
                getSupportLoaderManager().restartLoader(NEWS_LOADER_ID,null,this);
                break;
            case R.id.navigation_scores:
                ScoresFeedFragment scoresFeedFragment = new ScoresFeedFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.feed_container,scoresFeedFragment).commit();
                getSupportLoaderManager().restartLoader(SCORES_LOADER_ID,null,this);
                break;
            case R.id.navigation_highlights:
                HighlightsFeedFragment highlightsFeedFragment = new HighlightsFeedFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.feed_container,highlightsFeedFragment).commit();
                getSupportLoaderManager().restartLoader(HIGHLIGHTS_LOADER_ID,null,this);
                break;
            case R.id.navigation_following:
                TeamsFollowingFragment followingFragment = new TeamsFollowingFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.feed_container,followingFragment).commit();
            default:
                break;
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int loaderId, Bundle args) {
        switch (loaderId) {
            case NEWS_LOADER_ID:
                return new CursorLoader(this, AficionProvider.News.NEWS_CONTENT_URI, null, null, null, null);
            case SCORES_LOADER_ID:
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
                String selection = getCursorSelection(sharedPreferences);
                return new CursorLoader(this,AficionProvider.Scores.SCORES_CONTENT_URI,null,selection,null,null);
            case HIGHLIGHTS_LOADER_ID:
                return new CursorLoader(this,AficionProvider.Highlights.HIGHLIGHTS_CONTENT_URI,null,null,null,null);
            default:
                throw new RuntimeException("Loader not implemented: " + loaderId);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        int loaderId = loader.getId();
        switch (loaderId){
            case NEWS_LOADER_ID:
                NewsFeedFragment.mNewsAdapter.setNewsCursor(data);
                break;
            case SCORES_LOADER_ID:
                if(data != null && data.getCount() > 0){
                    ScoresFeedFragment.mScoresAdapter.setScoresCursor(data);
                }else{
                    ScoresFeedFragment.mMessageTextView.setVisibility(View.VISIBLE);
                }
                break;
            case HIGHLIGHTS_LOADER_ID:
                HighlightsFeedFragment.mHighlightsAdapter.setHighlightsCursor(data);
                break;
            default:
                break;
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        int loaderId = loader.getId();
        switch (loaderId){
            case NEWS_LOADER_ID:
                NewsFeedFragment.mNewsAdapter.setNewsCursor(null);
                break;
            case SCORES_LOADER_ID:
                ScoresFeedFragment.mScoresAdapter.setScoresCursor(null);
                break;
            case HIGHLIGHTS_LOADER_ID:
                HighlightsFeedFragment.mHighlightsAdapter.setHighlightsCursor(null);
                break;
            default:
                break;
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        startImmediateSync(sharedPreferences);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

    private String getNewsQueryParameter(SharedPreferences sharedPreferences){
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
            Log.d(FeedActivity.class.getSimpleName(),"QUERY PARAMETER BUILT: " + queryParameter);
        }else{
            queryParameter = null;
        }
        return queryParameter;
    }

    private String getCursorSelection(SharedPreferences sharedPreferences){
        Map<String,?> prefs = sharedPreferences.getAll();
        List<String> teamsFollowing = new ArrayList<String>();
        for (Map.Entry<String, ?> entry : prefs.entrySet()) {
            if(entry.getValue().toString() == "true"){
                teamsFollowing.add(entry.getKey());
            }
        }
        String selection = "";
        if(teamsFollowing.size() > 0){
            Object[] teams = teamsFollowing.toArray();
            for(int i =0; i<teams.length; i++){
                selection = selection + ScoresColumns.HOME_TEAM + "='" + teams[i] + "'"
                        +  " OR " + ScoresColumns.AWAY_TEAM + "='" + teams[i] + "'";
                if(i < teams.length-1){
                    selection = selection + " OR ";
                }
            }
            Log.d(FeedActivity.class.getSimpleName(),"QUERY PARAMETER BUILT: " + selection);
        }else{
            selection = null;
        }
        return selection;
    }

    private String[] getYoutubeQueryParameter(SharedPreferences sharedPreferences){
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
            }
        }else{
            queryParameter = null;
        }
        return queryParameter;
    }

    private void startImmediateSync(SharedPreferences sharedPreferences){
        String newsQueryParameter = getNewsQueryParameter(sharedPreferences);
        String[] youtubeQueryParameter = getYoutubeQueryParameter(sharedPreferences);
        Intent intentToSync = new Intent(this, SyncDataIntentService.class);
        intentToSync.putExtra(NEWS_PARAMETER_EXTRA,newsQueryParameter);
        intentToSync.putExtra(HIGHLIGHTS_PARAMETER_EXTRA,youtubeQueryParameter);
        startService(intentToSync);
    }
}

package com.example.android.aficion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.preference.PreferenceManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.aficion.data.AficionProvider;
import com.example.android.aficion.sync.SyncDataIntentService;


public class FeedActivity extends AppCompatActivity implements NavigationBarFragment.OnFeedClickListener,
        LoaderManager.LoaderCallbacks<Cursor>{
    private static final int NEWS_LOADER_ID = 12;
    private static final int SCORES_LOADER_ID = 99;
    private static final int HIGHLIGHTS_LOADER_ID = 47;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        Intent intentToSync = new Intent(this, SyncDataIntentService.class);
        startService(intentToSync);
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
                return new CursorLoader(this,AficionProvider.Scores.SCORES_CONTENT_URI,null,null,null,null);
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
                DatabaseUtils.dumpCursor(data);
                NewsFeedFragment.mNewsAdapter.setNewsCursor(data);
                break;
            case SCORES_LOADER_ID:
                ScoresFeedFragment.mScoresAdapter.setScoresCursor(data);
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
}

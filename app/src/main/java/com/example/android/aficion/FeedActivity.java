package com.example.android.aficion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v4.view.LayoutInflaterCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.example.android.aficion.data.AficionProvider;
import com.example.android.aficion.data.ScoresColumns;
import com.example.android.aficion.sync.SyncDataIntentService;
import com.example.android.aficion.sync.SyncDataTask;
import com.example.android.aficion.sync.SyncDataUtils;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.mikepenz.iconics.context.IconicsLayoutInflater2;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class FeedActivity extends AppCompatActivity implements NavigationBarFragment.OnFeedClickListener,
        SharedPreferences.OnSharedPreferenceChangeListener{
    public static final String NEWS_PARAMETER_EXTRA = "news_parameters";
    public static final String HIGHLIGHTS_PARAMETER_EXTRA = "highlights_parameters";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        if(savedInstanceState == null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            SyncDataUtils.startImmediateSync(this);
            SyncDataUtils.scheduleFirebaseJobDispatcherSync(this);
            sharedPreferences.registerOnSharedPreferenceChangeListener(this);
            NewsFeedFragment newsFeedFragment = new NewsFeedFragment();
            android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().add(R.id.feed_container, newsFeedFragment).commit();
        }
    }


    @Override
    public void onFeedSelected(int clickedItemId) {
        switch (clickedItemId) {
            case R.id.navigation_news:
                NewsFeedFragment newsFeedFragment = new NewsFeedFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.feed_container,newsFeedFragment).commit();
                break;
            case R.id.navigation_scores:
                ScoresFeedFragment scoresFeedFragment = new ScoresFeedFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.feed_container,scoresFeedFragment).commit();
                break;
            case R.id.navigation_highlights:
                HighlightsFeedFragment highlightsFeedFragment = new HighlightsFeedFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.feed_container,highlightsFeedFragment).commit();
                break;
            case R.id.navigation_following:
                TeamsFollowingFragment followingFragment = new TeamsFollowingFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.feed_container,followingFragment).commit();
            default:
                break;
        }
    }

    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        SyncDataUtils.startImmediateSync(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        PreferenceManager.getDefaultSharedPreferences(this).unregisterOnSharedPreferenceChangeListener(this);
    }

}

package com.example.android.aficion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.android.aficion.data.FetchHighlightsTask;
import com.example.android.aficion.data.FetchNewsTask;
import com.example.android.aficion.data.FetchScoresTask;

public class FeedActivity extends AppCompatActivity implements NavigationBarFragment.OnFeedClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        NewsFeedFragment newsFeedFragment = new NewsFeedFragment();
        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().add(R.id.feed_container, newsFeedFragment).commit();
        new FetchNewsTask().execute();
    }


    @Override
    public void onFeedSelected(int clickedItemId) {
        switch (clickedItemId) {
            case R.id.navigation_news:
                NewsFeedFragment newsFeedFragment = new NewsFeedFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.feed_container,newsFeedFragment).commit();
                new FetchNewsTask().execute();
                break;
            case R.id.navigation_scores:
                ScoresFeedFragment scoresFeedFragment = new ScoresFeedFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.feed_container,scoresFeedFragment).commit();
                new FetchScoresTask().execute();
                break;
            case R.id.navigation_highlights:
                HighlightsFeedFragment highlightsFeedFragment = new HighlightsFeedFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.feed_container,highlightsFeedFragment).commit();
                new FetchHighlightsTask().execute(getApplicationContext());
                break;
            default:
                break;
        }
    }
}

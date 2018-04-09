package com.example.android.aficion;

import android.content.SharedPreferences;
import android.database.Cursor;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.aficion.data.AficionProvider;
import com.example.android.aficion.data.ScoresColumns;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ScoresFeedFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    public static ScoresAdapter mScoresAdapter;
    public static TextView mMessageTextView;
    public static AdView mAdView;
    private static final int SCORES_LOADER_ID = 99;

    public ScoresFeedFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scores_feed,container,false);
        MobileAds.initialize(container.getContext(),"ca-app-pub-8633006304319916~8819182894");
        mAdView = rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        RecyclerView mScoresFeedRecyclerView = rootView.findViewById(R.id.scores_recycler_view);
        mScoresFeedRecyclerView.setHasFixedSize(true);
        mScoresAdapter = new ScoresAdapter();
        mScoresFeedRecyclerView.setAdapter(mScoresAdapter);
        mMessageTextView = rootView.findViewById(R.id.message_display);
        getLoaderManager().initLoader(SCORES_LOADER_ID,null,this);
        return rootView;
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String selection = getCursorSelection(sharedPreferences);
        return new CursorLoader(getActivity(), AficionProvider.Scores.SCORES_CONTENT_URI,null,selection,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if(data != null && data.getCount() > 0){
            mScoresAdapter.setScoresCursor(data);
        }else{
            mMessageTextView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mScoresAdapter.setScoresCursor(null);
    }

    public static String getCursorSelection(SharedPreferences sharedPreferences){
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
            Log.d(FeedActivity.class.getSimpleName(),"SCORES QUERY PARAMETER BUILT: " + selection);
        }else{
            selection = null;
        }
        return selection;
    }
}

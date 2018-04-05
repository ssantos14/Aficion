package com.example.android.aficion;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

public class ScoresFeedFragment extends Fragment {
    public static ScoresAdapter mScoresAdapter;
    public static TextView mMessageTextView;
    public static AdView mAdView;

    public ScoresFeedFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scores_feed,container,false);
        MobileAds.initialize(container.getContext(),"ca-app-pub-8633006304319916~8819182894");
        mAdView = rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
        RecyclerView mScoresFeedRecyclerView = rootView.findViewById(R.id.scores_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        mScoresFeedRecyclerView.setLayoutManager(layoutManager);
        mScoresFeedRecyclerView.setHasFixedSize(true);
        mScoresAdapter = new ScoresAdapter();
        mScoresFeedRecyclerView.setAdapter(mScoresAdapter);
        mMessageTextView = rootView.findViewById(R.id.message_display);
        return rootView;
    }

}

package com.example.android.aficion;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ScoresFeedFragment extends Fragment {
    public static ScoresAdapter mScoresAdapter;

    public ScoresFeedFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_scores_feed,container,false);
        RecyclerView mScoresFeedRecyclerView = rootView.findViewById(R.id.scores_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        mScoresFeedRecyclerView.setLayoutManager(layoutManager);
        mScoresFeedRecyclerView.setHasFixedSize(true);
        mScoresAdapter = new ScoresAdapter();
        mScoresFeedRecyclerView.setAdapter(mScoresAdapter);
        return rootView;
    }

}

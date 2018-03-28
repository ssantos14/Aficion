package com.example.android.aficion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HighlightsFeedFragment extends Fragment {
    public static HighlightsAdapter mHighlightsAdapter;

    public HighlightsFeedFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_highlights_feed,container,false);
        RecyclerView highlightsRecyclerView = rootView.findViewById(R.id.highlight_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        highlightsRecyclerView.setLayoutManager(layoutManager);
        highlightsRecyclerView.setHasFixedSize(true);
        mHighlightsAdapter = new HighlightsAdapter();
        highlightsRecyclerView.setAdapter(mHighlightsAdapter);
        return rootView;
    }

}
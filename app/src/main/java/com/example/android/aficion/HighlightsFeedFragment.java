package com.example.android.aficion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HighlightsFeedFragment extends Fragment implements HighlightsAdapter.HighlightsAdapterOnClickHandler{
    public static HighlightsAdapter mHighlightsAdapter;

    public HighlightsFeedFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_highlights_feed,container,false);
        RecyclerView highlightsRecyclerView = rootView.findViewById(R.id.highlight_recycler_view);
        highlightsRecyclerView.setHasFixedSize(true);
        mHighlightsAdapter = new HighlightsAdapter(this);
        highlightsRecyclerView.setAdapter(mHighlightsAdapter);
        return rootView;
    }

    @Override
    public void OnHighlightsVideoClick(String videoId) {
        String videoUrl = "http://www.youtube.com/watch?v=" + videoId;
        Intent openVideoIntent = new Intent(Intent.ACTION_VIEW);
        openVideoIntent.setData(Uri.parse(videoUrl));
        startActivity(openVideoIntent);
    }
}
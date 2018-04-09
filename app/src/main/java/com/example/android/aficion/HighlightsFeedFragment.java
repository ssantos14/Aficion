package com.example.android.aficion;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.aficion.data.AficionProvider;

public class HighlightsFeedFragment extends Fragment implements HighlightsAdapter.HighlightsAdapterOnClickHandler,
        LoaderManager.LoaderCallbacks<Cursor>{
    public static HighlightsAdapter mHighlightsAdapter;
    private static final int HIGHLIGHTS_LOADER_ID = 47;

    public HighlightsFeedFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_highlights_feed,container,false);
        RecyclerView highlightsRecyclerView = rootView.findViewById(R.id.highlight_recycler_view);
        highlightsRecyclerView.setHasFixedSize(true);
        mHighlightsAdapter = new HighlightsAdapter(this);
        highlightsRecyclerView.setAdapter(mHighlightsAdapter);
        getLoaderManager().initLoader(HIGHLIGHTS_LOADER_ID,null,this);
        return rootView;
    }

    @Override
    public void OnHighlightsVideoClick(String videoId) {
        String videoUrl = "http://www.youtube.com/watch?v=" + videoId;
        Intent openVideoIntent = new Intent(Intent.ACTION_VIEW);
        openVideoIntent.setData(Uri.parse(videoUrl));
        startActivity(openVideoIntent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), AficionProvider.Highlights.HIGHLIGHTS_CONTENT_URI,null,null,null,null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mHighlightsAdapter.setHighlightsCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mHighlightsAdapter.setHighlightsCursor(null);
    }
}
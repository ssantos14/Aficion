package com.example.android.aficion;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewsFeedFragment extends Fragment {
    private RecyclerView mNewsFeedRecyclerView;
    public static NewsAdapter mNewsAdapter;

    public NewsFeedFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_feed,container,false);
        mNewsFeedRecyclerView = rootView.findViewById(R.id.news_recycler_view);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        mNewsFeedRecyclerView.setLayoutManager(layoutManager);
        mNewsFeedRecyclerView.setHasFixedSize(true);
        mNewsAdapter = new NewsAdapter();
        mNewsFeedRecyclerView.setAdapter(mNewsAdapter);
        return rootView;
    }

}

package com.example.android.aficion;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewsFeedFragment extends Fragment implements NewsAdapter.NewsAdapterOnClickHandler {
    private RecyclerView mNewsFeedRecyclerView;
    public static NewsAdapter mNewsAdapter;

    public NewsFeedFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_feed,container,false);
        mNewsFeedRecyclerView = rootView.findViewById(R.id.news_recycler_view);
        mNewsFeedRecyclerView.setHasFixedSize(true);
        mNewsAdapter = new NewsAdapter(this);
        mNewsFeedRecyclerView.setAdapter(mNewsAdapter);
        return rootView;
    }

    @Override
    public void onNewsArticleClick(String articleUrl) {
        Intent openArticleIntent = new Intent(Intent.ACTION_VIEW);
        openArticleIntent.setData(Uri.parse(articleUrl));
        startActivity(openArticleIntent);
    }

//    @Override
//    public void onActivityCreated(Bundle savedInstanceState) {
//        super.onActivityCreated(savedInstanceState);
//        if (savedInstanceState != null) {
//            Parcelable state = savedInstanceState.getParcelable("SCROLL_POSITION");
//            mNewsFeedRecyclerView.getLayoutManager().onRestoreInstanceState(state);
//            //Restore the fragment's state here
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
//        Parcelable state = mNewsFeedRecyclerView.getLayoutManager().onSaveInstanceState();
//        outState.putParcelable("SCROLL_POSITION", state);
//    }

}

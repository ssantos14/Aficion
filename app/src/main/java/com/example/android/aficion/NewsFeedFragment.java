package com.example.android.aficion;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.aficion.data.AficionProvider;

public class NewsFeedFragment extends Fragment implements NewsAdapter.NewsAdapterOnClickHandler,LoaderManager.LoaderCallbacks<Cursor> {
    private RecyclerView mNewsFeedRecyclerView;
    public static NewsAdapter mNewsAdapter;
    private static final int NEWS_LOADER_ID = 12;

    public NewsFeedFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_news_feed,container,false);
        mNewsFeedRecyclerView = rootView.findViewById(R.id.news_recycler_view);
        mNewsFeedRecyclerView.setHasFixedSize(true);
        mNewsAdapter = new NewsAdapter(this);
        mNewsFeedRecyclerView.setAdapter(mNewsAdapter);
        getLoaderManager().initLoader(NEWS_LOADER_ID,null,this);
        return rootView;
    }

    @Override
    public void onNewsArticleClick(String articleUrl) {
        Intent openArticleIntent = new Intent(Intent.ACTION_VIEW);
        openArticleIntent.setData(Uri.parse(articleUrl));
        startActivity(openArticleIntent);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CursorLoader(getActivity(), AficionProvider.News.NEWS_CONTENT_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        mNewsAdapter.setNewsCursor(data);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mNewsAdapter.setNewsCursor(null);
    }

}

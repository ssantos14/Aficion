package com.example.android.aficion;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

/**
 * Created by Sylvana on 3/25/2018.
 */

public class NavigationBarFragment extends Fragment {
    OnFeedClickListener mCallback;

    public NavigationBarFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_navigation_bar,container,false);
        ImageButton newsFeedButton = rootView.findViewById(R.id.navigation_news);
        newsFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onFeedSelected(R.id.navigation_news);
            }
        });
        ImageButton scoresFeedButton = rootView.findViewById(R.id.navigation_scores);
        scoresFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onFeedSelected(R.id.navigation_scores);
            }
        });
        ImageButton highlightsFeedButton = rootView.findViewById(R.id.navigation_highlights);
        highlightsFeedButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onFeedSelected(R.id.navigation_highlights);
            }
        });
        ImageButton followingButton = rootView.findViewById(R.id.navigation_following);
        followingButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mCallback.onFeedSelected(R.id.navigation_following);
            }
        });
        return rootView;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        try{
            mCallback = (OnFeedClickListener) context;
        }catch(ClassCastException e){
            throw new ClassCastException(context.toString() + " must implement the OnFeedClickListener");
        }
    }

    public interface OnFeedClickListener{
        void onFeedSelected(int clickedItemId);
    }

}

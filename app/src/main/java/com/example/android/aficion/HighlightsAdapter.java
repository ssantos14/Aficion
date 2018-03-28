package com.example.android.aficion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Sylvana on 3/26/2018.
 */

public class HighlightsAdapter extends RecyclerView.Adapter<HighlightsAdapter.HighlightsViewHolder>{
    private String[] mHighlightsData;
    private int mNumberOfItems;

    public HighlightsAdapter(){}

    public void setHighlightsCursor(String[] highlightsData){
        mHighlightsData = highlightsData;
        mNumberOfItems = mHighlightsData.length;
        notifyDataSetChanged();
    }

    @Override
    public HighlightsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForItem = R.layout.highlights_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = layoutInflater.inflate(layoutIdForItem,parent,shouldAttachToParentImmediately);
        HighlightsViewHolder highlightsViewHolder = new HighlightsViewHolder(view);
        return highlightsViewHolder;
    }

    @Override
    public void onBindViewHolder(HighlightsViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberOfItems;
    }


    class HighlightsViewHolder extends RecyclerView.ViewHolder{
        TextView highlightsTitleTextView;
        public HighlightsViewHolder(View itemView){
            super(itemView);
            highlightsTitleTextView = itemView.findViewById(R.id.highlights_title_text_view);
        }
        void bind(int itemIndex){
            highlightsTitleTextView.setText(mHighlightsData[itemIndex]);
        }
    }
}

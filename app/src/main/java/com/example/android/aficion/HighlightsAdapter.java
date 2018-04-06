package com.example.android.aficion;

import android.content.Context;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Sylvana on 3/26/2018.
 */

public class HighlightsAdapter extends RecyclerView.Adapter<HighlightsAdapter.HighlightsViewHolder>{
    private Cursor mHighlightsData;
    private int mNumberOfItems;
    private final HighlightsAdapterOnClickHandler mClickHandler;

    public HighlightsAdapter(HighlightsAdapterOnClickHandler clickHandler){
        mClickHandler = clickHandler;
    }

    public void setHighlightsCursor(Cursor highlightsData){
        mHighlightsData = highlightsData;
        mNumberOfItems = mHighlightsData.getCount();
        notifyDataSetChanged();
    }

    public interface HighlightsAdapterOnClickHandler{
        void OnHighlightsVideoClick(String videoId);
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


    class HighlightsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView videoTitleTextView;
        ImageView videoThumbnail;
        public HighlightsViewHolder(View itemView){
            super(itemView);
            videoTitleTextView = itemView.findViewById(R.id.video_title_text_view);
            videoThumbnail = itemView.findViewById(R.id.video_thumbnail);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            mHighlightsData.moveToPosition(adapterPosition);
            String videoId = mHighlightsData.getString(1);
            mClickHandler.OnHighlightsVideoClick(videoId);
        }

        void bind(int itemIndex){
            mHighlightsData.moveToPosition(itemIndex);
            String title = mHighlightsData.getString(0);
            String thumbnailUrl = mHighlightsData.getString(2);
            Picasso.with(videoThumbnail.getContext()).load(thumbnailUrl).into(videoThumbnail);
            videoTitleTextView.setText(title);
        }
    }
}

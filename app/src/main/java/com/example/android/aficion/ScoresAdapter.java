package com.example.android.aficion;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Sylvana on 3/25/2018.
 */

public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ScoreViewHolder>{
    private String[] mScoresData;
    private int mNumberOfItems;

    public ScoresAdapter(){}

    public void setScoresCursor(String[] scoresData){
        mScoresData = scoresData;
        mNumberOfItems = mScoresData.length;
        notifyDataSetChanged();
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForItem = R.layout.scores_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = layoutInflater.inflate(layoutIdForItem,parent,shouldAttachToParentImmediately);
        ScoreViewHolder viewHolder = new ScoreViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ScoreViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberOfItems;
    }

    class ScoreViewHolder extends RecyclerView.ViewHolder{
        TextView GameTextView;
        public ScoreViewHolder(View itemView){
            super(itemView);
            GameTextView = itemView.findViewById(R.id.score_game_text_view);
        }
        void bind(int itemIndex){
            GameTextView.setText(mScoresData[itemIndex]);
        }
    }
}

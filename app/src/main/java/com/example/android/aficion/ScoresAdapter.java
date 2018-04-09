package com.example.android.aficion;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.aficion.data.AficionProvider;
import com.example.android.aficion.data.TeamsColumns;
import com.squareup.picasso.Picasso;

/**
 * Created by Sylvana on 3/25/2018.
 */

public class ScoresAdapter extends RecyclerView.Adapter<ScoresAdapter.ScoreViewHolder>{
    private Cursor mScoresData;
    private int mNumberOfItems;

    public ScoresAdapter(){}

    public void setScoresCursor(Cursor scoresData){
        if(scoresData != null){
            mScoresData = scoresData;
            mNumberOfItems = mScoresData.getCount();
            notifyDataSetChanged();
        }
    }

    @Override
    public ScoreViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForItem = R.layout.scores_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(layoutIdForItem,parent,false);
        return new ScoreViewHolder(view);
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
        TextView Team1TextView;
        TextView Team1ScoreTextView;
        TextView Team2TextView;
        TextView Team2ScoreTextView;
        public ScoreViewHolder(View itemView){
            super(itemView);
            Team1TextView = itemView.findViewById(R.id.team1_text_view);
            Team1ScoreTextView = itemView.findViewById(R.id.team1_score_text_view);
            Team2TextView = itemView.findViewById(R.id.team2_text_view);
            Team2ScoreTextView = itemView.findViewById(R.id.team2_score_text_view);
        }
        void bind(int itemIndex){
            mScoresData.moveToPosition(itemIndex);
            String team1 = mScoresData.getString(0);
            String team2 = mScoresData.getString(1);
            String team1Goals = mScoresData.getString(2);
            String team2Goals = mScoresData.getString(3);
            if(team1Goals.equals("null")){
                team1Goals = "0";
            }
            if(team2Goals.equals("null")){
                team2Goals = "0";
            }
            Team1TextView.setText(team1);
            Team1ScoreTextView.setText(team1Goals);
            Team2TextView.setText(team2);
            Team2ScoreTextView.setText(team2Goals);
        }
    }

}

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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ArticleViewHolder> {
    private int mNumberOfNewsArticles;
    private String[] mNewsData;

    public NewsAdapter(){}

    public void setNewsCursor(String[] newsCursor){
        mNewsData = newsCursor;
        mNumberOfNewsArticles = mNewsData.length;
        notifyDataSetChanged();
    }

    @Override
    public ArticleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForItem = R.layout.news_list_item;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = layoutInflater.inflate(layoutIdForItem,parent,shouldAttachToParentImmediately);
        ArticleViewHolder viewHolder = new ArticleViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ArticleViewHolder holder, int position) {
        holder.bind(position);
    }

    @Override
    public int getItemCount() {
        return mNumberOfNewsArticles;
    }


    class ArticleViewHolder extends RecyclerView.ViewHolder{
        TextView ArticleTitleTextView;
        public ArticleViewHolder(View itemView){
            super(itemView);
            ArticleTitleTextView = itemView.findViewById(R.id.news_item_title);
        }
        void bind(int articleIndex){
            ArticleTitleTextView.setText(mNewsData[articleIndex]);
        }
    }

}

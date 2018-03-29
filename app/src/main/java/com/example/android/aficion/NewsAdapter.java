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
 * Created by Sylvana on 3/25/2018.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ArticleViewHolder> {
    private int mNumberOfNewsArticles;
    private Cursor mNewsData;

    public NewsAdapter(){}

    public void setNewsCursor(Cursor newsCursor){
        mNewsData = newsCursor;
        mNumberOfNewsArticles = mNewsData.getCount();
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
        ImageView ArticleImageView;
        public ArticleViewHolder(View itemView){
            super(itemView);
            ArticleTitleTextView = itemView.findViewById(R.id.news_item_title);
            ArticleImageView = itemView.findViewById(R.id.news_item_image);
        }
        void bind(int articleIndex){
            mNewsData.moveToPosition(articleIndex);
            String title = mNewsData.getString(0);
            String imageUrl = mNewsData.getString(1);
            Picasso.with(ArticleImageView.getContext()).load(imageUrl).into(ArticleImageView);
            ArticleTitleTextView.setText(title);
        }
    }

}

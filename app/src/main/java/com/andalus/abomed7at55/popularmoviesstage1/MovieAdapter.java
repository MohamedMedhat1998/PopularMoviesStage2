package com.andalus.abomed7at55.popularmoviesstage1;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This Movie adapter is responsible for displaying movies in the recyclerView
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    //The data
    private Movie[] movies;

    private Context mContext;

    private AdapterClickListener mAdapterClickListener;

    /**
     * This constructor gives the adapter its initial data
     * @param moviesArray the movies array that contains data
     */
    public MovieAdapter(Movie[] moviesArray,AdapterClickListener clickListener){
        movies = moviesArray;
        mAdapterClickListener = clickListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        mContext = context;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.item,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Picasso.with(mContext)
                .load(movies[position].getPoster())
                .error(android.R.drawable.stat_notify_error)
                .into(holder.mainPoster);
    }

    @Override
    public int getItemCount() {
        return movies.length;
    }

    /**
     * This class represents the item in the recyclerView
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_poster_in_list)
        ImageView mainPoster;

        public MyViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            mainPoster.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();
                    mAdapterClickListener.onItemClicked(position);
                }
            });
        }
    }
}

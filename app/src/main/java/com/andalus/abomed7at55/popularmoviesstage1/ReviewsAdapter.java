package com.andalus.abomed7at55.popularmoviesstage1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This class is responsible for populating data to the recyclerView
 */

public class ReviewsAdapter extends RecyclerView.Adapter<ReviewsAdapter.ReviewsViewHolder>{
    //Data
    private MovieReview[] reviews;
    private AdapterClickListener mClickListener;

    /**
     * This constructor is used to set the adapter's data
     * @param reviewsData the array of reviews data
     */
    ReviewsAdapter(MovieReview[] reviewsData,AdapterClickListener clickListener){
        reviews = reviewsData;
        mClickListener = clickListener;
    }

    @Override
    public ReviewsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.review_item,parent,false);
        return new ReviewsViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ReviewsViewHolder holder, int position) {
        holder.tvAuthor.setText(reviews[position].getAuthor());
        holder.tvReview.setText(reviews[position].getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.length;
    }

    /**
     * This class represents the item in the recyclerView
     */
    public class ReviewsViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_author)
        TextView tvAuthor;
        @BindView(R.id.tv_review)
        TextView tvReview;
        public ReviewsViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mClickListener.onItemClicked(getAdapterPosition());
                }
            });
        }
    }
}

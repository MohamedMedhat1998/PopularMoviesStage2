package com.andalus.abomed7at55.popularmoviesstage1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * This class is responsible for populating data to the recyclerView
 */

public class VideosAdapter extends RecyclerView.Adapter<VideosAdapter.VideoViewHolder> {
    //Data
    private ArrayList<MovieVideo> videos;
    private AdapterClickListener listener;

    VideosAdapter(ArrayList<MovieVideo> videosData,AdapterClickListener clickListener){
        videos = videosData;
        listener = clickListener;
    }

    @Override
    public VideoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View v = inflater.inflate(R.layout.video_item,parent,false);
        return new VideoViewHolder(v);
    }

    @Override
    public void onBindViewHolder(VideoViewHolder holder, int position) {
        holder.tvTrailerLabel.setText(videos.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public class VideoViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.tv_trailer_label)
        TextView tvTrailerLabel;
        public VideoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onItemClicked(getAdapterPosition());
                }
            });
        }
    }
}

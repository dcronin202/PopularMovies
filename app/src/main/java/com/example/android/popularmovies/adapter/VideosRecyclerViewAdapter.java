package com.example.android.popularmovies.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.DetailActivity;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MovieVideos;

import java.util.ArrayList;

public class VideosRecyclerViewAdapter extends RecyclerView.Adapter<VideosRecyclerViewAdapter.ViewHolder> {

    private static final String LOG_TAG = VideosRecyclerViewAdapter.class.getSimpleName();

    //private ArrayList<String> mVideoTitle = new ArrayList<>();
    private ArrayList<MovieVideos> mMovieVideoTitle;
    private Activity mContext;

    /* public VideosRecyclerViewAdapter(Context mContext, ArrayList<String> mVideoTitle) {
        this.mVideoTitle = mVideoTitle;
        this.mContext = mContext;
    } */

    public VideosRecyclerViewAdapter(Activity mContext, ArrayList<MovieVideos> mMovieVideoTitle) {
        this.mMovieVideoTitle = mMovieVideoTitle;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_video_list_item, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int postion) {

        final MovieVideos movieVideo = mMovieVideoTitle.get(postion);

        // viewHolder.videoTitle.setText(mVideoTitle.get(postion));
        viewHolder.videoTitle.setText(movieVideo.getVideoName());

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Toast.makeText(mContext, mVideoTitle.get(postion), Toast.LENGTH_SHORT).show();
                Toast.makeText(mContext, movieVideo.getVideoName(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public int getItemCount() {
        //return mVideoTitle.size();
        if (mMovieVideoTitle == null) {
            return 0;
        } else {
            return mMovieVideoTitle.size();
        }
    }

    public void updateVideoList(ArrayList<MovieVideos> movieVideoList) {
        this.mMovieVideoTitle = movieVideoList;
        notifyDataSetChanged();
    }

    // TODO: Start here tomorrow & pass in the YouTube intent
    private void launchMovieVideo(MovieVideos videoAtPosition) {
        Intent intent = new Intent(this.mContext, DetailActivity.class);
        //

    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView videoTitle;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            videoTitle = itemView.findViewById(R.id.video_title);
            parentLayout = itemView.findViewById(R.id.video_list_layout);

        }
    }

}

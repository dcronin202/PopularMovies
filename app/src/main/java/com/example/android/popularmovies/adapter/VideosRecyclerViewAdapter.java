package com.example.android.popularmovies.adapter;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
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

    private ArrayList<MovieVideos> mMovieVideoTitle;
    private ArrayList<MovieVideos> mVideoId;
    private Activity mContext;


    public VideosRecyclerViewAdapter(Activity mContext, ArrayList<MovieVideos> mMovieVideoTitle, ArrayList<MovieVideos> mVideoId) {
        this.mMovieVideoTitle = mMovieVideoTitle;
        this.mVideoId = mVideoId;
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
    public void onBindViewHolder(ViewHolder viewHolder, final int position) {

        final MovieVideos movieVideo = mMovieVideoTitle.get(position);
        //final MovieVideos videoId = mVideoId.get(position);

        viewHolder.videoTitle.setText(movieVideo.getVideoName());

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, movieVideo.getVideoName(), Toast.LENGTH_SHORT).show();
                //launchMovieVideo(mContext, videoId.getVideoUrlKey());

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

    /* TODO: Pass in the YouTube intent
    private void launchMovieVideo(MovieVideos videoAtPosition) {
        Intent intent = new Intent(this.mContext, DetailActivity.class);
    } */

    public static void launchMovieVideo(Context context, String id) {
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + id));
        Intent webIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=" + id));

        try {
            context.startActivity(appIntent);

        } catch (ActivityNotFoundException ex) {
            context.startActivity(webIntent);
        }

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

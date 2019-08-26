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
import com.example.android.popularmovies.data.MovieReviews;
import com.example.android.popularmovies.data.MovieVideos;

import java.util.ArrayList;

public class ReviewsRecyclerViewAdapter extends RecyclerView.Adapter<ReviewsRecyclerViewAdapter.ViewHolder> {

    private static final String LOG_TAG = VideosRecyclerViewAdapter.class.getSimpleName();

    private ArrayList<MovieReviews> mMovieReviewAuthor;
    private ArrayList<MovieReviews> mMovieReviewContent;
    private Activity mContext;


    public ReviewsRecyclerViewAdapter(Activity mContext, ArrayList<MovieReviews> mMovieReviewAuthor, ArrayList<MovieReviews> mMovieReviewContent) {
        this.mMovieReviewAuthor = mMovieReviewAuthor;
        this.mMovieReviewContent = mMovieReviewContent;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ReviewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.movie_review_list_item, viewGroup, false);
        ReviewsRecyclerViewAdapter.ViewHolder viewHolder = new ReviewsRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewsRecyclerViewAdapter.ViewHolder viewHolder, final int postion) {

        final MovieReviews movieReviewsAuthor = mMovieReviewAuthor.get(postion);
        final MovieReviews movieReviewsContent = mMovieReviewContent.get(postion);

        viewHolder.reviewAuthor.setText(movieReviewsAuthor.getReviewAuthor());
        viewHolder.reviewContent.setText(movieReviewsContent.getReviewContent());

    }

    @Override
    public int getItemCount() {
        //return mVideoTitle.size();
        if (mMovieReviewAuthor == null) {
            return 0;
        } else {
            return mMovieReviewAuthor.size();
        }
    }

    public void updateReviewList(ArrayList<MovieReviews> movieReviewContent, ArrayList<MovieReviews> movieReviewAuthor) {
        this.mMovieReviewAuthor = movieReviewAuthor;
        this.mMovieReviewContent = movieReviewContent;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView reviewAuthor;
        TextView reviewContent;
        LinearLayout parentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            reviewAuthor = itemView.findViewById(R.id.review_author);
            reviewContent = itemView.findViewById(R.id.review_content);
            parentLayout = itemView.findViewById(R.id.review_list_layout);

        }
    }

}

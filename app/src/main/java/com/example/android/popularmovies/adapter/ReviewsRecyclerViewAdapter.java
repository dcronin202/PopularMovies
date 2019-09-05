package com.example.android.popularmovies.adapter;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.MovieReviews;

import java.util.ArrayList;

public class ReviewsRecyclerViewAdapter extends RecyclerView.Adapter<ReviewsRecyclerViewAdapter.ViewHolder> {

    private static final String LOG_TAG = VideosRecyclerViewAdapter.class.getSimpleName();

    private ArrayList<MovieReviews> mMovieReviewDetails;
    private Activity mContext;


    public ReviewsRecyclerViewAdapter(Activity mContext, ArrayList<MovieReviews> mMovieReviewDetails) {
        this.mMovieReviewDetails = mMovieReviewDetails;
        this.mContext = mContext;
    }


    @NonNull
    @Override
    public ReviewsRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item_movie_review, viewGroup, false);
        ReviewsRecyclerViewAdapter.ViewHolder viewHolder = new ReviewsRecyclerViewAdapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ReviewsRecyclerViewAdapter.ViewHolder viewHolder, final int postion) {

        final MovieReviews movieReviewsDetails = mMovieReviewDetails.get(postion);

        viewHolder.reviewAuthor.setText(movieReviewsDetails.getReviewAuthor());
        viewHolder.reviewContent.setText(movieReviewsDetails.getReviewContent());

    }

    @Override
    public int getItemCount() {
        //return mVideoTitle.size();
        if (mMovieReviewDetails == null) {
            return 0;
        } else {
            return mMovieReviewDetails.size();
        }
    }

    public void updateReviewList(ArrayList<MovieReviews> movieReviewContent) {
        this.mMovieReviewDetails = movieReviewContent;
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

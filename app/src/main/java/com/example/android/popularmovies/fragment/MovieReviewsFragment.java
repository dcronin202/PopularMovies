package com.example.android.popularmovies.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieReviews;

public class MovieReviewsFragment extends Fragment {

    private static final String TAG = "MovieReviewsFragment";

    private Movie movie;

    private MovieReviews reviews;

    private TextView movieReviewAuthor;

    private TextView movieReviewContent;

    private TextView movieReviewUrl;


    public MovieReviewsFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_reviews_fragment, container, false);

        movieReviewAuthor = view.findViewById(R.id.reviews_author);
        movieReviewContent = view.findViewById(R.id.reviews_content);
        movieReviewUrl = view.findViewById(R.id.reviews_url);

        updateReview(reviews);

        return view;

    }

    public void setReview(MovieReviews movieReviews) {
        this.reviews = reviews;
    }

    private void updateReview(MovieReviews movieReviews) {
        if (movie != null) {
            movieReviewAuthor.setText(reviews.getReviewAuthor());
            movieReviewContent.setText(reviews.getReviewContent());
            movieReviewUrl.setText(reviews.getReviewUrl());
        }
    }
}

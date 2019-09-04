package com.example.android.popularmovies.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieReviewsResponse {

    @SerializedName("id")
    public String movieId;

    @SerializedName("results")
    public List<MovieReviews> reviewResults;


    public List<MovieReviews> getReviewResults() {
        return reviewResults;
    }

    public void setReviewResults(List<MovieReviews> movieResults) {
        this.reviewResults = reviewResults;
    }


    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

}

package com.example.android.popularmovies.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieResponse {

    @SerializedName("results")
    public List<Movie> movieResults;

    public List<Movie> getMovieResults() {
        return movieResults;
    }

    public void setMovieResults(List<Movie> movieResults) {
        this.movieResults = movieResults;
    }

}

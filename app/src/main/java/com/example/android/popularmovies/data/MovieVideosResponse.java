package com.example.android.popularmovies.data;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MovieVideosResponse {

    @SerializedName("id")
    public String movieId;

    @SerializedName("results")
    public List<MovieVideos> videoResults;


    public List<MovieVideos> getVideoResults() {
        return videoResults;
    }

    public void setVideoResults(List<MovieVideos> videoResults) {
        this.videoResults = videoResults;
    }

    public String getMovieId() {
        return movieId;
    }

    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }

}

package com.example.android.popularmovies.data;

import com.google.gson.annotations.SerializedName;

public class MovieVideos {

    @SerializedName("name")
    private String videoName;

    @SerializedName("key")
    private String videoUrlKey;

    private String youTubeVideoPath = "https://www.youtube.com/watch?v=";


    public String getVideoName() {
        return videoName;
    }

    public void setVideoName(String videoName) {
        this.videoName = videoName;
    }

    public String getMovieVideos() {
        return youTubeVideoPath + videoUrlKey;
    }

    public void setMovieVideos(String movieVideo) {
        this.videoUrlKey = videoUrlKey;
    }

}

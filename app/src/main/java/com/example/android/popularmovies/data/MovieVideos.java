package com.example.android.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieVideos implements Parcelable {

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

    public void setMovieVideos(String videoUrlKey) {
        this.videoUrlKey = videoUrlKey;
    }

    public MovieVideos() {
    }

    public MovieVideos(String videoName, String videoUrlKey) {
        this.videoName = videoName;
        this.videoUrlKey = videoUrlKey;
    }


    // Code for Parcels
    private MovieVideos(Parcel p) {
        videoName = p.readString();
        videoUrlKey = p.readString();
    }

    @Override
    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(videoName);
        parcel.writeString(videoUrlKey);
    }

    public final static Parcelable.Creator<MovieVideos> CREATOR = new Parcelable.Creator<MovieVideos>() {
        @Override
        public MovieVideos createFromParcel(Parcel parcel) {
            return new MovieVideos(parcel);
        }

        @Override
        public MovieVideos[] newArray(int i) {
            return new MovieVideos[i];
        }
    };

}

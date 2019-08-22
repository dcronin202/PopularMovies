package com.example.android.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class Movie implements Parcelable {

    // Primary URL
    @SerializedName("id")
    private int movieId;

    @SerializedName("title")
    private String movieTitle;

    @SerializedName("poster_path")
    private String posterImage;

    @SerializedName("overview")
    private String plot;

    @SerializedName("vote_average")
    private double rating;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("popularity")
    private double popularity;

    private String moviePosterPath = "https://image.tmdb.org/t/p/w185/";


    // Movie Videos URL
    @SerializedName("name")
    private String videoName;

    @SerializedName("key")
    private String videoUrlKey;

    private String youTubeVideoPath = "https://www.youtube.com/watch?v=";


    public Movie() {
        }

    public Movie(String movieTitle, String posterImage, String plot, double rating, String releaseDate, double popularity) {
        this.movieTitle = movieTitle;
        this.posterImage = posterImage;
        this.plot = plot;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
    }

    // Main
    public int getMovieId() {
        return movieId;
    }

    public void setMovieId() {
        this.movieId = movieId;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getPosterImage() {
        return moviePosterPath + posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public double getRating () {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public double getPopularity() {
        return popularity;
    }

    public void setPopularity() {
        this.popularity = popularity;
    }

    // Videos
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

    // Code for Parcels
    private Movie(Parcel p) {
        movieId = p.readInt();
        movieTitle = p.readString();
        posterImage = p.readString();
        plot = p.readString();
        rating = p.readDouble();
        releaseDate = p.readString();
        popularity = p.readDouble();

        videoName = p.readString();
        videoUrlKey = p.readString();
        youTubeVideoPath = p.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(movieId);
        parcel.writeString(movieTitle);
        parcel.writeString(posterImage);
        parcel.writeString(plot);
        parcel.writeDouble(rating);
        parcel.writeString(releaseDate);
        parcel.writeDouble(popularity);

        parcel.writeString(videoName);
        parcel.writeString(videoUrlKey);
        parcel.writeString(youTubeVideoPath);

    }

    public final static Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel parcel) {
            return new Movie(parcel);
        }

        @Override
        public Movie[] newArray(int i) {
            return new Movie[i];
        }
    };

}

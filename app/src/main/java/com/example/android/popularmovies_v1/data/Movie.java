package com.example.android.popularmovies_v1.data;

import android.os.Parcel;
import android.os.Parcelable;


public class Movie implements Parcelable {

    private String movieTitle;
    private String originalTitle;
    private String posterImage;
    private String plot;
    private double rating;
    private String releaseDate;
    private double popularity;

    public Movie() {
        }

    public Movie(String movieTitle, String originalTitle, String posterImage, String plot, double rating, String releaseDate, double popularity) {
        this.movieTitle = movieTitle;
        this.originalTitle = originalTitle;
        this.posterImage = posterImage;
        this.plot = plot;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getOriginalTitle() {
        return originalTitle;
    }

    public void setOriginalTitle(String originalTitle) {
        this.originalTitle = originalTitle;
    }

    public String getPosterImage() {
        return posterImage;
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

    // Code for Parcels
    private Movie(Parcel p) {
        movieTitle = p.readString();
        originalTitle = p.readString();
        posterImage = p.readString();
        plot = p.readString();
        rating = p.readDouble();
        releaseDate = p.readString();
        popularity = p.readDouble();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(movieTitle);
        parcel.writeString(originalTitle);
        parcel.writeString(posterImage);
        parcel.writeString(plot);
        parcel.writeDouble(rating);
        parcel.writeString(releaseDate);
        parcel.writeDouble(popularity);

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

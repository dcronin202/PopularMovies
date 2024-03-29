package com.example.android.popularmovies.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "favorites")
public class Movie implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    @Expose
    private int movieId;

    @SerializedName("title")
    @Expose
    private String movieTitle;

    @SerializedName("poster_path")
    @Expose
    private String posterImage;

    @SerializedName("overview")
    @Expose
    private String overview;

    @SerializedName("vote_average")
    @Expose
    private double rating;

    @SerializedName("release_date")
    @Expose
    private String releaseDate;

    @SerializedName("popularity")
    @Expose
    private double popularity;

    @ColumnInfo(name = "is_favorite")
    @Expose
    private boolean isFavorite;

    @Ignore
    public Movie() {
        }

    @Ignore
    public Movie(String movieTitle, String posterImage, String overview, double rating, String releaseDate, double popularity) {
        this.movieTitle = movieTitle;
        this.posterImage = posterImage;
        this.overview = overview;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
    }

    public Movie(int movieId, String movieTitle, String posterImage, String overview, double rating, String releaseDate, double popularity, boolean isFavorite) {
        this.movieId = movieId;
        this.movieTitle = movieTitle;
        this.posterImage = posterImage;
        this.overview = overview;
        this.rating = rating;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.isFavorite = isFavorite;
    }

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
        String moviePosterPath = "https://image.tmdb.org/t/p/w185/";
        if (posterImage.contains(moviePosterPath)){
            return posterImage;

        } else {
            return moviePosterPath + posterImage;
        }
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String plot) {
        this.overview = overview;
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

    public boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }


    // Code for Parcels
    private Movie(Parcel p) {
        movieId = p.readInt();
        movieTitle = p.readString();
        posterImage = p.readString();
        overview = p.readString();
        rating = p.readDouble();
        releaseDate = p.readString();
        popularity = p.readDouble();
        int isFavoriteInt = p.readInt();
        if (isFavoriteInt == 0) {
            isFavorite = false;
        } else {
            isFavorite = true;
        }
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
        parcel.writeString(overview);
        parcel.writeDouble(rating);
        parcel.writeString(releaseDate);
        parcel.writeDouble(popularity);
        int isFavoriteInteger = 0;
        if (isFavorite) {
            isFavoriteInteger = 1;
        }
        parcel.writeInt(isFavoriteInteger);
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

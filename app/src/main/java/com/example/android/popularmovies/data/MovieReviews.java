package com.example.android.popularmovies.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class MovieReviews implements Parcelable {

    @SerializedName("author")
    private String reviewAuthor;

    @SerializedName("content")
    private String reviewContent;


    public String getReviewAuthor() {
        return reviewAuthor;
    }

    public void setReviewAuthor(String reviewAuthor) {
        this.reviewAuthor = reviewAuthor;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public MovieReviews() {
    }

    public MovieReviews(String reviewAuthor, String reviewContent) {
        this.reviewAuthor = reviewAuthor;
        this.reviewContent = reviewContent;

    }


    // Code for Parcels
    private MovieReviews(Parcel p) {
        reviewAuthor = p.readString();
        reviewContent = p.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(reviewAuthor);
        parcel.writeString(reviewContent);

    }

    public static final Parcelable.Creator<MovieReviews> CREATOR = new Parcelable.Creator<MovieReviews>() {
        @Override
        public MovieReviews createFromParcel(Parcel parcel) {
            return new MovieReviews(parcel);
        }

        @Override
        public MovieReviews[] newArray(int i) {
            return new MovieReviews[i];
        }
    };
}

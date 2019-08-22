package com.example.android.popularmovies.data;

import com.google.gson.annotations.SerializedName;

public class MovieReviews {

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

}

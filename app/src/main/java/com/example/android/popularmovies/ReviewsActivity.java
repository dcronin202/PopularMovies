package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.popularmovies.data.MovieReviews;

import java.util.ArrayList;

public class ReviewsActivity extends AppCompatActivity {

    public static final String REVIEW_DETAILS = "reviews";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        setTitle("Reviews");

        Intent intent = getIntent();

        ArrayList<MovieReviews> reviewsList = intent.getParcelableArrayListExtra(REVIEW_DETAILS);
        TextView textView = (TextView) findViewById(R.id.review_activity_textview);
        if (reviewsList != null && reviewsList.size() > 0) {
            textView.setText(reviewsList.get(0).getReviewContent());
        }

    }
}

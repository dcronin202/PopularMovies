package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class ReviewsActivity extends AppCompatActivity {

    public static final String REVIEW_DETAILS = "reviews";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);

        setTitle("Reviews");

        Intent intent = getIntent();

        TextView textView = (TextView) findViewById(R.id.movie_review_result);

    }
}
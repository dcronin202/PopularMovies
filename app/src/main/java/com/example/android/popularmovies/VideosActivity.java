package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class VideosActivity extends AppCompatActivity {

    public static final String VIDEO_DETAILS = "video";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        setTitle("Videos");

        Intent intent = getIntent();

        String message = intent.getStringExtra("Videos");
        TextView textView = (TextView) findViewById(R.id.video_activity_textview);
        textView.setText(message);
    }
}

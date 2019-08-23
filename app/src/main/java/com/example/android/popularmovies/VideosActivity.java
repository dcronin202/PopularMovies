package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.android.popularmovies.data.MovieVideos;

import java.util.ArrayList;
import java.util.List;

public class VideosActivity extends AppCompatActivity {

    public static final String VIDEO_DETAILS = "video";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videos);

        setTitle("Videos");

        Intent intent = getIntent();

        ArrayList<MovieVideos> videoList = intent.getParcelableArrayListExtra(VIDEO_DETAILS);
        TextView textView = (TextView) findViewById(R.id.video_activity_textview);
        if (videoList != null && videoList.size() > 0) {
            // TODO: Will need to iterate through array of videos
            textView.setText(videoList.get(0).getVideoName());
        }
    }
}

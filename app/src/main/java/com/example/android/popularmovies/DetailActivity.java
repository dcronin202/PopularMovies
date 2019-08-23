package com.example.android.popularmovies;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.adapter.RecyclerViewAdapter;
import com.example.android.popularmovies.data.JsonMovieApi;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieResponse;
import com.example.android.popularmovies.data.MovieReviews;
import com.example.android.popularmovies.data.MovieReviewsResponse;
import com.example.android.popularmovies.data.MovieVideos;
import com.example.android.popularmovies.data.MovieVideosResponse;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    public static final String MOVIE_DETAILS = "movie";

    private Movie movieDetails;

    private ArrayList<MovieVideos> videoDetails;

    private ArrayList<MovieReviews> reviewDetails;

    private ArrayList<String> mTestVideoTitles = new ArrayList<>();

    private static int movieId;

    private JsonMovieApi jsonMovieApi;

    private TextView movieReviewResult;

    private TextView movieVideoResult;

    // TODO: API KEY GOES HERE
    private static final String apiKey = "301ade02190b07969335c116b1456868";

    // URL for movie data
    private static final String MOVIE_URL = "http://api.themoviedb.org/3/movie/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        movieDetails = intent.getParcelableExtra(MOVIE_DETAILS);

        if (movieDetails != null) {

            populateMovieDetails(movieDetails);

            setTitle(R.string.detail_header);

        } else {
            closeOnError();
        }

        movieReviewResult = findViewById(R.id.movie_review_result);
        movieVideoResult = findViewById(R.id.movie_video_result);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MOVIE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonMovieApi = retrofit.create(JsonMovieApi.class);

        getMovieReviews();
        getMovieVideos();


        Button videosButton = (Button) findViewById(R.id.button_videos);
        videosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent videoIntent = new Intent(DetailActivity.this, VideosActivity.class);
                videoIntent.putParcelableArrayListExtra(VideosActivity.VIDEO_DETAILS, videoDetails);
                startActivity(videoIntent);
            }
        });

        Button reviewsButton = (Button) findViewById(R.id.button_reviews);
        reviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reviewIntent = new Intent(DetailActivity.this, ReviewsActivity.class);
                reviewIntent.putExtra(ReviewsActivity.REVIEW_DETAILS, reviewDetails);
                startActivity(reviewIntent);
            }
        });

        // RecyclerView method
        videoTitleList();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void closeOnError() {
        finish();
    }

    // Get current movie ID
    private int populateMovieId(Movie movie) {
        int movieId = movie.getMovieId();
        return movieId;
    }

    private void populateMovieDetails(Movie movie) {

        // Movie Title
        TextView movieTitle = findViewById(R.id.movie_title);
        movieTitle.setText(movie.getMovieTitle());

        // Movie Poster
        ImageView moviePoster = findViewById(R.id.movie_poster);
        Picasso.get()
                .load(movie.getPosterImage())
                .into(moviePoster);

        // Year - reformatted original String input to display MMMM yyyy format
        String releaseDate = movie.getReleaseDate();
        SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date newReleaseDate = null;
        try {
            newReleaseDate = (Date) oldDateFormat.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newDateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
        String finalDate = newDateFormat.format(newReleaseDate);

        TextView yearReleased = findViewById(R.id.release_date);
        yearReleased.setText(finalDate);

        // Avg Rating
        String movieRating = String.valueOf(movie.getRating());
        String avgMovieRating = movieRating + getString(R.string.out_of_ten);

        TextView averageRating = findViewById(R.id.average_rating);
        averageRating.setText(avgMovieRating);

        // Plot Overview
        TextView movieOverview = findViewById(R.id.movie_overview);
        movieOverview.setText(movie.getOverview());

    }

    private void getMovieReviews() {

        movieId = populateMovieId(movieDetails);

        Call<MovieReviewsResponse> call = jsonMovieApi.getMovieReviews(movieId, apiKey);

        call.enqueue(new Callback<MovieReviewsResponse>() {
            @Override
            public void onResponse(Call<MovieReviewsResponse> call, Response<MovieReviewsResponse> response) {
                onMovieReviewResponseReceived(response);
                // TODO: Add a string message "Currently no reviews for this movie" if no data is returned
            }

            @Override
            public void onFailure(Call<MovieReviewsResponse> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });

    }

    private void getMovieVideos() {

        movieId = populateMovieId(movieDetails);

        Call<MovieVideosResponse> call = jsonMovieApi.getMovieVideos(movieId, apiKey);

        call.enqueue(new Callback<MovieVideosResponse>() {
            @Override
            public void onResponse(Call<MovieVideosResponse> call, Response<MovieVideosResponse> response) {
                onMovieVideoResponseReceived(response);
                // TODO: Add a string message "Currently no videos for this movie" if no data is returned
            }

            @Override
            public void onFailure(Call<MovieVideosResponse> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });

    }

    private void onMovieReviewResponseReceived(Response<MovieReviewsResponse> response) {

        if (response.isSuccessful()) {

            MovieReviewsResponse movieReviewsResponse = response.body();
            reviewDetails = (ArrayList<MovieReviews>) movieReviewsResponse.getReviewResults();
            for (MovieReviews movieReview : reviewDetails) {
                String content = "";
                content += "Author: " + movieReview.getReviewAuthor() + "\n";
                content += "Content: " + movieReview.getReviewContent() + "\n";

                movieReviewResult.append(content);
            }

        } else {
            Log.e(LOG_TAG, "Code: " + response.code());

        }

    }

    private void onMovieVideoResponseReceived(Response<MovieVideosResponse> response) {

        if (response.isSuccessful()) {

            MovieVideosResponse movieVideoResponse = response.body();
            videoDetails = (ArrayList<MovieVideos>) movieVideoResponse.getVideoResults();
            for (MovieVideos movieVideo : videoDetails) {
                String content = "";
                content += "Name: " + movieVideo.getVideoName() + "\n";
                content += "URL: " + movieVideo.getMovieVideos() + "\n\n";

                movieVideoResult.append(content);
            }

        } else {
            Log.e(LOG_TAG, "Code: " + response.code());

        }

    }

    private void videoTitleList() {
        mTestVideoTitles.add("Official Trailer");
        mTestVideoTitles.add("Official New Trailer");
        mTestVideoTitles.add("John Wick: Chapter 3 - Parabellum (2019) Clip “Director Conversation” - Keanu Reeves");
        mTestVideoTitles.add("John Wick: Chapter 3 – Parabellum (2019 Movie) Official TV Spot “Guns” – Keanu Reeves, Halle Berry");
        mTestVideoTitles.add("John Wick: Chapter 3 – Parabellum (2019 Movie) Official TV Spot “Bounty” – Keanu Reeves, Halle Berry");
        mTestVideoTitles.add("John Wick: Chapter 3 - Parabellum (2019) Official TV Spot “Watching” - Keanu Reeves, Halle Berry");
        mTestVideoTitles.add("John Wick: Chapter 3 - Parabellum (2019 Movie) Official Clip “Taxi” – Keanu Reeves, Halle Berry");
        mTestVideoTitles.add("John Wick: Chapter 3 - Parabellum (2019) Official Clip “Management” – Keanu Reeves, Halle Berry");
        mTestVideoTitles.add("John Wick: Chapter 3 - Parabellum (2019) Official Behind the Scenes “Art of Action” – Keanu Reeves");
        mTestVideoTitles.add("John Wick: Chapter 3 - Parabellum (2019 Movie) “Happy National Puppy Day” - Keanu Reeves");

        initRecyclerView();

    }

    private void initRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview);
        RecyclerViewAdapter adapter = new RecyclerViewAdapter(this, mTestVideoTitles);
        recyclerView.setAdapter(adapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}

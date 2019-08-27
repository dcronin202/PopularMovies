package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.adapter.VideosRecyclerViewAdapter;
import com.example.android.popularmovies.adapter.ReviewsRecyclerViewAdapter;
import com.example.android.popularmovies.data.JsonMovieApi;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieReviews;
import com.example.android.popularmovies.data.MovieReviewsResponse;
import com.example.android.popularmovies.data.MovieVideos;
import com.example.android.popularmovies.data.MovieVideosResponse;
import com.example.android.popularmovies.database.MovieFavoritesDatabase;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

    private static int movieId;

    private JsonMovieApi jsonMovieApi;

    private VideosRecyclerViewAdapter videoAdapter;
    private ReviewsRecyclerViewAdapter reviewAdapter;

    private MovieFavoritesDatabase favoritesDatabase;

    // TODO: API KEY GOES HERE
    private static final String apiKey = " ";

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


        jsonMovieApi = getRetrofitInstance().create(JsonMovieApi.class);

        /* Button videosButton = (Button) findViewById(R.id.button_videos);
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
        }); */

        // RecyclerView method
        videoTitleList();
        movieReviewList();

    }

    private static Retrofit getRetrofitInstance() {
        return new Retrofit.Builder()
                .baseUrl(MOVIE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
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


    private void onMovieReviewResponseReceived(Response<MovieReviewsResponse> response) {

        if (response.isSuccessful()) {

            MovieReviewsResponse movieReviewsResponse = response.body();
            ArrayList<MovieReviews> reviews = (ArrayList<MovieReviews>) movieReviewsResponse.getReviewResults();
            reviewAdapter.updateReviewList(reviews);

        } else {
            Log.e(LOG_TAG, "Code: " + response.code());

        }

    }

        private void onMovieVideoResponseReceived(Response<MovieVideosResponse> response) {

        if (response.isSuccessful()) {

            MovieVideosResponse movieVideoResponse = response.body();
            ArrayList<MovieVideos> movieVideos = (ArrayList<MovieVideos>) movieVideoResponse.getVideoResults();
            videoAdapter.updateVideoList(movieVideos);

        } else {
            Log.e(LOG_TAG, "Code: " + response.code());

        }

    }

    private void videoTitleList() {

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

        initVideoRecyclerView();

    }

    private void movieReviewList() {

            movieId = populateMovieId(movieDetails);

            Call <MovieReviewsResponse> call = jsonMovieApi.getMovieReviews(movieId, apiKey);

            call.enqueue(new Callback<MovieReviewsResponse>() {
                @Override
                public void onResponse(Call<MovieReviewsResponse> call, Response<MovieReviewsResponse> response) {
                    onMovieReviewResponseReceived(response);
                }

                @Override
                public void onFailure(Call<MovieReviewsResponse> call, Throwable t) {
                    Log.e(LOG_TAG, t.getMessage());
                }
            });

            initReviewRecyclerView();
    }

    private void initVideoRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_videos);
        videoAdapter = new VideosRecyclerViewAdapter(this, videoDetails);
        recyclerView.setAdapter(videoAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void initReviewRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_reviews);
        reviewAdapter = new ReviewsRecyclerViewAdapter(this, reviewDetails);
        recyclerView.setAdapter(reviewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


}

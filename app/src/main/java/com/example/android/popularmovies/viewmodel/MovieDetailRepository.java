package com.example.android.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.os.AsyncTask;
import android.util.Log;

import com.example.android.popularmovies.data.JsonMovieApi;
import com.example.android.popularmovies.data.MovieReviews;
import com.example.android.popularmovies.data.MovieReviewsResponse;
import com.example.android.popularmovies.data.MovieVideos;
import com.example.android.popularmovies.data.MovieVideosResponse;
import com.example.android.popularmovies.database.MovieDao;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.database.MovieFavoritesDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieDetailRepository {

    private static final String LOG_TAG = MovieDetailRepository.class.getSimpleName();
    // TODO: API KEY GOES HERE
    private static final String apiKey = " ";
    private static final String MOVIE_URL = "http://api.themoviedb.org/3/movie/";
    private static int movieId;
    private JsonMovieApi jsonMovieApi;

    private MovieDao movieDao;
    private MutableLiveData<ArrayList<MovieVideos>> movieVideos;
    private MutableLiveData<ArrayList<MovieReviews>> movieReviews;

    MovieDetailRepository(Application application) {
        MovieFavoritesDatabase database = MovieFavoritesDatabase.getInstance(application);
        movieDao = database.movieDao();
        movieVideos = new MutableLiveData<ArrayList<MovieVideos>>();
        movieReviews = new MutableLiveData<ArrayList<MovieReviews>>();

    }

    public LiveData<ArrayList<MovieVideos>> getVideoDetails() {
        return movieVideos;
    }

    public LiveData<ArrayList<MovieReviews>> getReviewDetails() {
        return movieReviews;
    }


    public void insertMovie(Movie movie) {
        new InsertFavoritesAsyncTask().execute(movie);
    }

    public void removeMovie(Movie movie) {
        new RemoveFavoritesAsyncTask().execute(movie);
    }


    private class InsertFavoritesAsyncTask extends AsyncTask<Movie, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Movie... movies) {

            movieDao.insertMovie(movies[0]);
            return null;
        }
    }

    private class RemoveFavoritesAsyncTask extends AsyncTask<Movie, Void, List<Movie>> {

        @Override
        protected List<Movie> doInBackground(Movie... movies) {

            movieDao.removeMovie(movies[0]);
            return null;
        }
    }

    // Get current movie ID
    private int populateMovieId(Movie movie) {
        int movieId = movie.getMovieId();
        return movieId;
    }

    //  RETROFIT Methods //

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MOVIE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private void getJsonMovieApi() {
        jsonMovieApi = retrofit.create(JsonMovieApi.class);
    }

    // Method for retrieving Movie Videos
    public void callVideoList(Movie movie) {
        if (jsonMovieApi == null) {
            getJsonMovieApi();
        }
        movieId = populateMovieId(movie);

        Call<MovieVideosResponse> call = jsonMovieApi.getMovieVideos(movieId, apiKey);

        call.enqueue(new Callback<MovieVideosResponse>() {
            @Override
            public void onResponse(Call<MovieVideosResponse> call, Response<MovieVideosResponse> response) {
                onVideoResponseReceived(response);
                // TODO: Add a message "Currently no videos for this movie" if no data is returned and do the same for Reviews
            }

            @Override
            public void onFailure(Call<MovieVideosResponse> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });

    }

    // Method for retrieving Movie Reviews
    public void callReviewList(Movie movie) {
        if (jsonMovieApi == null) {
            getJsonMovieApi();
        }
        movieId = populateMovieId(movie);

        Call<MovieReviewsResponse> call = jsonMovieApi.getMovieReviews(movieId, apiKey);

        call.enqueue(new Callback<MovieReviewsResponse>() {
            @Override
            public void onResponse(Call<MovieReviewsResponse> call, Response<MovieReviewsResponse> response) {
                onReviewResponseReceived(response);

            }

            @Override
            public void onFailure(Call<MovieReviewsResponse> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());
            }
        });

    }

    // Method for receiving a Video response object and displaying its data
    private void onVideoResponseReceived(Response<MovieVideosResponse> response) {

        if (response.isSuccessful()) {

            MovieVideosResponse movieVideosResponse = response.body();
            ArrayList<MovieVideos> videos = (ArrayList<MovieVideos>) movieVideosResponse.getVideoResults();
            movieVideos.postValue(videos);

        } else {
            movieVideos.postValue(new ArrayList<MovieVideos>());
            Log.e(LOG_TAG, "Code: " + response.code());

        }
    }

    // Method for receiving a Review response object and displaying its data
    private void onReviewResponseReceived(Response<MovieReviewsResponse> response) {

        if (response.isSuccessful()) {

            MovieReviewsResponse movieReviewsResponse = response.body();
            ArrayList<MovieReviews> reviews = (ArrayList<MovieReviews>) movieReviewsResponse.getReviewResults();
            movieReviews.postValue(reviews);

        } else {
            movieReviews.postValue(new ArrayList<MovieReviews>());
            Log.e(LOG_TAG, "Code: " + response.code());

        }
    }

}

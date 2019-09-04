package com.example.android.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.util.Log;

import com.example.android.popularmovies.data.JsonMovieApi;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieResponse;
import com.example.android.popularmovies.database.MovieDao;
import com.example.android.popularmovies.database.MovieFavoritesDatabase;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MovieMainRepository {

    private static final String LOG_TAG = MovieMainRepository.class.getSimpleName();
    // TODO: API KEY GOES HERE
    private static final String apiKey = " ";
    private static final String MOVIE_URL = "http://api.themoviedb.org/3/movie/";
    private JsonMovieApi jsonMovieApi;

    private MovieDao movieDao;
    private MutableLiveData<List<Movie>> movies;

    MovieMainRepository(Application application) {
        MovieFavoritesDatabase database = MovieFavoritesDatabase.getInstance(application);
        movieDao = database.movieDao();
        movies = new MutableLiveData<>();

    }

    public LiveData<List<Movie>> getAllMovies() {
        return movies;
    }


    public void getFavoritesList(LifecycleOwner owner) {
        movieDao.loadAllMovies().observe(owner, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> databaseMovies) {
                if (databaseMovies != null) {
                    movies.postValue(databaseMovies);
                }
            }
        });
    }


    // RETROFIT Methods //

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MOVIE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private void getJsonMovieApi() {
        jsonMovieApi = retrofit.create(JsonMovieApi.class);
    }

    // Method for retrieving Popular movies
    public void callPopularMovies(final LifecycleOwner owner) {
        if (jsonMovieApi == null) {
            getJsonMovieApi();
        }

        Call<MovieResponse> call = jsonMovieApi.getPopularMovies(apiKey);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                onMovieResponseReceived(response, owner);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                movies.postValue(new ArrayList<Movie>());
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }

    // Method for retrieving Top Rated movies
    public void callTopRatedMovies(final LifecycleOwner owner) {
        if (jsonMovieApi == null) {
            getJsonMovieApi();
        }

        Call<MovieResponse> call = jsonMovieApi.getTopRatedMovies(apiKey);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                onMovieResponseReceived(response, owner);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                movies.postValue(new ArrayList<Movie>());
                Log.e(LOG_TAG, t.getMessage());
            }
        });
    }

    // Method for receiving a Movie response object and displaying its data
    private void onMovieResponseReceived(Response<MovieResponse> response, LifecycleOwner owner) {

        if (response.isSuccessful()) {

            MovieResponse movieResponse = response.body();
            final List<Movie> movieResults = movieResponse.getMovieResults();
            movieDao.loadAllMovies().observe(owner, new Observer<List<Movie>>() {
                @Override
                public void onChanged(@Nullable List<Movie> databaseMovies) {
                    if (databaseMovies != null) {
                        // Loop through the network movies to find the matching one in the DB
                        for (int indexForNetworkResult = 0; indexForNetworkResult < movieResults.size(); indexForNetworkResult++) {
                            // Loop through DB movies to see if its the same one
                            Movie movieFromNetwork = movieResults.get(indexForNetworkResult);
                            for (int indexForDbResult = 0; indexForDbResult < databaseMovies.size(); indexForDbResult++) {
                                Movie movieFromDb = databaseMovies.get(indexForDbResult);
                                if (movieFromDb.getMovieId() == movieFromNetwork.getMovieId()) {
                                    movieFromNetwork.setIsFavorite(true);
                                }
                            }
                        }
                    }
                }
            });
            movies.postValue(movieResults);


        } else {
            movies.postValue(new ArrayList<Movie>());
            Log.e(LOG_TAG, "Code: " + response.code());

        }
    }

}

package com.example.android.popularmovies.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies.data.Movie;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private static final String LOG_TAG = MovieViewModel.class.getSimpleName();

    private MovieRepository repository;
    private LiveData<List<Movie>> movies;


    public MovieViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieRepository(application);
        Log.d(LOG_TAG, "Actively retrieving the movies from the database.");
        movies = repository.getAllMovies();

    }

    public LiveData<List<Movie>> getMovies() {
        return repository.getAllMovies();
    }

    public void getPopularMovies() {
        repository.callPopularMovies();
    }

    public void getTopRatedMovies() {
        repository.callTopRatedMovies();
    }

}

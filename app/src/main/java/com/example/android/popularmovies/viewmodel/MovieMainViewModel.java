package com.example.android.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies.data.Movie;

import java.util.List;

public class MovieMainViewModel extends AndroidViewModel {

    private static final String LOG_TAG = MovieMainViewModel.class.getSimpleName();

    private MovieMainRepository repository;


    public MovieMainViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieMainRepository(application);
        Log.d(LOG_TAG, "Actively retrieving the movies from the database.");

    }

    public LiveData<List<Movie>> getMovies() {
        return repository.getAllMovies();
    }


    public void getFavorites(LifecycleOwner owner) {
        repository.getFavoritesList(owner);
    }

    public void getPopularMovies() {
        repository.callPopularMovies();
    }

    public void getTopRatedMovies() {
        repository.callTopRatedMovies();
    }

}

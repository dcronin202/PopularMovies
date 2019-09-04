package com.example.android.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.LiveData;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.android.popularmovies.data.Movie;

import java.util.List;

public class MovieMainViewModel extends AndroidViewModel {

    private static final String LOG_TAG = MovieMainViewModel.class.getSimpleName();

    private MovieMainRepository repository;

    private static final String viewState = "view_state";
    private static final String popularView = "popular";
    private static final String topRatedView = "top_rated";
    private static final String favoritesView = "favorites";
    private SharedPreferences mPreferences;


    public MovieMainViewModel(@NonNull Application application) {
        super(application);
        repository = new MovieMainRepository(application);
        Log.d(LOG_TAG, "Actively retrieving the movies from the database.");

    }

    public LiveData<List<Movie>> getMovies() {
        return repository.getAllMovies();
    }


    public void loadMovies(LifecycleOwner owner) {
        String showViewState = mPreferences.getString(viewState, "default");
        if (showViewState.equals(favoritesView)) {
            getFavorites(owner);
        } else if (showViewState.equals(topRatedView)) {
            getTopRatedMovies(owner);
        } else {
            getPopularMovies(owner);
        }
    }

    public void getFavorites(LifecycleOwner owner) {
        repository.getFavoritesList(owner);
        saveViewState(favoritesView);
    }

    public void getPopularMovies(LifecycleOwner owner) {
        repository.callPopularMovies(owner);
        saveViewState(popularView);
    }

    public void getTopRatedMovies(LifecycleOwner owner) {
        repository.callTopRatedMovies(owner);
        saveViewState(topRatedView);
    }


    public void setupSharedPref(SharedPreferences sharedPreferences) {
        this.mPreferences = sharedPreferences;
    }

    private void saveViewState(String newState) {
        String currentViewState = newState;
        SharedPreferences.Editor editor = mPreferences.edit();
        editor.putString(viewState, currentViewState);
        editor.commit();
    }


}

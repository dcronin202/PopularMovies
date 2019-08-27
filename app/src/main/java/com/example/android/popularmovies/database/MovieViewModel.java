package com.example.android.popularmovies.database;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.example.android.popularmovies.data.Movie;

import java.util.List;

public class MovieViewModel extends AndroidViewModel {

    private MovieRepository mRepository;

    private LiveData<List<Movie>> mAllMovies;


    public MovieViewModel(Application application) {
        super(application);
        mRepository = new MovieRepository(application);
        mAllMovies = mRepository.getAllMovies();

    }

    public LiveData<List<Movie>> getAllMovies() {

        // May need to call movies from Retrofit here
        //

        return mAllMovies;
    }

    public void insert(Movie movie) {
        mRepository.insert(movie);
    }

}

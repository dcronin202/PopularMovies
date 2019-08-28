package com.example.android.popularmovies.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieReviews;
import com.example.android.popularmovies.data.MovieVideos;

import java.util.ArrayList;

public class MovieDetailViewModel extends AndroidViewModel {

    private static final String LOG_TAG = MovieDetailViewModel.class.getSimpleName();

    private MovieDetailRepository movieDetailRepository;

    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        movieDetailRepository = new MovieDetailRepository(application);

    }

    public LiveData<ArrayList<MovieVideos>> getVideos() {
        return movieDetailRepository.getVideoDetails();
    }

    public LiveData<ArrayList<MovieReviews>> getReviews() {
        return movieDetailRepository.getReviewDetails();
    }

    public void getVideoList(Movie movieId) {
        movieDetailRepository.callVideoList(movieId);
    }

    public void getReviewList(Movie movieId) {
        movieDetailRepository.callReviewList(movieId);
    }

}

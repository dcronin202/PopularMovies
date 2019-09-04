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
    private Movie movieDetails;


    public MovieDetailViewModel(@NonNull Application application) {
        super(application);
        movieDetailRepository = new MovieDetailRepository(application);

    }

    public void setMovieDetails(Movie movie) {
        this.movieDetails = movie;
    }

    // Favorites
    public void addFavorite(Movie movie) {
        movie.setIsFavorite(true);
        movieDetailRepository.insertMovie(movie);
    }

    public void removeFavorite(Movie movie) {
        movieDetailRepository.removeMovie(movie);
    }

    // Videos
    public LiveData<ArrayList<MovieVideos>> getVideos() {
        return movieDetailRepository.getVideoDetails();
    }

    public void getVideoList(Movie movieId) {
        movieDetailRepository.callVideoList(movieId);
    }

    // Reviews
    public LiveData<ArrayList<MovieReviews>> getReviews() {
        return movieDetailRepository.getReviewDetails();
    }

    public void getReviewList() {
        movieDetailRepository.callReviewList(movieDetails);
    }

}

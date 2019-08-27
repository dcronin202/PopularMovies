package com.example.android.popularmovies.database;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;

import com.example.android.popularmovies.data.Movie;

import java.util.List;


public class MovieRepository {

    private MovieDao mMovieDao;
    private LiveData<List<Movie>> mAllMovies;

    MovieRepository(Application application) {
        MovieFavoritesDatabase database = MovieFavoritesDatabase.getInstance(application);
        mMovieDao = database.movieDao();
        mAllMovies = mMovieDao.loadAllMovies();

    }

    LiveData<List<Movie>> getAllMovies() {
        return mAllMovies;
    }


    // Replace this section w/ Retrofit code
    public void insert(Movie movie) {
        new insertAsyncTask(mMovieDao).execute(movie);
    }

    private static class insertAsyncTask extends AsyncTask<Movie, Void, Void> {

        private MovieDao mAsyncMovieDao;

        insertAsyncTask(MovieDao dao) {
            mAsyncMovieDao = dao;
        }

        @Override
        protected Void doInBackground(final Movie... params) {
            mAsyncMovieDao.insertMovie(params[0]);
            return null;
        }

    }

}

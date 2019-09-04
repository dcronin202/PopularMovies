package com.example.android.popularmovies.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.Movie;

public class MovieDetailsFragment extends Fragment {


    private static final String LOG_TAG = MovieDetailsFragment.class.getSimpleName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_info_fragment, container, false);

        return view;

    }

    /* private TextView overview;

    private Movie movie;


    public MovieDetailsFragment() {
        super();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_info_fragment, container, false);

        overview = view.findViewById(R.id.movie_info);

        updateOverview(movie);

        return view;

    }

    // Setter for the Movie object to load the details of the movie in the onCreateView method.
    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    private void updateOverview(Movie movie) {
        if (movie != null) {
            overview.setText(movie.getPlot());
        }
    } */

}

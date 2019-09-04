package com.example.android.popularmovies.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.android.popularmovies.DetailActivity;
import com.example.android.popularmovies.adapter.MovieAdapter;
import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.viewmodel.MovieMainViewModel;

import java.util.ArrayList;
import java.util.List;

/*
* A fragment containing the list view of movie posters
*/
public class MainActivityFragment extends Fragment {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    private MovieAdapter movieAdapter;

    private GridView gridView;
    private TextView errorMessage;

    private MovieMainViewModel viewModel;


    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Set up MovieMainViewModel
        setupViewModel();

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());

        // Get a reference to the ListView and attach this adapter to it
        gridView = rootView.findViewById(R.id.movie_grid);
        gridView.setAdapter(movieAdapter);

        // Get a reference to the error message
        errorMessage = rootView.findViewById(R.id.error_message_display);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie moviePosition = movieAdapter.getItem(position);
                launchMovieDetailActivity(moviePosition);
            }
        });

        return rootView;

    }

    private void launchMovieDetailActivity(Movie movieAtPosition) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIE_DETAILS, movieAtPosition);
        startActivity(intent);
    }

    private void setupViewModel() {
        viewModel = ViewModelProviders.of(getActivity()).get(MovieMainViewModel.class);
        viewModel.setupSharedPref(getActivity().getSharedPreferences("movie-app", Context.MODE_PRIVATE));
        viewModel.getMovies().observe(this, new Observer<List<Movie>>() {
            @Override
            public void onChanged(@Nullable List<Movie> movies) {
                if (movies.size() > 0) {
                    // Clear any previous data
                    movieAdapter.clear();

                    // Add new movie data
                    Log.d(LOG_TAG, "Updated list of movies from LiveData in ViewModel");
                    movieAdapter.addAll(movies);

                    gridView.setVisibility(View.VISIBLE);
                    errorMessage.setVisibility(View.GONE);

                } else {
                    errorMessage.setVisibility(View.VISIBLE);
                    gridView.setVisibility(View.GONE);

                }
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        viewModel.loadMovies(this);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public void sortByPopularity() {
        viewModel.getPopularMovies(this);
    }

    public void sortByTopRated() {
        viewModel.getTopRatedMovies(this);
    }

    public void sortByFavorites() {
        viewModel.getFavorites(this);
    }


}

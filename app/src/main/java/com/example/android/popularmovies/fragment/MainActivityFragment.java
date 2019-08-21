package com.example.android.popularmovies.fragment;

import android.content.Intent;
import android.os.Bundle;
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
import com.example.android.popularmovies.data.JsonMovieApi;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/*
* A fragment containing the list view of movie posters
*/
public class MainActivityFragment extends Fragment {

    private static final String LOG_TAG = MainActivityFragment.class.getSimpleName();

    private MovieAdapter movieAdapter;

    private GridView gridView;

    private TextView errorMessage;

    private JsonMovieApi jsonMovieApi;


    // TODO: API KEY GOES HERE
    private static final String apiKey = " ";

    // URL for movie data
    private static final String MOVIE_URL = "http://api.themoviedb.org/3/movie/";


    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // movieList = new ArrayList<Movie>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MOVIE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        jsonMovieApi = retrofit.create(JsonMovieApi.class);

        getPopularMovies();

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

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

    public void sortByPopularity() {
        getPopularMovies();
    }

    public void sortByTopRated() {
        getTopRatedMovies();
    }

    // Method for retrieving Popular movies
    private void getPopularMovies() {

        Call<MovieResponse> call = jsonMovieApi.getPopularMovies(apiKey);

        call.enqueue(new Callback<MovieResponse>() {

            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                onMovieResponseReceived(response);

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());

            }
        });
    }

    // Method for retrieving Top Rated Movies
    private void getTopRatedMovies() {

        Call<MovieResponse> call = jsonMovieApi.getTopRatedMovies(apiKey);

        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                onMovieResponseReceived(response);
            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                Log.e(LOG_TAG, t.getMessage());

            }
        });
    }

    // Method for receiving a Movie response object and displaying its data
    private void onMovieResponseReceived(Response<MovieResponse> response) {
        movieAdapter.clear();

        if (response.isSuccessful()) {

            MovieResponse movieResponse = response.body();
            List<Movie> movies = movieResponse.getMovieResults();
            movieAdapter.addAll(movies);

            gridView.setVisibility(View.VISIBLE);
            errorMessage.setVisibility(View.GONE);

        } else {
            errorMessage.setVisibility(View.VISIBLE);
            gridView.setVisibility(View.GONE);
            Log.e(LOG_TAG, "Code: " + response.code());

        }
    }

}

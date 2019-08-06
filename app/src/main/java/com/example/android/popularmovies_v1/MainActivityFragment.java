package com.example.android.popularmovies_v1;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.popularmovies_v1.data.JsonUtils;
import com.example.android.popularmovies_v1.data.Movie;

import java.util.ArrayList;
import java.util.List;

/*
* A fragment containing the list view of movie posters
*/
public class MainActivityFragment extends Fragment {

    private MovieAdapter movieAdapter;

    private ArrayList<Movie> movieList;

    // TODO: REMOVE API KEY BEFORE SUBMITTING
    private static final String apiKey = "301ade02190b07969335c116b1456868";

    // URL for movie data sorted by popularity
    private static final String MOVIE_MOST_POPULAR_URL = "http://api.themoviedb.org/3/movie/popular?api_key=" + apiKey;

    // URL for movie data sorted by top rated
    private static final String MOVIE_TOP_RATED_URL = "http://api.themoviedb.org/3/movie/top_rated?api_key=" + apiKey;


    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        movieList = new ArrayList<Movie>();

        // Start the AsyncTask to fetch the movie data
        MovieAsyncTask task = new MovieAsyncTask();
        task.execute(MOVIE_MOST_POPULAR_URL);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
//        outState.putParcelableArrayList("results", movieList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new MovieAdapter(getActivity(), movieList);

        // Get a reference to the ListView and attach this adapter to it
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid);
        gridView.setAdapter(movieAdapter);

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
        // Start the AsyncTask to fetch the movie data sorted by popularity
        MovieAsyncTask task = new MovieAsyncTask();
        task.execute(MOVIE_MOST_POPULAR_URL);
    }

    public void sortByTopRated() {
        // Start the AsyncTask to fetch the movie data sorted by average ratings
        MovieAsyncTask task = new MovieAsyncTask();
        task.execute(MOVIE_TOP_RATED_URL);
    }

    private class MovieAsyncTask extends AsyncTask<String, Void, List<Movie>> {

        /* This method runs on a background thread and performs the network request */
        @Override
        protected List<Movie> doInBackground(String... urls) {
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Movie> result = JsonUtils.getMovieData(urls[0]);
            return result;

        }

        /* This method runs on the main UI thread after the background work has been completed. */
        @Override
        protected void onPostExecute(List<Movie> data) {
            // Clear the adapter of previous movie data
            movieAdapter.clear();

            // If there is a valid list of Movies, then add them to the adapter's data set. This will trigger the ListView to update.
            if (data != null) {
                movieAdapter.addAll(data);
            }
        }

    }

        /* FOR THE MENU ITEMS: will need something like this to sort the list of movies.
    public int compare(Movie a, Movie b) {
        if (a.popularity == b.popularity) {
            return 0;
        } else if (a.popularity > b.popularity) {
            return 1;
        } else {
            return -1;
        }
    } */

}

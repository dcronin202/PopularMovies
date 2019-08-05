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

import com.example.android.popularmovies_v1.data.JsonUtils;
import com.example.android.popularmovies_v1.data.Movie;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/*
* A fragment containing the list view of movie posters
*/
public class MainActivityFragment extends Fragment {

    private MovieAdapter movieAdapter;

    private ArrayList<Movie> movieList;

    // TODO: REMOVE API KEY BEFORE SUBMITTING
    private static final String apiKey = "301ade02190b07969335c116b1456868";

    // URL for the movie data
    private static final String MOVIE_DB_URL = "http://api.themoviedb.org/3/movie/popular?api_key=" + apiKey;


    // dummy data
    Movie[] moviePosters = {
            new Movie("Title 1", "OT", "https://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "Plot", 7.6, "2019-03-05", 109.035),
            new Movie("Title 2", "OT","https://image.tmdb.org/t/p/w185//dzBtMocZuJbjLOXvrl4zGYigDzh.jpg", "Plot", 7.5, "2019-03-06", 108.035),
            new Movie("Title 3", "OT","https://image.tmdb.org/t/p/w185//or06FN3Dka5tukK1e9sl16pB3iy.jpg", "Plot", 8.6, "2018-03-07", 107.035),
            new Movie("Title 4", "OT","https://image.tmdb.org/t/p/w185//rjbNpRMoVvqHmhmksbokcyCr7wn.jpg", "Plot", 9.6, "2017-03-08", 106.035),
            new Movie("Title 5", "OT","https://image.tmdb.org/t/p/w185//xRWht48C2V8XNfzvPehyClOvDni.jpg", "Plot", 7.3, "2016-03-09", 109.037),
            new Movie("Title 6", "OT","https://image.tmdb.org/t/p/w185//8j58iEBw9pOXFD2L0nt0ZXeHviB.jpg", "Plot", 6.6, "2015-03-10", 109.039),
            new Movie("Title 7", "OT","https://image.tmdb.org/t/p/w185//pU3bnutJU91u3b4IeRPQTOP8jhV.jpg", "Plot", 5.6, "2014-03-11", 99.035),
            new Movie("Title 8", "OT","https://image.tmdb.org/t/p/w185//keym7MPn1icW1wWfzMnW3HeuzWU.jpg", "Plot", 4.6, "2013-03-12", 102.03),
            new Movie("Title 9", "OT","https://image.tmdb.org/t/p/w185//AtsgWhDnHTq68L0lLsUrCnM7TjG.jpg", "Plot", 7.0, "2012-03-13", 105.035),
            new Movie("Title 10", "OT","https://image.tmdb.org/t/p/w185//7WsyChQLEftFiDOVTGkv3hFpyyt.jpg", "Plot", 7.9, "2011-03-14", 104.035)
    };

    public MainActivityFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey("results")) {
            movieList = new ArrayList<Movie>(Arrays.asList(moviePosters));
        } else {
            movieList = savedInstanceState.getParcelableArrayList("results");
        }

        // Start the AsyncTask to fetch the movie data
        MovieAsyncTask task = new MovieAsyncTask();
        task.execute(MOVIE_DB_URL);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList("results", movieList);
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
                launchMovieDetailActivity(position);
            }
        });

        return rootView;

    }

    private void launchMovieDetailActivity(int position) {
        Intent intent = new Intent(getActivity(), DetailActivity.class);
        intent.putExtra(DetailActivity.MOVIE_DETAILS, position);
        startActivity(intent);
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

package com.example.android.popularmovies_v1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.android.popularmovies_v1.data.Movie;

import java.util.Arrays;

/*
* A fragment containing the list view of movie posters
*/
public class MainActivityFragment extends Fragment {

    private MovieAdapter movieAdapter;

    // dummy data
    Movie[] moviePosters = {
            new Movie("Title 1", "OT", "https://image.tmdb.org/t/p/w185//nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg", "Prow scuttle parrel provost Sail ho shrouds spirits boom mizzenmast yardarm.", 7.6, "2019-03-05", 109.035),
            new Movie("Title 2", "OT","https://image.tmdb.org/t/p/w185//dzBtMocZuJbjLOXvrl4zGYigDzh.jpg", "Prow scuttle parrel provost Sail ho shrouds spirits boom mizzenmast yardarm. Pinnace holystone mizzenmast quarter crow's nest nipperkin grog yardarm hempen halter furl. Swab barque interloper chantey doubloon starboard grog black jack gangway rutters.", 7.5, "2019-03-06", 108.035),
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        movieAdapter = new MovieAdapter(getActivity(), Arrays.asList(moviePosters));

        // Get a reference to the ListView and attach this adapter to it
        GridView gridView = (GridView) rootView.findViewById(R.id.movie_grid);
        gridView.setAdapter(movieAdapter);

        return rootView;

    }

}

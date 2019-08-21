package com.example.android.popularmovies.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.R;

public class MovieOverviewFragment extends Fragment {

    private static final String TAG = "MovieOverviewFragment";

    private TextView overview;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_overview_fragment, container, false);

        Movie movie = new Movie();

        overview = view.findViewById(R.id.overview);
        overview.setText(movie.getPlot());

        return view;

    }

}

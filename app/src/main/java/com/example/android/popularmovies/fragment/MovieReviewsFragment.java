package com.example.android.popularmovies.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmovies.R;

public class MovieReviewsFragment extends Fragment {

    private static final String TAG = "MovieReviewsFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_reviews_fragment, container, false);

        return view;

    }
}
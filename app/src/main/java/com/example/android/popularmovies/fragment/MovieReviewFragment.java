package com.example.android.popularmovies.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.adapter.ReviewsRecyclerViewAdapter;
import com.example.android.popularmovies.data.MovieReviews;
import com.example.android.popularmovies.viewmodel.MovieDetailViewModel;

import java.util.ArrayList;

public class MovieReviewFragment extends Fragment {

    private static final String LOG_TAG = MovieReviewFragment.class.getSimpleName();

    private MovieDetailViewModel viewModel;

    private RecyclerView recyclerView;
    private ReviewsRecyclerViewAdapter reviewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_review_fragment, container, false);

        reviewAdapter = new ReviewsRecyclerViewAdapter(getActivity(), new ArrayList<MovieReviews>());
        recyclerView = view.findViewById(R.id.recyclerview_reviews);
        recyclerView.setAdapter(reviewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupReviewsViewModel();

        return view;

    }

    private void setupReviewsViewModel() {
        viewModel = ViewModelProviders.of(getActivity()).get(MovieDetailViewModel.class);
        viewModel.getReviews().observe(this, new Observer<ArrayList<MovieReviews>>() {
            @Override
            public void onChanged(@Nullable ArrayList<MovieReviews> movieReviews) {
                reviewAdapter.updateReviewList(movieReviews);
            }
        });
        viewModel.getReviewList();
    }

}

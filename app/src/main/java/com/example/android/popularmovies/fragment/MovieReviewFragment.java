package com.example.android.popularmovies.fragment;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.adapter.ReviewsRecyclerViewAdapter;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieReviews;
import com.example.android.popularmovies.viewmodel.MovieDetailViewModel;

import java.util.ArrayList;

public class MovieReviewFragment extends Fragment {

    private static final String LOG_TAG = MovieReviewFragment.class.getSimpleName();
    private TextView errorMessage;

    private MovieDetailViewModel viewModel;

    private RecyclerView recyclerView;
    private ReviewsRecyclerViewAdapter reviewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_review, container, false);

        reviewAdapter = new ReviewsRecyclerViewAdapter(getActivity(), new ArrayList<MovieReviews>());
        recyclerView = view.findViewById(R.id.recyclerview_reviews);
        recyclerView.setAdapter(reviewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Divider between Reviews in RecyclerView
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(itemDecoration);

        setupReviewsViewModel();
        setupErrorMessage(view);

        return view;

    }

    private void setupReviewsViewModel() {
        viewModel = ViewModelProviders.of(getActivity()).get(MovieDetailViewModel.class);
        viewModel.getReviews().observe(this, new Observer<ArrayList<MovieReviews>>() {

            @Override
            public void onChanged(@Nullable ArrayList<MovieReviews> movieReviews) {

                if (movieReviews.size() > 0) {
                    reviewAdapter.updateReviewList(movieReviews);
                    recyclerView.setVisibility(View.VISIBLE);
                    errorMessage.setVisibility(View.GONE);

                } else {
                    recyclerView.setVisibility(View.GONE);
                    errorMessage.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.getReviewList();
    }

    private void setupErrorMessage(View view) {
        final Movie movie = viewModel.getMovieDetails();

        Resources resources = getResources();
        String movieTitle = movie.getMovieTitle();
        String errorText = resources.getString(R.string.no_reviews_available, movieTitle);

        errorMessage = view.findViewById(R.id.reviews_error_message);
        errorMessage.setText(errorText);

    }

}

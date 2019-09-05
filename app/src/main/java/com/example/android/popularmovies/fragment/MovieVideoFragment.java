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
import com.example.android.popularmovies.adapter.VideosRecyclerViewAdapter;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieVideos;
import com.example.android.popularmovies.viewmodel.MovieDetailViewModel;

import java.util.ArrayList;

public class MovieVideoFragment extends Fragment {

    private static final String LOG_TAG = MovieVideoFragment.class.getSimpleName();
    private TextView errorMessage;

    private MovieDetailViewModel viewModel;

    private RecyclerView recyclerView;
    private VideosRecyclerViewAdapter videoAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_video, container, false);

        videoAdapter = new VideosRecyclerViewAdapter(getActivity(), new ArrayList<MovieVideos>());
        recyclerView = view.findViewById(R.id.recyclerview_videos);
        recyclerView.setAdapter(videoAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Divider between Videos in RecyclerView
        DividerItemDecoration itemDecoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        itemDecoration.setDrawable(ContextCompat.getDrawable(getContext(), R.drawable.divider));
        recyclerView.addItemDecoration(itemDecoration);

        setupVideosViewModel();
        setupErrorMessage(view);

        return view;

    }

    private void setupVideosViewModel() {
        viewModel = ViewModelProviders.of(getActivity()).get(MovieDetailViewModel.class);
        viewModel.getVideos().observe(this, new Observer<ArrayList<MovieVideos>>() {

            @Override
            public void onChanged(@Nullable ArrayList<MovieVideos> movieVideos) {

                if (movieVideos.size() > 0) {
                    videoAdapter.updateVideoList(movieVideos);
                    recyclerView.setVisibility(View.VISIBLE);
                    errorMessage.setVisibility(View.GONE);

                } else {
                    recyclerView.setVisibility(View.GONE);
                    errorMessage.setVisibility(View.VISIBLE);
                }
            }
        });

        viewModel.getVideoList();
    }

    private void setupErrorMessage(View view) {
        final Movie movie = viewModel.getMovieDetails();

        Resources resources = getResources();
        String movieTitle = movie.getMovieTitle();
        String errorText = resources.getString(R.string.no_videos_available, movieTitle);

        errorMessage = view.findViewById(R.id.video_error_message);
        errorMessage.setText(errorText);

    }

}

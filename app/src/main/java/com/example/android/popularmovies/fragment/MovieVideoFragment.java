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
import com.example.android.popularmovies.adapter.VideosRecyclerViewAdapter;
import com.example.android.popularmovies.data.MovieVideos;
import com.example.android.popularmovies.viewmodel.MovieDetailViewModel;

import java.util.ArrayList;

public class MovieVideoFragment extends Fragment {

    private static final String LOG_TAG = MovieVideoFragment.class.getSimpleName();

    private MovieDetailViewModel viewModel;

    private RecyclerView recyclerView;
    private VideosRecyclerViewAdapter videoAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.movie_video_fragment, container, false);

        videoAdapter = new VideosRecyclerViewAdapter(getActivity(), new ArrayList<MovieVideos>());
        recyclerView = view.findViewById(R.id.recyclerview_videos);
        recyclerView.setAdapter(videoAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        setupVideosViewModel();

        return view;

    }

    private void setupVideosViewModel() {
        viewModel = ViewModelProviders.of(getActivity()).get(MovieDetailViewModel.class);
        viewModel.getVideos().observe(this, new Observer<ArrayList<MovieVideos>>() {
            @Override
            public void onChanged(@Nullable ArrayList<MovieVideos> movieVideos) {
                videoAdapter.updateVideoList(movieVideos);
            }
        });
        viewModel.getVideoList();
    }

}

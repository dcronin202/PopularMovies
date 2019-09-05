package com.example.android.popularmovies.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.R;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.viewmodel.MovieDetailViewModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class MovieDetailsFragment extends Fragment {


    private static final String LOG_TAG = MovieDetailsFragment.class.getSimpleName();

    private MovieDetailViewModel viewModel;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_movie_info, container, false);

        viewModel = ViewModelProviders.of(getActivity()).get(MovieDetailViewModel.class);

        populateMovieDetails(view);

        return view;

    }

    private void populateMovieDetails(View view) {
        final Movie movie = viewModel.getMovieDetails();

        // Movie Poster
        ImageView moviePoster = view.findViewById(R.id.movie_poster);
        Picasso.get()
                .load(movie.getPosterImage())
                .into(moviePoster);

        // Year - reformatted original String input to display MMMM yyyy format
        String releaseDate = movie.getReleaseDate();
        SimpleDateFormat oldDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        Date newReleaseDate = null;
        try {
            newReleaseDate = (Date) oldDateFormat.parse(releaseDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat newDateFormat = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
        String finalDate = newDateFormat.format(newReleaseDate);

        TextView yearReleased = view.findViewById(R.id.release_date);
        yearReleased.setText(finalDate);

        // Avg Rating
        String movieRating = String.valueOf(movie.getRating());
        String avgMovieRating = movieRating + getString(R.string.out_of_ten);

        TextView averageRating = view.findViewById(R.id.average_rating);
        averageRating.setText(avgMovieRating);

        // Plot Overview
        TextView overview = view.findViewById(R.id.movie_overview);
        overview.setText(movie.getOverview());

        // Favorites Checkbox
        final CheckBox favorites = view.findViewById(R.id.checkbox_favorites);
        favorites.setChecked(movie.getIsFavorite());
        favorites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(favorites.isChecked()) {
                    viewModel.addFavorite(movie);
                    Toast.makeText(getActivity(), R.string.added_to_favorites, Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.removeFavorite(movie);
                    Toast.makeText(getActivity(), R.string.removed_from_favorites, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

}

package com.example.android.popularmovies_v1;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies_v1.data.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra position";

    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        // dummy data
        Movie testMovie = new Movie("The Lion King", "The Lion King","https://image.tmdb.org/t/p/w185//dzBtMocZuJbjLOXvrl4zGYigDzh.jpg", "Simba idolises his father, King Mufasa, and takes to heart his own royal destiny. But not everyone in the kingdom celebrates the new cub's arrival. Scar, Mufasa's brother—and former heir to the throne—has plans of his own. The battle for Pride Rock is ravaged with betrayal, tragedy and drama, ultimately resulting in Simba's exile. With help from a curious pair of newfound friends, Simba will have to figure out how to grow up and take back what is rightfully his.", 7.2, "2019-07-12", 425.026);

        populateMovieDetails(testMovie);

        // Change DetailActivity title/header to match movie title
        setTitle(testMovie.getMovieTitle());

    }

    private void populateMovieDetails(Movie movie) {

        // Movie Title
        TextView movieTitle = findViewById(R.id.movie_title);
        movieTitle.setText(movie.getMovieTitle());

        // Movie Poster
        ImageView moviePoster = findViewById(R.id.movie_poster);
        Picasso.get()
                .load(movie.getPosterImage())
                .into(moviePoster);

        // Original Title
        TextView originalTitle = findViewById(R.id.original_title);
        originalTitle.setText(movie.getOriginalTitle());

        // Year
        TextView yearReleased = findViewById(R.id.release_date);
        yearReleased.setText(movie.getReleaseDate());

        // Avg Rating
        TextView averageRating = findViewById(R.id.average_rating);
        averageRating.setText(movie.getRating() + "/10");  // TODO: Once this is working, convert the hard-coded String to a resource string

        // Plot Summary
        TextView plotSummary = findViewById(R.id.plot_summary);
        plotSummary.setText(movie.getPlot());

    }

}

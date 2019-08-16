package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.data.Movie;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Set;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_DETAILS = "movie";

    private MoviePagerAdapter mMoviePagerAdapter;

    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        Movie movieDetails = intent.getParcelableExtra(MOVIE_DETAILS);

        /* Set up ViewPager with the MoviePagerAdapter
        mMoviePagerAdapter = new MoviePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.container);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager); */

        if (movieDetails != null) {

            populateMovieDetails(movieDetails);

            setTitle(R.string.detail_header);

        } else {
            closeOnError();
        }
    }

    private void closeOnError() {
        finish();
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

        TextView yearReleased = findViewById(R.id.release_date);
        yearReleased.setText(finalDate);

        // Avg Rating
        String movieRating = String.valueOf(movie.getRating());
        String avgMovieRating = movieRating + getString(R.string.out_of_ten);

        TextView averageRating = findViewById(R.id.average_rating);
        averageRating.setText(avgMovieRating);

        // Plot Summary
        TextView plotSummary = findViewById(R.id.plot_summary);
        plotSummary.setText(movie.getPlot());

    }

    //Adds fragments to MoviePagerAdapter
    private void setupViewPager(ViewPager viewPager) {
        MoviePagerAdapter adapter = new MoviePagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new MovieOverviewFragment(), String.valueOf(R.string.tab_overview));
        adapter.addFragment(new MovieVideosFragment(), String.valueOf(R.string.tab_videos));
        adapter.addFragment(new MovieReviewsFragment(), String.valueOf(R.string.tab_reviews));

        viewPager.setAdapter(adapter);

    }

}

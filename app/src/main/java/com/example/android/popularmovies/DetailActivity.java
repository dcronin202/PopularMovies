package com.example.android.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.popularmovies.adapter.MoviePagerAdapter;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieReviews;
import com.example.android.popularmovies.fragment.MovieOverviewFragment;
import com.example.android.popularmovies.fragment.MovieReviewsFragment;
import com.example.android.popularmovies.fragment.MovieVideosFragment;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_DETAILS = "movie";

    private MoviePagerAdapter mMoviePagerAdapter;

    private ViewPager mViewPager;

    private Movie movieDetails;

    private MovieReviews movieReviews;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        movieDetails = intent.getParcelableExtra(MOVIE_DETAILS);

        if (movieDetails != null) {

            populateMovieDetails(movieDetails);

            setTitle(R.string.detail_header);

        } else {
            closeOnError();
        }

        //Set up ViewPager with the MoviePagerAdapter
        mMoviePagerAdapter = new MoviePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
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

    }

    //Adds fragments to MoviePagerAdapter
    private void setupViewPager(ViewPager viewPager) {
        final MoviePagerAdapter adapter = new MoviePagerAdapter(getSupportFragmentManager());

        // Passing in movieDetails so views are immediately visible
        MovieOverviewFragment movieOverviewFragment = new MovieOverviewFragment();
        MovieReviewsFragment movieReviewsFragment = new MovieReviewsFragment();

        movieOverviewFragment.setMovie(movieDetails);

        adapter.addFragment(movieOverviewFragment, getString(R.string.tab_overview));
        adapter.addFragment(new MovieVideosFragment(), getString(R.string.tab_videos));
        adapter.addFragment(movieReviewsFragment, getString(R.string.tab_reviews));

        viewPager.setAdapter(adapter);

    }

}

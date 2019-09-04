package com.example.android.popularmovies;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmovies.adapter.MoviePagerAdapter;
import com.example.android.popularmovies.adapter.VideosRecyclerViewAdapter;
import com.example.android.popularmovies.adapter.ReviewsRecyclerViewAdapter;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.data.MovieReviews;
import com.example.android.popularmovies.data.MovieVideos;
import com.example.android.popularmovies.fragment.MovieDetailsFragment;
import com.example.android.popularmovies.fragment.MovieReviewFragment;
import com.example.android.popularmovies.fragment.MovieVideoFragment;
import com.example.android.popularmovies.viewmodel.MovieDetailViewModel;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    public static final String MOVIE_DETAILS = "movie";

    private Movie movieDetails;
    private MovieDetailViewModel viewModel;

    private VideosRecyclerViewAdapter videoAdapter;
    private ReviewsRecyclerViewAdapter reviewAdapter;

    private MoviePagerAdapter mMoviePagerAdapter;
    private ViewPager mViewPager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        movieDetails = intent.getParcelableExtra(MOVIE_DETAILS);

        setTitle(movieDetails.getMovieTitle());

        viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);

        /*if (movieDetails != null) {
            populateMovieDetails(movieDetails);
            setTitle(R.string.detail_header);

        } else {
            closeOnError();
        } */

        viewModel.setMovieDetails(movieDetails);

        //Set up ViewPager with the MoviePagerAdapter
        mMoviePagerAdapter = new MoviePagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(mViewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void closeOnError() {
        finish();
    }


    //Adds fragments to MoviePagerAdapter
    private void setupViewPager(ViewPager viewPager) {
        final MoviePagerAdapter adapter = new MoviePagerAdapter(getSupportFragmentManager());

        // Passing in movieDetails so views are immediately visible
        //MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
        //MovieReviewFragment movieReviewsFragment = new MovieReviewFragment();

        //movieDetailsFragment.setMovie(movieDetails);

        adapter.addFragment(new MovieDetailsFragment(), getString(R.string.tab_info));
        adapter.addFragment(new MovieVideoFragment(), getString(R.string.tab_videos));
        adapter.addFragment(new MovieReviewFragment(), getString(R.string.tab_reviews));

        viewPager.setAdapter(adapter);

    }

}

package com.example.android.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.popularmovies.adapter.MoviePagerAdapter;
import com.example.android.popularmovies.data.Movie;
import com.example.android.popularmovies.fragment.MovieDetailsFragment;
import com.example.android.popularmovies.fragment.MovieReviewFragment;
import com.example.android.popularmovies.fragment.MovieVideoFragment;
import com.example.android.popularmovies.viewmodel.MovieDetailViewModel;


public class DetailActivity extends AppCompatActivity {

    private static final String LOG_TAG = DetailActivity.class.getSimpleName();

    public static final String MOVIE_DETAILS = "movie";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        Movie movieDetails = intent.getParcelableExtra(MOVIE_DETAILS);

        setTitle(movieDetails.getMovieTitle());

        MovieDetailViewModel viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);

        viewModel.setMovieDetails(movieDetails);

        //Set up ViewPager with the MoviePagerAdapter
        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);
        setupViewPager(viewPager);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    //Adds fragments to MoviePagerAdapter
    private void setupViewPager(ViewPager viewPager) {
        final MoviePagerAdapter adapter = new MoviePagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new MovieDetailsFragment(), getString(R.string.tab_info));
        adapter.addFragment(new MovieVideoFragment(), getString(R.string.tab_videos));
        adapter.addFragment(new MovieReviewFragment(), getString(R.string.tab_reviews));

        viewPager.setAdapter(adapter);

    }

}

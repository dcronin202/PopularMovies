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
import com.example.android.popularmovies.database.MovieFavoritesDatabase;
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

    private MovieFavoritesDatabase favoritesDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        movieDetails = intent.getParcelableExtra(MOVIE_DETAILS);

        setTitle(movieDetails.getMovieTitle());

        /*if (movieDetails != null) {
            populateMovieDetails(movieDetails);
            setTitle(R.string.detail_header);

        } else {
            closeOnError();
        } */

        //initVideoRecyclerView();
        //initReviewRecyclerView();

        //setupVideosViewModel();
        //setupReviewsViewModel();

                /* Button videosButton = (Button) findViewById(R.id.button_videos);
        videosButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent videoIntent = new Intent(DetailActivity.this, VideosActivity.class);
                videoIntent.putParcelableArrayListExtra(VideosActivity.VIDEO_DETAILS, videoDetails);
                startActivity(videoIntent);
            }
        });

        Button reviewsButton = (Button) findViewById(R.id.button_reviews);
        reviewsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent reviewIntent = new Intent(DetailActivity.this, ReviewsActivity.class);
                reviewIntent.putExtra(ReviewsActivity.REVIEW_DETAILS, reviewDetails);
                startActivity(reviewIntent);
            }
        }); */

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


    private void setupVideosViewModel() {
        viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        viewModel.getVideos().observe(this, new Observer<ArrayList<MovieVideos>>() {
            @Override
            public void onChanged(@Nullable ArrayList<MovieVideos> movieVideos) {
                videoAdapter.updateVideoList(movieVideos);
            }
        });
        viewModel.getVideoList(movieDetails);
    }

    private void setupReviewsViewModel() {
        viewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);
        viewModel.getReviews().observe(this, new Observer<ArrayList<MovieReviews>>() {
            @Override
            public void onChanged(@Nullable ArrayList<MovieReviews> movieReviews) {
                reviewAdapter.updateReviewList(movieReviews);
            }
        });
        viewModel.getReviewList(movieDetails);
    }


    private void initVideoRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_videos);
        videoAdapter = new VideosRecyclerViewAdapter(this, new ArrayList<MovieVideos>());
        recyclerView.setAdapter(videoAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initReviewRecyclerView() {
        RecyclerView recyclerView = findViewById(R.id.recyclerview_reviews);
        reviewAdapter = new ReviewsRecyclerViewAdapter(this, new ArrayList<MovieReviews>());
        recyclerView.setAdapter(reviewAdapter);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
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

        // Plot Overview
        TextView movieOverview = findViewById(R.id.movie_overview);
        movieOverview.setText(movie.getOverview());

        // Favorites Checkbox
        final CheckBox favorites = findViewById(R.id.checkbox_favorites);
        favorites.setChecked(movie.getIsFavorite());
        favorites.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(favorites.isChecked()) {
                    viewModel.addFavorite(movieDetails);
                    Toast.makeText(getApplicationContext(), R.string.added_to_favorites, Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.removeFavorite(movieDetails);
                    Toast.makeText(getApplicationContext(), R.string.removed_from_favorites, Toast.LENGTH_SHORT).show();
                }
            }
        });

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

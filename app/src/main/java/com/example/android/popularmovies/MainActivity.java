package com.example.android.popularmovies;

import android.arch.lifecycle.ViewModelProviders;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.android.popularmovies.fragment.MainActivityFragment;
import com.example.android.popularmovies.viewmodel.MovieMainViewModel;

public class MainActivity extends AppCompatActivity {

    MainActivityFragment mainActivityFragment;
    MovieMainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = ViewModelProviders.of(this).get(MovieMainViewModel.class);
        setTitle();

        mainActivityFragment = (MainActivityFragment) getSupportFragmentManager().findFragmentById(R.id.fragment);

    }

    private void setTitle() {
        if (viewModel.getViewState().equals(MovieMainViewModel.favoritesView)) {
            setTitle("Favorite Movies");
        } else if (viewModel.getViewState().equals(MovieMainViewModel.topRatedView)) {
            setTitle("Top Rated Movies");
        } else {
            setTitle("Popular Movies");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.most_popular) {
            // Sort by most popular
            mainActivityFragment.sortByPopularity();
            setTitle();
            return true;
        }

        if (id == R.id.highest_rated) {
            // Sort by highest rated
            mainActivityFragment.sortByTopRated();
            setTitle();
            return true;
        }

        if (id==R.id.favorites) {
            // Sort by favorites
            mainActivityFragment.sortByFavorites();
            setTitle();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

}

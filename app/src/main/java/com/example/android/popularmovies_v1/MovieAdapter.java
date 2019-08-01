package com.example.android.popularmovies_v1;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.android.popularmovies_v1.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MovieAdapter extends ArrayAdapter<Movie> {

    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();

    /**
     * Custom constructor used to inflate the layout file,
     * and the List is the data we want to populate into the lists.
     *
     * @param context       The current context. Used to inflate the layout file.
     * @param moviePosters  List of Movie objects to display in a list.
     */
    public MovieAdapter(Activity context, List<Movie> moviePosters) {

        super(context, 0, moviePosters);

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Gets the Movie object from the ArrayAdapter at the appropriate position
        Movie poster = getItem(position);

        /**
         * if this is a new View object, then inflate the layout.
         * If not, this view already has the layout inflated from a previous call to getView
         * and we modify the View widgets as normal.
         */
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.movie_item, parent, false);
        }

        ImageView posterView = (ImageView) convertView.findViewById(R.id.movie_image);
        Picasso.get()
                .load(poster.getPosterImage())
                .fit()  // this "stretches to fit" inside the image-view
                .into(posterView);

        return convertView;

    }

}

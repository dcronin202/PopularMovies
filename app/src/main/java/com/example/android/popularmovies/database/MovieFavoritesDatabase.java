package com.example.android.popularmovies.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.util.Log;

import com.example.android.popularmovies.data.Movie;

@Database(entities = {Movie.class}, version = 1, exportSchema = false)
public abstract class MovieFavoritesDatabase extends RoomDatabase {

    private static final String LOG_TAG = MovieFavoritesDatabase.class.getSimpleName();

    private static final Object LOCK = new Object();
    private static final String FAVORITES_LIST = "favorites";
    private static MovieFavoritesDatabase sInstance;

    public static MovieFavoritesDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                Log.d(LOG_TAG, "Creating new database instance.");
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        MovieFavoritesDatabase.class,
                        MovieFavoritesDatabase.FAVORITES_LIST)
                        .build();
            }
        }
        Log.d(LOG_TAG, "Getting the database instance");
        return sInstance;
    }

    public abstract MovieDao movieDao();

    /* Create callback for populating the database
    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {

        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase database) {
            super.onOpen(database);
            new PopulateDatabaseAsync(sInstance).exectute();
        }
    };

    // Populate the database in the background
    private static class PopulateDatabaseAsync extends AsyncTask<Void, Void, Void> {

        private final MovieDao mDao;
        private Movie movieDetails;
        String[] test = {"Testing", "One", "Two"};

        PopulateDatabaseAsync(MovieFavoritesDatabase database) {
            mDao = database.taskDao();
        }

        /*
        @Override
        protected Void doInBackground(final Void... params) {
            mDao.deleteAll();


        }

    } */

}

package com.example.android.popularmovies.database;


import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;


import com.example.android.popularmovies.data.Movie;

import java.util.List;


@Dao
public interface MovieDao {

    @Query("SELECT * FROM favorites")
    LiveData<List<Movie>> loadAllMovies();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertMovie(Movie movie);

    @Delete
    void removeMovie(Movie movie);

    @Query("DELETE FROM favorites")
    void deleteAll();

}

package com.example.android.popularmovies.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface JsonMovieApi {

    @GET("popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("movie/{id}/videos")
    Call<MovieResponse> getMovieVideos();

    @GET("movie/{id}/reviews")
    Call<MovieResponse> getMovieReviews();

}

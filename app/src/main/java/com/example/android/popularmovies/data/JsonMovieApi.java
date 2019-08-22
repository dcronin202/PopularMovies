package com.example.android.popularmovies.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface JsonMovieApi {

    @GET("popular")
    Call<MovieResponse> getPopularMovies(@Query("api_key") String apiKey);

    @GET("top_rated")
    Call<MovieResponse> getTopRatedMovies(@Query("api_key") String apiKey);

    @GET("{id}/videos")
    Call<MovieVideosResponse> getMovieVideos(@Path("id") int videoId, @Query("api_key") String apiKey);

    @GET("{id}/reviews")
    Call<MovieReviewsResponse> getMovieReviews(@Path("id") int reviewID, @Query("api_key") String apiKey);

}

package com.example.android.popularmovies_v1.data;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    // Tag for log messages
    private static final String LOG_TAG = JsonUtils.class.getSimpleName();


    private JsonUtils() {
    }

    /* Query the movie dataset and return a list of Movie objects. */
    public static List<Movie> getMovieData(String requestUrl) {

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        // Extract relevant fields from the JSON response
        List<Movie> movies = parseMoviesFromJson(jsonResponse);

        // Return list of movies
        return movies;

    }

    /* Returns new URL object from the given string URL */
    private static URL createUrl(String stringUrl) {

        URL url = null;

        try {
            url = new URL(stringUrl);

        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL ", e);
        }

        return url;
    }

    /* Make an HTTP request ot the given URL and return a String as a response */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;

        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200), then read the input stream and parse the response
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);

            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());

            }

        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the movie JSON results.", e);

        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (inputStream != null) {
                // Closing the input stream could throw an IOException, which is why
                // the makeHttpRequest(URL url) method signature specifies than an IOException
                // could be thrown.
                inputStream.close();
            }

        }

        return jsonResponse;
    }

    /* Convert the InputStream into a String that contains the whole JSON response from the server */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();

            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }

        return output.toString();
    }

    /* Return a list of Movie objects that have been built up from parsing the given JSON response */
    private static List<Movie> parseMoviesFromJson(String movieJSON) {
        // If the JSON string is empty or null, return early.
        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }

        // Create an empty ArrayList to add movies to
        List<Movie> movies = new ArrayList<>();

        // Try to parse the JSON response string
        try {
            // Create a JSONObject from the JSON response string
            JSONObject jsonResponse = new JSONObject(movieJSON);

            // Extract the JSONArray associated with the key called "results", which represents the list of movies
            JSONArray movieArray = jsonResponse.getJSONArray("results");

            for (int i = 0; i < movieArray.length(); i++) {
                JSONObject currentMovie = movieArray.getJSONObject(i);

                // Extract value for movie title
                String movieTitle = currentMovie.getString("title");

                // Extract value for original title
                String originalTitle = currentMovie.getString("original_title");

                // Extract value for movie poster
                String moviePoster = "https://image.tmdb.org/t/p/w185/" + currentMovie.getString("poster_path");

                // Extract value for plot summary
                String plot = currentMovie.getString("overview");

                // Extract value for average rating
                double rating = currentMovie.getDouble("vote_average");

                // Extract value for release date
                String releaseDate = currentMovie.getString("release_date");

                // Extract value for popularity
                double popularity = currentMovie.getDouble("popularity");


                // Return new Movie object with the data from the JSON response
                Movie movie = new Movie(movieTitle, originalTitle, moviePoster, plot, rating, releaseDate, popularity);
                // Return the list of movies

                // Add the new Movie to the list of movies
                movies.add(movie);

            }

        } catch (JSONException e) {
            Log.e("JsonUtils", "Problem parsing the movie JSON results", e);

        }

        // Return list of movies
        return movies;

    }

}

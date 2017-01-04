package com.udacity.apurv.android_nanodegree_project2.util;

import android.net.Uri;
import android.util.Log;

import com.google.common.collect.ImmutableMap;
import com.udacity.apurv.android_nanodegree_project2.BuildConfig;
import com.udacity.apurv.android_nanodegree_project2.constants.MovieDBAPIConstants;

import java.util.Map;

import lombok.NonNull;

/**
 * Contains method to create the Movie DB URL
 */

public class MovieDBUtils {
    private static String LOG_TAG = MovieDBUtils.class.getSimpleName();

    private static final Map<String, String> apiMap = ImmutableMap.of(MovieDBAPIConstants.POPULAR_MOVIES_OPTION, MovieDBAPIConstants.POPULAR_MOVIES_API,
                                                                      MovieDBAPIConstants.TOP_RATED_MOVIES_OPTION, MovieDBAPIConstants.TOP_RATED_MOVIES_API);

    /**
     * This method returns the popular movies URL
     * @param option one of the criteria to fetch the movie
     * @return String Url string to fetch popular movie information
     * @throws IllegalArgumentException This is thrown in case the option does not match one of the existing movie type options
     */
    public static String getPopularMoviesURL(@NonNull final String option) throws IllegalArgumentException {
        String url = "";
        if (!apiMap.containsKey(option)) {
           url = MovieDBAPIConstants.MOVIE_DB_BASE_URL + apiMap.get(MovieDBAPIConstants.POPULAR_MOVIES_OPTION);
        } else {
            url = MovieDBAPIConstants.MOVIE_DB_BASE_URL + apiMap.get(option);
        }
        Uri uri = Uri.parse(url)
                .buildUpon()
                .appendQueryParameter(MovieDBAPIConstants.API_KEY, BuildConfig.MOVIE_DB_API_KEY)
                .build();
        Log.v(LOG_TAG, "URL is" + uri.toString());
        return uri.toString();



    }

    /**
     * This method is used to return the trailer URL for movies.
     * @param movieId - stores the movie id.
     * @return String - url.
     * @throws IllegalArgumentException
     */
    public static String getMovieTrailerURL(@NonNull final String movieId) throws IllegalArgumentException {
        String url = MovieDBAPIConstants.MOVIE_TRAILER_URL;
        url = url.replaceAll(MovieDBAPIConstants.MOVIE_ID_PLACEHOLDER, movieId);
        Uri uri = Uri.parse(url)
                .buildUpon()
                .appendQueryParameter(MovieDBAPIConstants.API_KEY, BuildConfig.MOVIE_DB_API_KEY)
                .appendQueryParameter(MovieDBAPIConstants.MOVIE_LANGUAGE_TAG, MovieDBAPIConstants.MOVIE_LANGUAGE)
                .build();
        Log.v(LOG_TAG, "URL is" + uri.toString());
        return uri.toString();
    }

    /**
     * This method is used to return the review URL for movies.
     * @param movieId - stores the movie id.
     * @return String - url.
     * @throws IllegalArgumentException
     */
    public static String getMovieReviewsURL(@NonNull final String movieId) throws IllegalArgumentException {
        String url = MovieDBAPIConstants.MOVIE_REVIEWS_URL;
        url = url.replaceAll(MovieDBAPIConstants.MOVIE_ID_PLACEHOLDER, movieId);
        Uri uri = Uri.parse(url)
                .buildUpon()
                .appendQueryParameter(MovieDBAPIConstants.API_KEY, BuildConfig.MOVIE_DB_API_KEY)
                .appendQueryParameter(MovieDBAPIConstants.MOVIE_LANGUAGE_TAG, MovieDBAPIConstants.MOVIE_LANGUAGE)
                .build();
        Log.v(LOG_TAG, "URL is" + uri.toString());
        return uri.toString();
    }
}

package com.udacity.apurv.android_nanodegree_project2.util;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.udacity.apurv.android_nanodegree_project2.BuildConfig;
import com.udacity.apurv.android_nanodegree_project2.R;
import com.udacity.apurv.android_nanodegree_project2.constants.MovieDBAPIConstants;

import org.apache.commons.lang3.StringUtils;

import lombok.NonNull;

/**
 * Contains method to create the Movie DB URL
 */

public class MovieDBUtils {
    private static String LOG_TAG = MovieDBUtils.class.getSimpleName();

    /**
     * This method returns the popular movies URL
     * @param option one of the criteria to fetch the movie
     * @return String Url string to fetch popular movie information
     * @throws IllegalArgumentException This is thrown in case the option does not match one of the existing movie type options
     */
    public static String getPopularMoviesURL(@NonNull final Context context, @NonNull final String option) throws IllegalArgumentException {
        String url = StringUtils.EMPTY;

        if(option.equals(context.getString(R.string.pref_sort_order_popular))) {
            url = MovieDBAPIConstants.MOVIE_DB_BASE_URL + MovieDBAPIConstants.POPULAR_MOVIES_API;
        } else if(option.equals(context.getString(R.string.pref_sort_order_toprated))) {
            url = MovieDBAPIConstants.MOVIE_DB_BASE_URL + MovieDBAPIConstants.TOP_RATED_MOVIES_API;
        } else {
            url = MovieDBAPIConstants.MOVIE_DB_BASE_URL + MovieDBAPIConstants.POPULAR_MOVIES_API;
        }

        Uri uri = Uri.parse(url)
                .buildUpon()
                .appendQueryParameter(context.getString(R.string.movie_db_api_key), BuildConfig.MOVIE_DB_API_KEY)
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
    public static String getMovieTrailerURL(@NonNull final Context context, @NonNull final String movieId) throws IllegalArgumentException {
        String url = MovieDBAPIConstants.MOVIE_TRAILER_URL;
        url = url.replaceAll(MovieDBAPIConstants.MOVIE_ID_PLACEHOLDER, movieId);
        Uri uri = Uri.parse(url)
                .buildUpon()
                .appendQueryParameter(context.getString(R.string.movie_db_api_key), BuildConfig.MOVIE_DB_API_KEY)
                .appendQueryParameter(context.getString(R.string.movie_language_tag), context.getString(R.string.movie_language))
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
    public static String getMovieReviewsURL(@NonNull final Context context, @NonNull final String movieId) throws IllegalArgumentException {
        String url = MovieDBAPIConstants.MOVIE_REVIEWS_URL;
        url = url.replaceAll(MovieDBAPIConstants.MOVIE_ID_PLACEHOLDER, movieId);
        Uri uri = Uri.parse(url)
                .buildUpon()
                .appendQueryParameter(context.getString(R.string.movie_db_api_key), BuildConfig.MOVIE_DB_API_KEY)
                .appendQueryParameter(context.getString(R.string.movie_language_tag), context.getString(R.string.movie_language))
                .build();
        Log.v(LOG_TAG, "URL is" + uri.toString());
        return uri.toString();
    }
}

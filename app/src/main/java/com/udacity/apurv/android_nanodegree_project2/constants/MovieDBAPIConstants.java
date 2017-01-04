package com.udacity.apurv.android_nanodegree_project2.constants;

/**
 * Contains all the constants related to the Movie DB API
 */

public final class MovieDBAPIConstants {

    public static final String API_KEY = "api_key";

    public static final String MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3/";

    public static final String POPULAR_MOVIES_OPTION = "popularMovies";

    public static final String TOP_RATED_MOVIES_OPTION = "topRatedMovies";

    public static final String MOVIE_PATH = "movie/";

    public static final String POPULAR_MOVIES_API = MOVIE_PATH + "popular";

    public static final String TOP_RATED_MOVIES_API =  MOVIE_PATH + "top_rated";

    public static final String MOVIE_DB_IMAGE_BASE_URL_MAIN = "http://image.tmdb.org/t/p/w185/";

    public static final String MOVIE_DB_IMAGE_BASE_URL_DETAIL = "http://image.tmdb.org/t/p/w500/";

    public static final String MOVIE_DB_ERROR_MESSAGE = "Unable to fetch the information. Please try again.";

    public static final String MOVIE_ID_PLACEHOLDER = "<MOVIE_ID>";

    public static final String MOVIE_TRAILER_URL = MOVIE_DB_BASE_URL + MOVIE_PATH + MOVIE_ID_PLACEHOLDER + "/videos";

    public static final String MOVIE_REVIEWS_URL = MOVIE_DB_BASE_URL + MOVIE_PATH + MOVIE_ID_PLACEHOLDER + "/reviews";

    public static final String MOVIE_LANGUAGE_TAG = "language";

    public static final String MOVIE_LANGUAGE = "en-US";

    public static final String MOVIE_DB_YOUTUBE_URL = "vnd.youtube:";

}

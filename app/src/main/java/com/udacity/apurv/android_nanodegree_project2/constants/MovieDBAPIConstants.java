package com.udacity.apurv.android_nanodegree_project2.constants;

/**
 * Contains all the constants related to the Movie DB API. Some constants made sense to be kept here as
 * string concatenation was proving more difficult using resources.
 */

public final class MovieDBAPIConstants {


    public static final String MOVIE_DB_BASE_URL = "http://api.themoviedb.org/3/";

    public static final String MOVIE_PATH = "movie/";

    public static final String POPULAR_MOVIES_API = MOVIE_PATH + "popular";

    public static final String TOP_RATED_MOVIES_API =  MOVIE_PATH + "top_rated";

    public static final String MOVIE_ID_PLACEHOLDER = "<MOVIE_ID>";

    public static final String MOVIE_TRAILER_URL = MOVIE_DB_BASE_URL + MOVIE_PATH + MOVIE_ID_PLACEHOLDER + "/videos";

    public static final String MOVIE_REVIEWS_URL = MOVIE_DB_BASE_URL + MOVIE_PATH + MOVIE_ID_PLACEHOLDER + "/reviews";

}

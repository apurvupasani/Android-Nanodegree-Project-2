package com.udacity.apurv.android_nanodegree_project2.data;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Created by upasa on 12/29/2016.
 */

public class MovieContract {
    //Content Authority for movie db.
    public static final String CONTENT_AUTHORITY = "com.udacity.apurv.android_nanodegree_project2";
    //Base content URI for the movie db.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);
    //Base path for movie
    public static final String PATH_MOVIE = "movie";

    /**
     *  This class defines the table contents of the movies table
     */
    public static final class MovieEntry implements BaseColumns {
        //Content URI content://com.udacity.apurv.android_nanodegree_project2/movie
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MOVIE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MOVIE;

        public static final String TABLE_NAME = "movie";

        public static final String COLUMN_MOVIE_ID = "movie_id";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IMAGE = "image";
        public static final String COLUMN_IMAGE2 = "image2";
        public static final String COLUMN_OVERVIEW = "overview";
        public static final String COLUMN_RATING = "rating";
        public static final String COLUMN_RELEASE_DATE = "release_date";
        public static final String COLUMN_UPDATE_DATE = "update_date";
        //Default Y for now. Will be updated once we move to syncadapter.
        public static final String COLUMN_IS_FAVORITE = "is_favorite";

        /**
         * Provides the URI for the movie with id.
         * @param id
         * @return Uri.
         */
        public static Uri buildMovieUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }

        public static long normalizeDate(long startDate) {
            // normalize the start date to the beginning of the (UTC) day
            Time time = new Time();
            time.set(startDate);
            int julianDay = Time.getJulianDay(startDate, time.gmtoff);
            return time.setJulianDay(julianDay);
        }
    }
}

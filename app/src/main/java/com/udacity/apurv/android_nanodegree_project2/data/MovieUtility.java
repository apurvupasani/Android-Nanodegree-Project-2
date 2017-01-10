package com.udacity.apurv.android_nanodegree_project2.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.udacity.apurv.android_nanodegree_project2.entities.MovieRecord;

import java.util.ArrayList;
import java.util.List;

/**
 * This class contains utility methods to access the DB layer
 * Created by upasa on 12/31/2016.
 */

public class MovieUtility {

    private static final int COL_MOVIE_ID = 1;
    private static final int COL_TITLE = 2;
    private static final int COL_OVERVIEW = 3;
    private static final int COL_RATING = 4;
    private static final int COL_RELEASE_DATE = 5;
    private static final int COL_IMAGE = 6;

    public static ContentValues getContentValues(MovieRecord movieRecord) {
        ContentValues movieValues = new ContentValues();
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movieRecord.getMovieId());
        movieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movieRecord.getOriginalTitle());
        movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movieRecord.getOverview());
        movieValues.put(MovieContract.MovieEntry.COLUMN_IMAGE, movieRecord.getMovieImageThumbnailPath());
        movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movieRecord.getReleaseDate());
        movieValues.put(MovieContract.MovieEntry.COLUMN_RATING, movieRecord.getUserRating());
        return movieValues;
    }

    public static boolean isFavourite(Context context, String movieId) {
        Cursor cursor = context.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                new String[]{movieId},
                null
        );
         boolean res = (cursor != null) && cursor.moveToFirst();
         cursor.close();
         return res;
    }

    public static List<MovieRecord> getAllFavorites(Context context) {
        Cursor cursor = context.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null, null, null, null );

        if (!cursor.moveToFirst()) {
            return new ArrayList<MovieRecord>();
        }
        List<MovieRecord> records = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                MovieRecord record =  new MovieRecord();
                record.setMovieId(Integer.toString(cursor.getInt(COL_MOVIE_ID)));
                record.setOriginalTitle(cursor.getString(COL_TITLE));
                record.setOverview(cursor.getString(COL_OVERVIEW));
                record.setReleaseDate(cursor.getString(COL_RELEASE_DATE));
                record.setUserRating(cursor.getDouble(COL_RATING));
                record.setMovieImageThumbnailPath(cursor.getString(COL_IMAGE));
                records.add(record);
            } while (cursor.moveToNext());
        } else {
            return new ArrayList<MovieRecord>();
        }
        cursor.close();
        return records;
    }
}
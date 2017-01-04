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

public class Utility {

    public static final int COL_MOVIE_ID = 1;
    public static final int COL_TITLE = 2;
    public static final int COL_OVERVIEW = 3;
    public static final int COL_IMAGE = 3;
    public static final int COL_RELEASE_DATE = 4;
    public static final int COL_RATING = 5;


    public static ContentValues getContentValues(MovieRecord movieRecord) {
        ContentValues movieValues = new ContentValues();
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, movieRecord.getMovieId());
        movieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, movieRecord.getOriginalTitle());
        movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, movieRecord.getOverview());
        movieValues.put(MovieContract.MovieEntry.COLUMN_IMAGE, movieRecord.getMovieImageThumbnailPath());
        movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, movieRecord.getReleaseDate());
        movieValues.put(MovieContract.MovieEntry.COLUMN_RATING, movieRecord.getUserRating());
        movieValues.put(MovieContract.MovieEntry.COLUMN_IS_FAVORITE, "Y");
        return movieValues;
    }

    public static boolean isFavourite(Context context, String movieId) {
        Cursor cursor = context.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,   // projection
                MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?", // selection
                new String[]{movieId},   // selectionArgs
                null    // sort order
        );
         boolean res = (cursor != null) && cursor.moveToFirst();
         cursor.close();
         return res;
    }

    public static List<MovieRecord> getAllFavorites(Context context) {
        Log.d("Utility", "In here");
        Cursor cursor = context.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI,
                null,   // projection
                null, null,   // selectionArgs
                null    // sort order
        );

        if (!cursor.moveToFirst()) {
            return new ArrayList<MovieRecord>();
        }
        List<MovieRecord> records = new ArrayList<>();
        while(cursor.isLast()) {
            MovieRecord record =  new MovieRecord();
            record.setMovieId(Integer.toString(cursor.getInt(COL_MOVIE_ID)));
            record.setOriginalTitle(cursor.getString(COL_TITLE));
            record.setOverview(cursor.getString(COL_OVERVIEW));
            record.setReleaseDate(cursor.getString(COL_RELEASE_DATE));
            record.setUserRating(cursor.getDouble(COL_RATING));
            record.setMovieImageThumbnailPath(cursor.getString(COL_IMAGE));
            records.add(record);
            cursor.moveToNext();
        }
        cursor.close();
        Log.d("Utility", "Num records" + records.size());
        return records;
    }
}
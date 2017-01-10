package com.udacity.apurv.android_nanodegree_project2.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.test.AndroidTestCase;

import java.util.Map;
import java.util.Set;

/**
 * Created by upasa on 12/29/2016.
 */

public class TestMovieUtilities extends AndroidTestCase {

      static ContentValues getMovieValues() {
        ContentValues movieValues = new ContentValues();
        movieValues.put(MovieContract.MovieEntry.COLUMN_MOVIE_ID, 12345);
        movieValues.put(MovieContract.MovieEntry.COLUMN_TITLE, "Test Movie");
        movieValues.put(MovieContract.MovieEntry.COLUMN_OVERVIEW, "Test Overview");
        movieValues.put(MovieContract.MovieEntry.COLUMN_RATING, 2.5);
        movieValues.put(MovieContract.MovieEntry.COLUMN_RELEASE_DATE, "2016-06-30");
        movieValues.put(MovieContract.MovieEntry.COLUMN_IMAGE, "Test image");
        return movieValues;
    }

     static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }

    }
}

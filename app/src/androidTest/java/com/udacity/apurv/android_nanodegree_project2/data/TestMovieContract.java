package com.udacity.apurv.android_nanodegree_project2.data;

import android.net.Uri;
import android.test.AndroidTestCase;

/**
 * Used to test the movie contract.
 */

public class TestMovieContract extends AndroidTestCase {

    private static final Long TEST_MOVIE_ID = 12345L;

    public void testBuildMovieURI() {
        Uri movieUri = MovieContract.MovieEntry.buildMovieUri(TEST_MOVIE_ID);
        assertNotNull("Error: Null URI returned.", movieUri);
        assertEquals("Error: Movie URI not appended correctly",
                Long.toString(TEST_MOVIE_ID), movieUri.getLastPathSegment());
        assertEquals("Error: Movie URI doesn't match our expected result",
                movieUri.toString(),
                "content://com.udacity.apurv.android_nanodegree_project2/movie/12345");
    }
}

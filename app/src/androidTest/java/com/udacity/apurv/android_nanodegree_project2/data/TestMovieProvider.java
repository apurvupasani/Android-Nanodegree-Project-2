package com.udacity.apurv.android_nanodegree_project2.data;

/**
 * Created by upasa on 12/29/2016.
 */


import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Build;
import android.test.AndroidTestCase;
import android.util.Log;

/*
    Note: This is not a complete set of tests of the Sunshine ContentProvider, but it does test
    that at least the basic functionality has been implemented correctly.

    Students: Uncomment the tests in this class as you implement the functionality in your
    ContentProvider to make sure that you've implemented things reasonably correctly.
 */
public class TestMovieProvider extends AndroidTestCase {

    public static final String LOG_TAG = TestMovieProvider.class.getSimpleName();

    public void deleteAllRecordsFromProvider() {
        mContext.getContentResolver().delete(
                MovieContract.MovieEntry.CONTENT_URI, null, null);

        Cursor cursor = mContext.getContentResolver().query(
                MovieContract.MovieEntry.CONTENT_URI, null, null, null, null);
        assertEquals("Error: Records not deleted from Movie table during delete", 0, cursor.getCount());
        cursor.close();

    }

    public void deleteAllRecords() {
        deleteAllRecordsFromProvider();
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        deleteAllRecords();
    }

    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();
        ComponentName componentName = new ComponentName(mContext.getPackageName(), MovieProvider.class.getName());
        try {
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);
            assertEquals("Error: MovieProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + MovieContract.CONTENT_AUTHORITY,
                    providerInfo.authority, MovieContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
             assertTrue("Error: MovieProvider not registered at " + mContext.getPackageName(), false);
        }
    }

    public void testGetType() {
        // content://com.udacity.apurv.android_nanodegree_project2/movie
        String type = mContext.getContentResolver().getType(MovieContract.MovieEntry.CONTENT_URI);
        // vnd.android.cursor.dir/com.udacity.apurv.android_nanodegree_project2/movie
        assertEquals("Error: the MovieEntry CONTENT_URI should return MovieEntry.CONTENT_TYPE",
                MovieContract.MovieEntry.CONTENT_TYPE, type);
    }


    /*
        This test uses the database directly to insert and then uses the ContentProvider to
        read out the data.
     */
    public void testBasicMovieQuery() {
                MovieDBHelper dbHelper = new MovieDBHelper(mContext);
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues movieValues = TestMovieUtilities.getMovieValues();

        long movieRowId = db.insert(MovieContract.MovieEntry.TABLE_NAME, null, movieValues);
        assertTrue("Unable to Insert MovieEntry into the Database", movieRowId != -1);
        db.close();

        // Test the basic content provider query
        Cursor movieCursor = mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null,null,null,null);
        assertTrue("Error: Unable to get any rows from DB", movieCursor.moveToFirst());
        // Make sure we get the correct cursor out of the database
        TestMovieUtilities.validateCurrentRecord("testBasicMovieQuery", movieCursor, movieValues);

        //Make sure we can update. Here we will update the movie title
        ContentValues updateValues = new ContentValues();
        updateValues.put(MovieContract.MovieEntry.COLUMN_TITLE, "Test Update");
        db = dbHelper.getWritableDatabase();
        int numRowsaffected = db.update(MovieContract.MovieEntry.TABLE_NAME, updateValues,"_ID=?", new String[]{Long.toString(movieRowId)});
        assertEquals("Wrong number of rows updated ", 1, numRowsaffected);
        db.close();

        //Check whether the update happened
        Cursor movieUpdateCursor = mContext.getContentResolver().query(MovieContract.MovieEntry.CONTENT_URI, null, "_ID=?",new String[]{Long.toString(movieRowId)},null);
        assertTrue("Error: Unable to get any rows from DB", movieUpdateCursor.moveToFirst());
        assertEquals("Invalid update.", movieUpdateCursor.getString(2), "Test Update");

        //Check whether record can be deleted
        db = dbHelper.getWritableDatabase();
        int numRowsDeleted = db.delete(MovieContract.MovieEntry.TABLE_NAME, "_ID=?", new String[]{Long.toString(movieRowId)});
        assertEquals("Wrong number of rows deleted ", 1, numRowsaffected);
        db.close();
    }
}

package com.udacity.apurv.android_nanodegree_project2.task;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.udacity.apurv.android_nanodegree_project2.data.MovieContract;
import com.udacity.apurv.android_nanodegree_project2.data.Utility;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieRecord;

import static com.udacity.apurv.android_nanodegree_project2.constants.MovieDBAPIConstants.MOVIE_DB_ERROR_MESSAGE;

/**
 * Created by upasa on 12/31/2016.
 */

public class UpdateFavoriteMovieTask extends AsyncTask<Void, Void, Boolean> {
    private Context mContext;
    private MovieRecord movieRecord;
    private Boolean performAction;
    private ImageView favImage;

    private static final String LOG_TAG = UpdateFavoriteMovieTask.class.getSimpleName();

    public UpdateFavoriteMovieTask(Context mContext, MovieRecord movieRecord, ImageView favorite) {
        this.mContext = mContext;
        this.movieRecord = movieRecord;
        this.favImage = favorite;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return Utility.isFavourite(mContext, movieRecord.getMovieId());
    }


    @Override
    protected void onPostExecute(Boolean isFavorite) {
        if (!isFavorite) {
            ContentValues values = Utility.getContentValues(movieRecord);
            Log.d(LOG_TAG, "Image path to be saved" + movieRecord.getMovieImageThumbnailPath());
            mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);
            if (Utility.isFavourite(mContext, movieRecord.getMovieId())) {
                favImage.setImageResource(android.R.drawable.btn_star_big_on);
                Toast.makeText(mContext, "Movie '" + movieRecord.getOriginalTitle()  + "' added to favorites.", Toast.LENGTH_SHORT).show();
            }
        } else {
            int rowsDeleted = mContext.getContentResolver().delete(
                    MovieContract.MovieEntry.CONTENT_URI,
                    MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                    new String[]{movieRecord.getMovieId()}
            );
            if (!Utility.isFavourite(mContext, movieRecord.getMovieId())) {
                favImage.setImageResource(android.R.drawable.btn_star_big_off);
                Toast.makeText(mContext, "Movie '" + movieRecord.getOriginalTitle()  + "' removed from favorites.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}

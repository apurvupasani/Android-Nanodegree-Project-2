package com.udacity.apurv.android_nanodegree_project2.task;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;

import com.udacity.apurv.android_nanodegree_project2.data.MovieContract;
import com.udacity.apurv.android_nanodegree_project2.data.Utility;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieRecord;

/**
 * Created by upasa on 12/31/2016.
 */

public class UpdateFavoriteMovieTask extends AsyncTask<Void, Void, Boolean> {
    private Context mContext;
    private MovieRecord movieRecord;
    private Boolean performAction;
    private Button favButton;

    public UpdateFavoriteMovieTask(Context mContext, MovieRecord movieRecord, Button favorite) {
        this.mContext = mContext;
        this.movieRecord = movieRecord;
        this.favButton = favorite;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return Utility.isFavourite(mContext, movieRecord.getMovieId());
    }


    @Override
    protected void onPostExecute(Boolean isFavorite) {
        if (!isFavorite) {
            ContentValues values = Utility.getContentValues(movieRecord);
            mContext.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);
            if (Utility.isFavourite(mContext, movieRecord.getMovieId())) {
                favButton.setText("Favorite");
            }
        } else {
            int rowsDeleted = mContext.getContentResolver().delete(
                    MovieContract.MovieEntry.CONTENT_URI,
                    MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                    new String[]{movieRecord.getMovieId()}
            );
            if (!Utility.isFavourite(mContext, movieRecord.getMovieId())) {
                favButton.setText("Not Favorite");
            }
        }
    }
}

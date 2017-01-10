package com.udacity.apurv.android_nanodegree_project2.task;

import android.content.ContentValues;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;
import android.widget.Toast;

import com.udacity.apurv.android_nanodegree_project2.R;
import com.udacity.apurv.android_nanodegree_project2.data.MovieContract;
import com.udacity.apurv.android_nanodegree_project2.data.MovieUtility;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieRecord;

/**
 * Async task to toggle the state of favorite movie.
 */

public class UpdateFavoriteMovieTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private MovieRecord movieRecord;
    private Boolean performAction;
    private ImageView favImage;

    private static final String LOG_TAG = UpdateFavoriteMovieTask.class.getSimpleName();

    public UpdateFavoriteMovieTask(Context mContext, MovieRecord movieRecord, ImageView favorite) {
        this.context = mContext;
        this.movieRecord = movieRecord;
        this.favImage = favorite;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return MovieUtility.isFavourite(context, movieRecord.getMovieId());
    }


    @Override
    protected void onPostExecute(Boolean isFavorite) {
        if (!isFavorite) {
            ContentValues values = MovieUtility.getContentValues(movieRecord);
            context.getContentResolver().insert(MovieContract.MovieEntry.CONTENT_URI, values);
            if (MovieUtility.isFavourite(context, movieRecord.getMovieId())) {
                favImage.setImageResource(android.R.drawable.btn_star_big_on);
                Toast.makeText(context, context.getString(R.string.add_favorite_msg), Toast.LENGTH_SHORT).show();
            }
        } else {
            context.getContentResolver().delete(
                    MovieContract.MovieEntry.CONTENT_URI,
                    MovieContract.MovieEntry.COLUMN_MOVIE_ID + " = ?",
                    new String[]{movieRecord.getMovieId()}
            );
            if (!MovieUtility.isFavourite(context, movieRecord.getMovieId())) {
                favImage.setImageResource(android.R.drawable.btn_star_big_off);
                Toast.makeText(context, context.getString(R.string.remove_favorite_msg), Toast.LENGTH_SHORT).show();
            }
        }
    }
}

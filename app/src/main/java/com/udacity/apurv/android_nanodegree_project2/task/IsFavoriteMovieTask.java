package com.udacity.apurv.android_nanodegree_project2.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.udacity.apurv.android_nanodegree_project2.data.MovieUtility;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieRecord;

/**
 * Async task to find out whether the movie is favorite or not.
 */

public class IsFavoriteMovieTask extends AsyncTask<Void, Void, Boolean> {
    private Context context;
    private MovieRecord movieRecord;
    private Boolean performAction;
    private ImageView imageView;

    public IsFavoriteMovieTask(Context mContext, MovieRecord movieRecord, ImageView favorite) {
        this.context = mContext;
        this.movieRecord = movieRecord;
        this.imageView = favorite;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return MovieUtility.isFavourite(context, movieRecord.getMovieId());
    }


    @Override
    protected void onPostExecute(Boolean isFavorite) {
            if (isFavorite) {
                imageView.setImageResource(android.R.drawable.btn_star_big_on);
            } else {
                imageView.setImageResource(android.R.drawable.btn_star_big_off);
            }
    }
}
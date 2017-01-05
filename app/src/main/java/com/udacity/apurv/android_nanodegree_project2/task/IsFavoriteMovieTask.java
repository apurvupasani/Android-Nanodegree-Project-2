package com.udacity.apurv.android_nanodegree_project2.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.ImageView;

import com.udacity.apurv.android_nanodegree_project2.R;
import com.udacity.apurv.android_nanodegree_project2.data.Utility;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieRecord;

/**
 * Created by upasa on 12/31/2016.
 */

public class IsFavoriteMovieTask extends AsyncTask<Void, Void, Boolean> {
    private Context mContext;
    private MovieRecord movieRecord;
    private Boolean performAction;
    private ImageView imageView;

    public IsFavoriteMovieTask(Context mContext, MovieRecord movieRecord, ImageView favorite) {
        this.mContext = mContext;
        this.movieRecord = movieRecord;
        this.imageView = favorite;
    }

    @Override
    protected Boolean doInBackground(Void... params) {
        return Utility.isFavourite(mContext, movieRecord.getMovieId());
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
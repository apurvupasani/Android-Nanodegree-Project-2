package com.udacity.apurv.android_nanodegree_project2.task;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Button;
import android.widget.ImageButton;

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
    private Button favButton;

    public IsFavoriteMovieTask(Context mContext, MovieRecord movieRecord, Button favorite) {
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
            if (isFavorite) {
                favButton.setText("Favorite");
            } else {
                favButton.setText("Not Favorite");
            }
    }
}
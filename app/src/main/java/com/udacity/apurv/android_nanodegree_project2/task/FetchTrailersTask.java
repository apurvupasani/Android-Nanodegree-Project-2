package com.udacity.apurv.android_nanodegree_project2.task;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.udacity.apurv.android_nanodegree_project2.adapter.MovieArrayAdapter;
import com.udacity.apurv.android_nanodegree_project2.adapter.MovieReviewAdapter;
import com.udacity.apurv.android_nanodegree_project2.adapter.MovieTrailerAdapter;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieRecord;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieTrailer;
import com.udacity.apurv.android_nanodegree_project2.util.HttpConnectionUtils;
import com.udacity.apurv.android_nanodegree_project2.util.MovieDBJsonUtils;
import com.udacity.apurv.android_nanodegree_project2.util.MovieDBUtils;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import lombok.NonNull;

/**
 * Used to fetch trailers. Is an async task.
 */

public class FetchTrailersTask extends AsyncTask<String, Void, List<MovieTrailer>> {

    private final String LOG_TAG = FetchTrailersTask.class.getSimpleName();

    private MovieTrailerAdapter movieTrailerAdapter;

    private Context context;

    private RecyclerView view;

    public FetchTrailersTask(@NonNull final MovieTrailerAdapter movieTrailerAdapter,
                             @NonNull final Context context,
                             @NonNull final RecyclerView view) {
        this.movieTrailerAdapter = movieTrailerAdapter;
        this.view = view;
        this.context = context;
    }

    @Override
    protected List<MovieTrailer> doInBackground(String... params) {
        String url = MovieDBUtils.getMovieTrailerURL(context, params[0]);
        Log.v(LOG_TAG, url);
        try {
            String popularMovieTrailerJson = HttpConnectionUtils.getAPIData(new URL(url));
            Log.v(LOG_TAG, popularMovieTrailerJson);
            List<MovieTrailer> recordList = MovieDBJsonUtils.getTrailersDataFromJson(context, popularMovieTrailerJson);
            Log.v(LOG_TAG, recordList.toString());
            return recordList;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception while parsing message", e);
            return null;
        }

    }

    @Override
    protected void onPostExecute(List<MovieTrailer> trailers) {
        if (trailers != null && trailers.size() > 0) {
            movieTrailerAdapter = new MovieTrailerAdapter(trailers);
            view.setAdapter(movieTrailerAdapter);
            Log.d(LOG_TAG, "Trailers: " + trailers);
        }

    }
}
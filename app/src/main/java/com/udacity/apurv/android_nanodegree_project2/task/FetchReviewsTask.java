package com.udacity.apurv.android_nanodegree_project2.task;

import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.udacity.apurv.android_nanodegree_project2.adapter.MovieReviewAdapter;
import com.udacity.apurv.android_nanodegree_project2.adapter.MovieTrailerAdapter;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieReview;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieTrailer;
import com.udacity.apurv.android_nanodegree_project2.util.HttpConnectionUtils;
import com.udacity.apurv.android_nanodegree_project2.util.MovieDBJsonUtils;
import com.udacity.apurv.android_nanodegree_project2.util.MovieDBUtils;

import java.net.URL;
import java.util.List;

import lombok.NonNull;

/**
 * Created by upasa on 12/31/2016.
 */

public class FetchReviewsTask extends AsyncTask<String, Void, List<MovieReview>> {

    private final String LOG_TAG = FetchReviewsTask.class.getSimpleName();

    private MovieReviewAdapter movieReviewAdapter;
    private RecyclerView view;

    public FetchReviewsTask(@NonNull final MovieReviewAdapter movieReviewAdapter, @NonNull final RecyclerView view) {
        this.movieReviewAdapter = movieReviewAdapter;
        this.view = view;
    }

    @Override
    protected List<MovieReview> doInBackground(String... params) {
        String url = MovieDBUtils.getMovieReviewsURL(params[0]);
        Log.v(LOG_TAG, url);
        try {
            String popularMovieReviewJson = HttpConnectionUtils.getAPIData(new URL(url));
            Log.v(LOG_TAG, popularMovieReviewJson);
            List<MovieReview> recordList = MovieDBJsonUtils.getReviewsDataFromJson(popularMovieReviewJson);
            Log.v(LOG_TAG, recordList.toString());
            return recordList;
        } catch (Exception e) {
            Log.e(LOG_TAG, "Exception while parsing message", e);
            return null;
        }
    }

    @Override
    protected void onPostExecute(List<MovieReview> reviews) {
        if (reviews != null && reviews.size() > 0) {
            movieReviewAdapter = new MovieReviewAdapter(reviews);
            view.setAdapter(movieReviewAdapter);
            Log.d(LOG_TAG, "Reviews: " + reviews);
        }
    }
}
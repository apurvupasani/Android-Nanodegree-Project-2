package com.udacity.apurv.android_nanodegree_project2.task;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.GridView;
import android.widget.Toast;

import com.udacity.apurv.android_nanodegree_project2.R;
import com.udacity.apurv.android_nanodegree_project2.adapter.MovieArrayAdapter;
import com.udacity.apurv.android_nanodegree_project2.data.MovieContract;
import com.udacity.apurv.android_nanodegree_project2.data.Utility;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieRecord;
import com.udacity.apurv.android_nanodegree_project2.util.HttpConnectionUtils;
import com.udacity.apurv.android_nanodegree_project2.util.MovieDBJsonUtils;
import com.udacity.apurv.android_nanodegree_project2.util.MovieDBUtils;

import java.net.URL;
import java.util.List;

import lombok.NonNull;

public class FetchMoviesTask extends AsyncTask<String, Void, List<MovieRecord>> {

    private MovieArrayAdapter movieArrayAdapter;
    private Context context;
    private GridView view;

    public FetchMoviesTask(@NonNull final MovieArrayAdapter movieArrayAdapter,@NonNull final Context context,@NonNull final GridView view) {
        this.movieArrayAdapter = movieArrayAdapter;
        this.context = context;
        this.view = view;
    }
    private String LOG_TAG = FetchMoviesTask.class.getSimpleName();

    @Override
    protected List<MovieRecord> doInBackground(String... params) {
        Log.d(LOG_TAG, "Params " + params[0] + " " + context.getString(R.string.pref_sort_order_favorites));
        if(params[0].equals(context.getString(R.string.pref_sort_order_favorites))) {
            Log.d(LOG_TAG, "Getting stuff from DB" + Utility.getAllFavorites(context).size());
            return Utility.getAllFavorites(context);
        } else {
            String url = MovieDBUtils.getPopularMoviesURL(params[0]);
            Log.v(LOG_TAG, url);
            try {
                String popularMovieJson = HttpConnectionUtils.getAPIData(new URL(url));
                Log.v(LOG_TAG, popularMovieJson);
                List<MovieRecord> recordList = MovieDBJsonUtils.parseMovieDBJSON(popularMovieJson);
                Log.v(LOG_TAG, recordList.toString());
                return recordList;
            } catch (Exception e) {
                Log.e(LOG_TAG, "Exception while parsing message", e);
                return null;
            }
        }

    }

    @Override
    protected void onPostExecute(List<MovieRecord> result) {
        Log.d(LOG_TAG,"Result for " + result);
        if (result != null) {
            movieArrayAdapter.clear();
            for (MovieRecord record: result) {
                Log.d(LOG_TAG,"Executed for " + record);
                movieArrayAdapter.add(record);
            }
            view.setAdapter(movieArrayAdapter);
        } else {
            Toast.makeText(context, "Unable to show the movie information", Toast.LENGTH_LONG).show();
        }
    }
}
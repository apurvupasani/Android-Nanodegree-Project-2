package com.udacity.apurv.android_nanodegree_project2.fragments;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.udacity.apurv.android_nanodegree_project2.R;
import com.udacity.apurv.android_nanodegree_project2.activities.MovieDetailActivity;
import com.udacity.apurv.android_nanodegree_project2.adapter.MovieArrayAdapter;
import com.udacity.apurv.android_nanodegree_project2.constants.ActivityConstants;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieRecord;
import com.udacity.apurv.android_nanodegree_project2.task.FetchMoviesTask;

import java.util.ArrayList;
import java.util.List;

import static com.udacity.apurv.android_nanodegree_project2.constants.MovieDBAPIConstants.MOVIE_DB_ERROR_MESSAGE;

/**
 * This is the movie listing fragment used to list all the movies based on user preference in a grid view.
 */
public class MovieListingFragment extends Fragment {

    private static final String LOG_TAG = MovieListingFragment.class.getSimpleName();
    private MovieArrayAdapter movieArrayAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final List<MovieRecord> movieRecords = new ArrayList<MovieRecord>();
        movieArrayAdapter = new MovieArrayAdapter(getActivity(), R.layout.movie_listing_fragment, R.id.grid_item_movie_imageview, movieRecords);

        final View rootView = inflater.inflate(R.layout.movie_listing_fragment, container, false);
        final GridView gridView = (GridView) rootView.findViewById(R.id.gridview_movies);
        if (gridView != null && movieArrayAdapter != null) {
            gridView.setAdapter(movieArrayAdapter);
            //For each item, go to appropriate detail page
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final MovieRecord record = movieArrayAdapter.getItem(position);
                    final Intent intent = new Intent(getActivity(), MovieDetailActivity.class)
                            .putExtra(ActivityConstants.MOVIE_RECORD_INTENT, record);
                    startActivity(intent);
                }
            });
        } else {
            Log.w(LOG_TAG, "Unable to display the grid.");
            Toast.makeText(getContext(), MOVIE_DB_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        performPopularMovieTaskExecution();
    }

    private void performPopularMovieTaskExecution() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = preferences.getString(getString(R.string.pref_sort_order_key), "");
        FetchMoviesTask task = new FetchMoviesTask(movieArrayAdapter, getContext());
        task.execute(sortOrder);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movielistingfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
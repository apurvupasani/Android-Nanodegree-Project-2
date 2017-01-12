package com.udacity.apurv.android_nanodegree_project2.fragments;

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
import com.udacity.apurv.android_nanodegree_project2.adapter.MovieArrayAdapter;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieRecord;
import com.udacity.apurv.android_nanodegree_project2.task.FetchMoviesTask;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * This is the movie listing fragment used to list all the movies based on user preference in a grid view.
 */
public class MovieListingFragment extends Fragment {

    private static final String LOG_TAG = MovieListingFragment.class.getSimpleName();

    private MovieArrayAdapter movieArrayAdapter;
    private ArrayList<MovieRecord> movieRecords;
    private String sortSetting = StringUtils.EMPTY;

    private static final String SAVED_SORT_KEY = "SAVED_SORT_KEY";
    private static final String SAVED_MOVIES_KEY = "SAVED_MOVIES_KEY";

    @Bind(R.id.gridview_movies)
    GridView gridView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.movie_listing_fragment, container, false);
        ButterKnife.bind(this, rootView);

        movieRecords = new ArrayList<MovieRecord>();
        movieArrayAdapter = new MovieArrayAdapter(getActivity(), R.layout.movie_listing_fragment, R.id.grid_item_movie_imageview, movieRecords);

        if (gridView != null && movieArrayAdapter != null) {
            gridView.setAdapter(movieArrayAdapter);
            //For each item, go to appropriate detail page
            gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    final MovieRecord record = movieArrayAdapter.getItem(position);
                    ((MovieListingBundleCallback) getActivity()).onMovieItemSelected(record);
                }
            });

            if (savedInstanceState != null) {
                if (savedInstanceState.containsKey(SAVED_SORT_KEY)) {
                    sortSetting = savedInstanceState.getString(SAVED_SORT_KEY);
                }

                if (savedInstanceState.containsKey(SAVED_MOVIES_KEY)) {
                    movieRecords = savedInstanceState.getParcelableArrayList(SAVED_MOVIES_KEY);
                    movieArrayAdapter.addAll(movieRecords);
                } else {
                    performPopularMovieTaskExecution();
                }
            } else {
                performPopularMovieTaskExecution();
            }
        } else {
            Toast.makeText(getContext(), getString(R.string.movie_db_error_message), Toast.LENGTH_LONG).show();
        }
        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (StringUtils.isNotEmpty(sortSetting)) {
            outState.putString(SAVED_SORT_KEY, sortSetting);
        }
        if (movieRecords != null) {
            outState.putParcelableArrayList(SAVED_MOVIES_KEY, movieRecords);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onStart() {
        super.onStart();
        performPopularMovieTaskExecution();
    }

    private void performPopularMovieTaskExecution() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String sortOrder = preferences.getString(getContext().getString(R.string.pref_sort_order_key), StringUtils.EMPTY);
        if (!sortSetting.equals(sortOrder) || sortOrder.equals(getString(R.string.pref_sort_order_favorites))) {
            FetchMoviesTask task = new FetchMoviesTask(movieArrayAdapter, getContext(), gridView, movieRecords);
            task.execute(sortOrder);
            sortSetting = sortOrder;
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_movielistingfragment, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    /**
     * This interface is used to perform on click callback for grid item click.
     */
    public interface MovieListingBundleCallback {
        /**
         * This is used to perform fragment load on grid item click.
         * @param movieRecord
         */
        void onMovieItemSelected(MovieRecord movieRecord);

        /**
         * This is used for removi
         */
        void disableFragmentView();
    }
}
package com.udacity.apurv.android_nanodegree_project2.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.udacity.apurv.android_nanodegree_project2.R;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieRecord;
import com.udacity.apurv.android_nanodegree_project2.fragments.MovieDetailActivityFragment;
import com.udacity.apurv.android_nanodegree_project2.fragments.MovieListingFragment;

/**
 * Contains the main screen activity and operations.
 */
public class MainActivity extends AppCompatActivity implements MovieListingFragment.MovieListingBundleCallback {

    private boolean isTwoPane;
    private static final String DETAIL_FRAGMENT_TAG = "DETAIL_FRAGMENT_TAG";
    private static final String LOG_TAG = MainActivity.class.getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (findViewById(R.id.movie_detail_container) != null) {
            isTwoPane = true;
            if (savedInstanceState == null) {
                Log.d(LOG_TAG, "In Main activity no saved instance");
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.movie_detail_container, new MovieDetailActivityFragment(), DETAIL_FRAGMENT_TAG)
                        .commit();
            }
        } else {
            isTwoPane = false;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onMovieItemSelected(MovieRecord movieRecord) {
        if (isTwoPane) {
            Bundle arguments = new Bundle();
            arguments.putParcelable(getString(R.string.movie_record_args_bundle), movieRecord);

            MovieDetailActivityFragment movieDetailFragment = new MovieDetailActivityFragment();
            movieDetailFragment.setArguments(arguments);

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movie_detail_container, movieDetailFragment, DETAIL_FRAGMENT_TAG)
                    .commit();
            findViewById(R.id.movie_detail_container).setVisibility(View.VISIBLE);
        } else {
            Intent intent = new Intent(this, MovieDetailActivity.class)
                    .putExtra(getString(R.string.movie_record_intent), movieRecord);
            startActivity(intent);
        }
    }

    @Override
    public void disableFragmentView() {
        if (isTwoPane) {
            findViewById(R.id.movie_detail_container).setVisibility(View.INVISIBLE);
        }
    }
}

package com.udacity.apurv.android_nanodegree_project2.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.apurv.android_nanodegree_project2.R;
import com.udacity.apurv.android_nanodegree_project2.adapter.MovieReviewAdapter;
import com.udacity.apurv.android_nanodegree_project2.adapter.MovieTrailerAdapter;
import com.udacity.apurv.android_nanodegree_project2.constants.ActivityConstants;
import com.udacity.apurv.android_nanodegree_project2.constants.MovieDBAPIConstants;
import com.udacity.apurv.android_nanodegree_project2.constants.MovieDBAPIResultConstants;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieRecord;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieReview;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieTrailer;
import com.udacity.apurv.android_nanodegree_project2.task.FetchReviewsTask;
import com.udacity.apurv.android_nanodegree_project2.task.FetchTrailersTask;
import com.udacity.apurv.android_nanodegree_project2.task.IsFavoriteMovieTask;
import com.udacity.apurv.android_nanodegree_project2.task.UpdateFavoriteMovieTask;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

import static com.udacity.apurv.android_nanodegree_project2.constants.MovieDBAPIConstants.MOVIE_DB_ERROR_MESSAGE;
import static com.udacity.apurv.android_nanodegree_project2.util.MovieDBJsonUtils.convertDateToProperFormat;

/**
 * This fragment is used to populate the detail view on load.
 */
public class MovieDetailActivityFragment extends Fragment {

    private static final String LOG_TAG = MovieDetailActivityFragment.class.getSimpleName();

    private static final String VOTE_AVERAGE_MAX_STR = " / 10";

    private static final String SAVED_STATE_OBJECT = "MOVIE_STATE";

    @Bind(R.id.original_title)
    TextView originalTitle;

    @Bind(R.id.overview)
    TextView overview;

    @Bind(R.id.release_date)
    TextView releaseDate;

    @Bind(R.id.user_rating)
    TextView userRating;

    @Bind(R.id.poster_image)
    ImageView imageView;

    @Bind(R.id.favImage)
    ImageView favorite;

    @Bind(R.id.trailers_recycler_view)
    RecyclerView movieTrailersRecyclerView;

    @Bind(R.id.reviews_recycler_view)
    RecyclerView movieReviewsRecyclerView;

    //Required in adapters.
    private String movieId;

    public MovieTrailerAdapter trailerAdapter;
    public MovieReviewAdapter reviewAdapter;
    private MovieRecord movieRecord;

    public MovieDetailActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(SAVED_STATE_OBJECT, movieRecord);
        super.onSaveInstanceState(outState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, rootView);


        Log.v(LOG_TAG, "In movie fragment view");
        if(savedInstanceState == null || !savedInstanceState.containsKey(SAVED_STATE_OBJECT)) {
            //Handle tablet layout args
            if ( getArguments() != null) {
                movieRecord =  getArguments().getParcelable(ActivityConstants.MOVIE_RECORD_ARG_BUNDLE);
            } else {
                Intent intent = getActivity().getIntent();
                if (intent != null && intent.hasExtra(ActivityConstants.MOVIE_RECORD_INTENT)) {
                    movieRecord = (MovieRecord) intent.getParcelableExtra(ActivityConstants.MOVIE_RECORD_INTENT);
                } else {
                    rootView.setVisibility(View.INVISIBLE);
                    return rootView;
                }
            }
        } else {
            //TODO: Put reviews and trailer info in this.
            movieRecord = savedInstanceState.getParcelable(SAVED_STATE_OBJECT);
        }
        //Set the information in appropriate fields
        movieId = movieRecord.getMovieId();
        originalTitle.setText(movieRecord.getOriginalTitle());
        overview.setText(movieRecord.getOverview());
        overview.setMovementMethod(new ScrollingMovementMethod());
        releaseDate.setText(convertDateToProperFormat(movieRecord.getReleaseDate()));
        userRating.setText(movieRecord.getUserRating() + VOTE_AVERAGE_MAX_STR);
        Picasso.with(getContext()).load(MovieDBAPIConstants.MOVIE_DB_IMAGE_BASE_URL_DETAIL + movieRecord.getMovieImageThumbnailPath()).into(imageView);
        new IsFavoriteMovieTask(getActivity(), movieRecord, favorite).execute();
            favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(LOG_TAG, "Movie Record" + movieRecord.getMovieImageThumbnailPath());
                    new UpdateFavoriteMovieTask(getActivity(), movieRecord, favorite).execute();
                }
            });

        final List<MovieTrailer> trailers = new ArrayList<>();
        trailerAdapter = new MovieTrailerAdapter(trailers);
        final List<MovieReview> reviews = new ArrayList<>();
        reviewAdapter = new MovieReviewAdapter(reviews);
        movieTrailersRecyclerView.setAdapter(trailerAdapter);
        movieReviewsRecyclerView.setAdapter(reviewAdapter);
        rootView.setVisibility(View.VISIBLE);
        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        if (movieRecord !=null) {
            fetchTrailers();
            fetchReviews();
        }
    }

    private void fetchReviews() {
        new FetchReviewsTask(reviewAdapter, movieReviewsRecyclerView).execute(movieId);
        Log.d(LOG_TAG, "In fetchReviews" + reviewAdapter.getItemCount());
    }
    private void fetchTrailers() {
        new FetchTrailersTask(trailerAdapter, movieTrailersRecyclerView).execute(movieId);
        Log.d(LOG_TAG, "In fetchTrailers"+ trailerAdapter.getItemCount());
    }


}

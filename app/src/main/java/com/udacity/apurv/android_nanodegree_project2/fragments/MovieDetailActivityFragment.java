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
    Button favorite;

    @Bind(R.id.trailers_recycler_view)
    RecyclerView movieTrailersRecyclerView;

    @Bind(R.id.reviews_recycler_view)
    RecyclerView movieReviewsRecyclerView;

    //Required in adapters.
    private String movieId;

    public MovieTrailerAdapter trailerAdapter;
    public MovieReviewAdapter reviewAdapter;

    public MovieDetailActivityFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, rootView);
        Intent intent = getActivity().getIntent();
        Log.v(LOG_TAG, "In movie fragment view");
        if (intent != null && intent.hasExtra(ActivityConstants.MOVIE_RECORD_INTENT)) {
            final MovieRecord movieRecord = (MovieRecord) intent.getSerializableExtra(ActivityConstants.MOVIE_RECORD_INTENT);

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
                    new UpdateFavoriteMovieTask(getActivity(), movieRecord, favorite).execute();
                }
            });

            final List<MovieTrailer> trailers = new ArrayList<>();
            trailerAdapter = new MovieTrailerAdapter(trailers);
            final List<MovieReview> reviews = new ArrayList<>();
            reviewAdapter = new MovieReviewAdapter(reviews);
            movieTrailersRecyclerView.setAdapter(trailerAdapter);
            movieReviewsRecyclerView.setAdapter(reviewAdapter);

        } else {
            Toast.makeText(getContext(), MOVIE_DB_ERROR_MESSAGE, Toast.LENGTH_LONG).show();
        }

        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        fetchTrailers();
        fetchReviews();
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

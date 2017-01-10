package com.udacity.apurv.android_nanodegree_project2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.udacity.apurv.android_nanodegree_project2.R;
import com.udacity.apurv.android_nanodegree_project2.adapter.MovieReviewAdapter;
import com.udacity.apurv.android_nanodegree_project2.adapter.MovieTrailerAdapter;
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

import static com.udacity.apurv.android_nanodegree_project2.util.MovieDBJsonUtils.convertDateToProperFormat;

/**
 * This fragment is used to populate the detail view on load.
 */
public class MovieDetailActivityFragment extends Fragment {

    private static final String LOG_TAG = MovieDetailActivityFragment.class.getSimpleName();

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
        outState.putParcelable(getString(R.string.detail_saved_state), movieRecord);
        super.onSaveInstanceState(outState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.fragment_movie_detail, container, false);
        ButterKnife.bind(this, rootView);

        if(savedInstanceState == null || !savedInstanceState.containsKey(getString(R.string.detail_saved_state))) {
            //Handle tablet layout args
            if ( getArguments() != null) {
                movieRecord =  getArguments().getParcelable(getString(R.string.movie_record_args_bundle));
            } else {
                Intent intent = getActivity().getIntent();
                if (intent != null && intent.hasExtra(getString(R.string.movie_record_intent))) {
                    movieRecord = (MovieRecord) intent.getParcelableExtra(getString(R.string.movie_record_intent));
                } else {
                    rootView.setVisibility(View.INVISIBLE);
                    return rootView;
                }
            }
        } else {
            movieRecord = savedInstanceState.getParcelable(getString(R.string.detail_saved_state));
        }
        //Set the information in appropriate fields
        movieId = movieRecord.getMovieId();
        originalTitle.setText(movieRecord.getOriginalTitle());
        overview.setText(movieRecord.getOverview());
        overview.setMovementMethod(new ScrollingMovementMethod());
        releaseDate.setText(convertDateToProperFormat(getContext(), movieRecord.getReleaseDate()));
        userRating.setText(getString(R.string.voting_average, movieRecord.getUserRating()));

        Picasso.with(getContext())
                .load(getString(R.string.movie_db_image_base_url_detail,movieRecord.getMovieImageThumbnailPath()))
                .into(imageView);

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
        rootView.setVisibility(View.VISIBLE);
        return rootView;
    }
    @Override
    public void onStart() {
        super.onStart();
        if (movieRecord != null) {
            fetchTrailers();
            fetchReviews();
        }
    }

    private void fetchReviews() {
        new FetchReviewsTask(reviewAdapter, getContext(), movieReviewsRecyclerView).execute(movieId);
    }
    private void fetchTrailers() {
        new FetchTrailersTask(trailerAdapter, getContext(), movieTrailersRecyclerView).execute(movieId);
    }


}

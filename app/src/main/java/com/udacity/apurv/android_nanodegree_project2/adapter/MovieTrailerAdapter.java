package com.udacity.apurv.android_nanodegree_project2.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.udacity.apurv.android_nanodegree_project2.R;

import com.udacity.apurv.android_nanodegree_project2.constants.MovieDBAPIConstants;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieReview;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieTrailer;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Getter;

/**
 * Created by upasa on 12/31/2016.
 */

public class MovieTrailerAdapter extends RecyclerView.Adapter<MovieTrailerAdapter.ViewHolder> {

    private static String LOG_TAG = MovieReviewAdapter.class.getSimpleName();
    private List<MovieTrailer> movieTrailers;

    public MovieTrailerAdapter(List<MovieTrailer> trailers) {
        Log.d(LOG_TAG, "Number of trailers" + trailers.size());
        this.movieTrailers = trailers;
    }

    @Override
    public MovieTrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_trailer_item, parent, false);
        Log.d(LOG_TAG, "In On CreateViewHolder" );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieTrailerAdapter.ViewHolder holder, final int position) {
        final MovieTrailer trailer = movieTrailers.get(position);
        holder.getName().setText(trailer.getTrailerName());
        Log.d(LOG_TAG, "In On BindViewHolder" );
        final Context c = holder.name.getContext();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String youtubeVideoId = movieTrailers.get(position).getTrailerKey();
                String videoURI = MovieDBAPIConstants.MOVIE_DB_YOUTUBE_URL + youtubeVideoId;
                Intent i = new Intent(Intent.ACTION_VIEW, Uri.parse(videoURI));
                c.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return movieTrailers.size();
    }
    @Getter
    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.trailer_name)
        TextView name;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

}
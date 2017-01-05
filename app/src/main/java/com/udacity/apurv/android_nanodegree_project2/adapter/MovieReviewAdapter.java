package com.udacity.apurv.android_nanodegree_project2.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import lombok.Getter;

import com.udacity.apurv.android_nanodegree_project2.R;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieReview;

import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * Created by upasa on 12/31/2016.
 */

public class MovieReviewAdapter extends RecyclerView.Adapter<MovieReviewAdapter.ViewHolder> {

    private static String LOG_TAG = MovieReviewAdapter.class.getSimpleName();
    private List<MovieReview> movieReviews;

    public MovieReviewAdapter( List<MovieReview> reviews) {
        Log.d(LOG_TAG, "Number of reviews" + reviews.size());
        this.movieReviews = reviews;
    }

    @Override
    public MovieReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_review_item, parent, false);
        Log.d(LOG_TAG, "In On CreateViewHolder" );
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MovieReviewAdapter.ViewHolder holder, int position) {
        final MovieReview review = movieReviews.get(position);
        holder.getAuthor().setText(StringUtils.capitalize(review.getAuthor()));
        holder.getContent().setText(review.getContent());
        Log.d(LOG_TAG, "In On BindViewHolder" );
    }

    @Override
    public int getItemCount() {
        return movieReviews.size();
    }
    @Getter
    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.review_author_title)
        TextView author;

        @Bind(R.id.review_content)
        TextView content;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }

}
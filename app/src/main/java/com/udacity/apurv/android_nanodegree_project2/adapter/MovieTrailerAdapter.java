package com.udacity.apurv.android_nanodegree_project2.adapter;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupMenu;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import com.udacity.apurv.android_nanodegree_project2.R;
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
        this.movieTrailers = trailers;
    }

    @Override
    public MovieTrailerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.movie_trailer_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final MovieTrailerAdapter.ViewHolder holder, final int position) {
        final MovieTrailer trailer = movieTrailers.get(position);
        holder.getName().setText(trailer.getTrailerName());
        final Context c = holder.name.getContext();
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PopupMenu popup = new PopupMenu(c, v);
                popup.getMenuInflater().inflate(R.menu.menu_trailer, popup.getMenu());
                popup.setOnMenuItemClickListener(new android.widget.PopupMenu.OnMenuItemClickListener() {

                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        if (item.getTitle().equals("View")) {
                            return viewYoutubeVideo(c, position);
                        } else if (item.getTitle().equals("Share")) {
                            return shareYoutubeURL(c, position, item);
                        }
                        else {
                            return false;
                        }

                    }
                });
                popup.show();
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

    private boolean viewYoutubeVideo(final Context context, final int position) {
        String youtubeVideoId = movieTrailers.get(position).getTrailerKey();
        try {
            Intent appIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context.getString(R.string.movie_db_youtube_url, youtubeVideoId)));
            context.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            Intent webIntent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse(context.getString(R.string.movie_db_youtube_web_url, youtubeVideoId)));
            context.startActivity(webIntent);
        }
        return true;
    }

    private boolean shareYoutubeURL(final Context context, final int position, final MenuItem menuItem) {
        String youtubeVideoId = movieTrailers.get(position).getTrailerKey();
        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
        shareIntent.setType(context.getString(R.string.movie_db_youtube_share_type));
        shareIntent.putExtra(Intent.EXTRA_TEXT, context.getString(R.string.movie_db_youtube_web_url, youtubeVideoId));
        ShareActionProvider shareActionProvider = (ShareActionProvider) menuItem.getActionProvider();
        shareActionProvider.setShareIntent(shareIntent);
        return true;
    }

}
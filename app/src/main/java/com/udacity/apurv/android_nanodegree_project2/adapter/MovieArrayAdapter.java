package com.udacity.apurv.android_nanodegree_project2.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.udacity.apurv.android_nanodegree_project2.R;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieRecord;

import java.util.List;

/**
 * This is a customized adapter used for storing and presenting the image poster.
 */

public class MovieArrayAdapter extends ArrayAdapter<MovieRecord> {
    private static final String LOG_TAG = MovieArrayAdapter.class.getSimpleName();
    private Context context;
    public MovieArrayAdapter(Context context, int layout, int id, List<MovieRecord> movieRecordList) {
        super(context, layout, id, movieRecordList);
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MovieRecord movieRecord = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grid_item_movie, parent, false);
        }
        ImageView imageView = (ImageView) convertView.findViewById(R.id.grid_item_movie_imageview);

        Picasso.with(getContext()).load(context.getString(R.string.movie_db_image_base_url_main, movieRecord.getMovieImageThumbnailPath())).into(imageView);
        return convertView;
    }
}

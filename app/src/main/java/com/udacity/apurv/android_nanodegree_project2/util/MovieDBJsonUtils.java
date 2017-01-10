package com.udacity.apurv.android_nanodegree_project2.util;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.udacity.apurv.android_nanodegree_project2.R;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieRecord;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieReview;
import com.udacity.apurv.android_nanodegree_project2.entities.MovieTrailer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * API used to read the JSON information and parse it appropriately
 */

public class MovieDBJsonUtils {
    private static final String LOG_TAG = MovieDBJsonUtils.class.getSimpleName();

    public static List<MovieRecord> parseMovieDBJSON(@NonNull final Context context,
                                                     @NonNull final String jsonString) throws JSONException {

        List<MovieRecord> movieRecords = new ArrayList<>();

        JSONObject movieListObject = new JSONObject(jsonString);
        JSONArray results = movieListObject.getJSONArray(context.getString(R.string.movie_results_field));

        for(int i = 0; i < results.length(); i++) {

            try {
                MovieRecord movieRecord = new MovieRecord();
                JSONObject result = results.getJSONObject(i);
                movieRecord.setMovieId(result.getString(context.getString(R.string.id_field)));
                movieRecord.setMovieImageThumbnailPath(result.getString(context.getString(R.string.thumbnail_field)));
                movieRecord.setOriginalTitle(result.getString(context.getString(R.string.original_title_field)));
                movieRecord.setOverview(result.getString(context.getString(R.string.overview_field)));
                movieRecord.setReleaseDate(result.getString(context.getString(R.string.release_date_field)));
                movieRecord.setUserRating(result.getDouble(context.getString(R.string.vote_average_field)));
                movieRecords.add(movieRecord);
            } catch (JSONException ex) {
                Log.e(LOG_TAG, "Exception in handling input json", ex);
            }

        }
        return movieRecords;
    }

    public static List<MovieTrailer> getTrailersDataFromJson(@NonNull final Context context,
                                                             @NonNull final String jsonString) throws JSONException {
        final JSONObject trailerJson = new JSONObject(jsonString);
        final JSONArray trailerArray = trailerJson.getJSONArray(context.getString(R.string.movie_results_field));
        final List<MovieTrailer> results = new ArrayList<>();

        for(int i = 0; i < trailerArray.length(); i++) {
            JSONObject trailerJsonObject = trailerArray.getJSONObject(i);
            // Only show Trailers which are on Youtube
            if (trailerJsonObject.getString(context.getString(R.string.trailer_site))
                    .contentEquals(context.getString(R.string.trailer_site_youtube))){
                MovieTrailer movieTrailer = new MovieTrailer();
                movieTrailer.setTrailerId(trailerJsonObject.getString(context.getString(R.string.id_field)));
                movieTrailer.setTrailerKey(trailerJsonObject.getString(context.getString(R.string.trailer_key)));
                movieTrailer.setTrailerName(trailerJsonObject.getString(context.getString(R.string.trailer_name)));
                movieTrailer.setTrailerSite(trailerJsonObject.getString(context.getString(R.string.trailer_site)));
                movieTrailer.setTrailerSize(trailerJsonObject.getString(context.getString(R.string.trailer_size)));
                movieTrailer.setTrailerType(trailerJsonObject.getString(context.getString(R.string.trailer_type)));
                results.add(movieTrailer);
            }

        }
        return results;
    }

    public static List<MovieReview> getReviewsDataFromJson(@NonNull final Context context,
                                                           @NonNull final String jsonString) throws JSONException {
        JSONObject reviewJson = new JSONObject(jsonString);
        JSONArray reviewArray = reviewJson.getJSONArray(context.getString(R.string.movie_results_field));
        List<MovieReview> results = new ArrayList<>();
        for(int i = 0; i < reviewArray.length(); i++) {
            JSONObject review = reviewArray.getJSONObject(i);
            MovieReview movieReview = new MovieReview();
            movieReview.setId(review.getString(context.getString(R.string.id_field)));
            movieReview.setContent(review.getString(context.getString(R.string.review_content)));
            movieReview.setAuthor(review.getString(context.getString(R.string.review_author)));
            results.add(movieReview);
        }

        return results;
    }


    public static final String convertDateToProperFormat(@NonNull final Context context, @NonNull final String date) {

        DateFormat yyFormat = new SimpleDateFormat(context.getString(R.string.input_date_format));
        DateFormat outputFormat = new SimpleDateFormat(context.getString(R.string.output_date_format));
        Date sysDate = null;
        try {
            sysDate = yyFormat.parse(date);
            return outputFormat.format(sysDate);
        } catch (ParseException ex) {
            Log.e(LOG_TAG, "Unable to parse input date: " + date, ex);
            return date;
        }

    }
}

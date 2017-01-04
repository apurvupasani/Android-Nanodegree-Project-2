package com.udacity.apurv.android_nanodegree_project2.util;

import android.support.annotation.NonNull;
import android.util.Log;

import com.udacity.apurv.android_nanodegree_project2.constants.MovieDBAPIConstants;
import com.udacity.apurv.android_nanodegree_project2.constants.MovieDBAPIResultConstants;
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

import static com.udacity.apurv.android_nanodegree_project2.constants.DateConstants.INPUT_DATE_FORMAT_STRING;
import static com.udacity.apurv.android_nanodegree_project2.constants.DateConstants.OUTPUT_DATE_FORMAT_STRING;

/**
 * API used to read the JSON information and parse it appropriately
 */

public class MovieDBJsonUtils {
    private static final String LOG_TAG = MovieDBJsonUtils.class.getSimpleName();

    public static List<MovieRecord> parseMovieDBJSON(@NonNull final String jsonString) throws JSONException {

        List<MovieRecord> movieRecords = new ArrayList<>();

        JSONObject movieListObject = new JSONObject(jsonString);
        JSONArray results = movieListObject.getJSONArray(MovieDBAPIResultConstants.RESULTS_FIELD);

        for(int i = 0; i < results.length(); i++) {

            try {
                MovieRecord movieRecord = new MovieRecord();
                JSONObject result = results.getJSONObject(i);
                movieRecord.setMovieId(result.getString(MovieDBAPIResultConstants.MOVIE_ID));
                movieRecord.setMovieImageThumbnailPath(result.getString(MovieDBAPIResultConstants.THUMBNAIL_FIELD));
                movieRecord.setOriginalTitle(result.getString(MovieDBAPIResultConstants.ORIGINAL_TITLE_FIELD));
                movieRecord.setOverview(result.getString(MovieDBAPIResultConstants.OVERVIEW_FIELD));
                movieRecord.setReleaseDate(result.getString(MovieDBAPIResultConstants.RELEASE_DATE_FIELD));
                movieRecord.setUserRating(result.getDouble(MovieDBAPIResultConstants.USER_RATING_FIELD));
                movieRecords.add(movieRecord);
            } catch (JSONException ex) {
                Log.e(LOG_TAG, "Exception in handling input json", ex);
            }

        }

        return movieRecords;
    }

    public static List<MovieTrailer> getTrailersDataFromJson(String jsonStr) throws JSONException {
        final JSONObject trailerJson = new JSONObject(jsonStr);
        final JSONArray trailerArray = trailerJson.getJSONArray(MovieDBAPIResultConstants.RESULTS_FIELD);
        final List<MovieTrailer> results = new ArrayList<>();

        for(int i = 0; i < trailerArray.length(); i++) {
            JSONObject trailerJsonObject = trailerArray.getJSONObject(i);
            // Only show Trailers which are on Youtube
            if (trailerJsonObject.getString(MovieDBAPIResultConstants.TRAILER_SITE).contentEquals(MovieDBAPIResultConstants.TRAILER_SITE_YOUTUBE)){
                MovieTrailer movieTrailer = new MovieTrailer();
                movieTrailer.setTrailerId(trailerJsonObject.getString(MovieDBAPIResultConstants.TRAILER_ID));
                movieTrailer.setTrailerKey(trailerJsonObject.getString(MovieDBAPIResultConstants.TRAILER_KEY));
                movieTrailer.setTrailerName(trailerJsonObject.getString(MovieDBAPIResultConstants.TRAILER_NAME));
                movieTrailer.setTrailerSite(trailerJsonObject.getString(MovieDBAPIResultConstants.TRAILER_SITE));
                movieTrailer.setTrailerSize(trailerJsonObject.getString(MovieDBAPIResultConstants.TRAILER_SIZE));
                movieTrailer.setTrailerType(trailerJsonObject.getString(MovieDBAPIResultConstants.TRAILER_TYPE));
                results.add(movieTrailer);
            }

        }
        return results;
    }

    public static List<MovieReview> getReviewsDataFromJson(String jsonStr) throws JSONException {
        JSONObject reviewJson = new JSONObject(jsonStr);
        JSONArray reviewArray = reviewJson.getJSONArray("results");
        List<MovieReview> results = new ArrayList<>();
        for(int i = 0; i < reviewArray.length(); i++) {
            JSONObject review = reviewArray.getJSONObject(i);
            MovieReview movieReview = new MovieReview();
            movieReview.setId(review.getString(MovieDBAPIResultConstants.REVIEW_ID));
            movieReview.setContent(review.getString(MovieDBAPIResultConstants.REVIEW_CONTENT));
            movieReview.setAuthor(review.getString(MovieDBAPIResultConstants.REVIEW_AUTHOR));
            results.add(movieReview);
        }

        return results;
    }


    public static final String convertDateToProperFormat(@NonNull final String date) {
        DateFormat yyFormat = new SimpleDateFormat(INPUT_DATE_FORMAT_STRING);
        DateFormat outputFormat = new SimpleDateFormat(OUTPUT_DATE_FORMAT_STRING);
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

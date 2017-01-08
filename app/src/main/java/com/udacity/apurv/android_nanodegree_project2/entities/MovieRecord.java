package com.udacity.apurv.android_nanodegree_project2.entities;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * POJO for MovieRecord. Implements serializable as it needs to be passed around via Intents.
 */
@Data
@ToString
public class MovieRecord implements Parcelable {
    private String originalTitle;
    private String movieImageThumbnailPath;
    private String overview;
    private Double userRating;
    private String releaseDate;
    private String movieId;

    public MovieRecord () { }

    public MovieRecord(Parcel in) {
        movieId = in.readString();
        overview = in.readString();
        releaseDate = in.readString();
        originalTitle = in.readString();
        userRating = in.readDouble();
        movieImageThumbnailPath = in.readString();
    }

    public static final Parcelable.Creator<MovieRecord> CREATOR = new Parcelable.Creator<MovieRecord>() {
        public MovieRecord createFromParcel(Parcel in) {
            return new MovieRecord(in);
        }

        public MovieRecord[] newArray(int size) {
            return new MovieRecord[size];
        }
    };


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(movieId);
        dest.writeString(overview);
        dest.writeString(releaseDate);
        dest.writeString(originalTitle);
        dest.writeDouble(userRating);
        dest.writeString(movieImageThumbnailPath);
    }
}

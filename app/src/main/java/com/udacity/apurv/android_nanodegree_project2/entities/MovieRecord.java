package com.udacity.apurv.android_nanodegree_project2.entities;

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
public class MovieRecord implements Serializable {
    private String originalTitle;
    private String movieImageThumbnailPath;
    private String overview;
    private Double userRating;
    private String releaseDate;
    private String movieId;
}

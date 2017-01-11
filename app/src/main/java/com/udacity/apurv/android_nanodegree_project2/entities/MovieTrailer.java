package com.udacity.apurv.android_nanodegree_project2.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This is a model used to store trailer information.
 */
@Data
@ToString
public class MovieTrailer {
    private String trailerId;
    private String trailerKey;
    private String trailerName;
    private String trailerSite;
    private String trailerSize;
    private String trailerType;
}

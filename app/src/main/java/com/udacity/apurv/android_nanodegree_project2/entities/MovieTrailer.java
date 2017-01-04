package com.udacity.apurv.android_nanodegree_project2.entities;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * This is a model used to store trailer information.
 * Created by upasa on 12/31/2016.
 */
@Data
@ToString
public class MovieTrailer implements Serializable {
    private String trailerId;
    private String trailerKey;
    private String trailerName;
    private String trailerSite;
    private String trailerSize;
    private String trailerType;
}

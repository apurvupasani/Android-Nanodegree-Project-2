package com.udacity.apurv.android_nanodegree_project2.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * Created by upasa on 12/31/2016.
 */
@Data
@ToString
public class MovieReview {
    private String id;
    private String author;
    private String content;
}

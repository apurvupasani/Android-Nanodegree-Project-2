package com.udacity.apurv.android_nanodegree_project2.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * POJO for movie review.
 */
@Data
@ToString
public class MovieReview {
    private String id;
    private String author;
    private String content;
}

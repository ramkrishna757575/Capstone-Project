package com.ram.capstone.capstone_project.models;

import com.google.gson.annotations.SerializedName;
import com.squareup.moshi.Json;

/**
 * Created by ramkrishna on 4/3/17.
 */

public class UserRating {
    @Json(name = "aggregate_rating")
    @SerializedName("aggregate_rating")
    float aggregateRating;

    @Json(name = "rating_text")
    @SerializedName("rating_text")
    String ratingText;

    @Json(name = "votes")
    @SerializedName("votes")
    int votes;

    public float getAggregateRating() {
        return aggregateRating;
    }

    public String getRatingText() {
        return ratingText;
    }

    public int getVotes() {
        return votes;
    }
}

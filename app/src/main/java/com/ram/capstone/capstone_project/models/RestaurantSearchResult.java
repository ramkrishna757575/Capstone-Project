package com.ram.capstone.capstone_project.models;

import com.google.gson.annotations.SerializedName;
import com.squareup.moshi.Json;

import java.util.List;

/**
 * Created by ramkrishna on 4/3/17.
 */

public class RestaurantSearchResult {
    @Json(name = "results_found")
    @SerializedName("results_found")
    int resultCount;

    @Json(name = "results_start")
    @SerializedName("results_start")
    int resultStart;

    @Json(name = "results_shown")
    @SerializedName("results_shown")
    int resultsShown;

    @Json(name = "restaurants")
    @SerializedName("restaurants")
    List<RestaurantContainer> restaurantsContainer;

    public int getResultCount() {
        return resultCount;
    }

    public int getResultStart() {
        return resultStart;
    }

    public int getResultsShown() {
        return resultsShown;
    }

    public List<RestaurantContainer> getRestaurantsContainer() {
        return restaurantsContainer;
    }
}

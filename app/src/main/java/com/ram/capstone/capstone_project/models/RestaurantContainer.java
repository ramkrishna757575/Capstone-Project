package com.ram.capstone.capstone_project.models;

import com.google.gson.annotations.SerializedName;
import com.squareup.moshi.Json;

/**
 * Created by ramkrishna on 5/3/17.
 */

public class RestaurantContainer {
    @Json(name = "restaurant")
    @SerializedName("restaurant")
    Restaurant restaurant;

    public Restaurant getRestaurant() {
        return restaurant;
    }
}

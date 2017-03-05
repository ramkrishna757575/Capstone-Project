package com.ram.capstone.capstone_project.api;


import com.ram.capstone.capstone_project.models.RestaurantSearchResult;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Query;

/**
 * Created by ramkrishna on 26/2/17.
 */

public interface ZomatoApiInterface {
    @GET("search")
    Call<RestaurantSearchResult> searchRestaurants(
            @Header("user-key") String apiKey,
            @Query("radius") int radius,
            @Query("lat") String latitude,
            @Query("lon") String longitude,
            @Query("start") int start,
            @Query("count") int pageSize,
            @Query("q") String query
    );
}

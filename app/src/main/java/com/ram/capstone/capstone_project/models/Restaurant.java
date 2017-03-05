package com.ram.capstone.capstone_project.models;

import com.google.gson.annotations.SerializedName;
import com.squareup.moshi.Json;

/**
 * Created by ramkrishna on 4/3/17.
 */

public class Restaurant {
    @Json(name = "id")
    @SerializedName("id")
    int id;

    @Json(name = "name")
    @SerializedName("name")
    String name;

    @Json(name = "url")
    @SerializedName("url")
    String url;

    @Json(name = "location")
    @SerializedName("location")
    Location location;

    @Json(name = "average_cost_for_two")
    @SerializedName("average_cost_for_two")
    int AvgCostForTwo;

    @Json(name = "price_range")
    @SerializedName("price_range")
    int priceRange;

    @Json(name = "currency")
    @SerializedName("currency")
    String currency;

    @Json(name = "thumb")
    @SerializedName("thumb")
    String thumb;

    @Json(name = "featured_image")
    @SerializedName("featured_image")
    String featuredImage;

    @Json(name = "photos_url")
    @SerializedName("photos_url")
    String photosUrl;

    @Json(name = "menu_url")
    @SerializedName("menu_url")
    String menuUrl;

    @Json(name = "events_url")
    @SerializedName("events_url")
    String eventUrl;

    @Json(name = "user_rating")
    @SerializedName("user_rating")
    UserRating userRating;

    @Json(name = "has_online_delivery")
    @SerializedName("has_online_delivery")
    int hasOnlineDelivery;

    @Json(name = "is_delivering_now")
    @SerializedName("is_delivering_now")
    int isDeliveringNow;

    @Json(name = "has_table_booking")
    @SerializedName("has_table_booking")
    int hasTableBooking;

    @Json(name = "cuisines")
    @SerializedName("cuisines")
    String cuisines;

    @Json(name = "deeplink")
    @SerializedName("deeplink")
    String deeplink;

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUrl() {
        return url;
    }

    public Location getLocation() {
        return location;
    }

    public int getAvgCostForTwo() {
        return AvgCostForTwo;
    }

    public int getPriceRange() {
        return priceRange;
    }

    public String getCurrency() {
        return currency;
    }

    public String getThumb() {
        return thumb;
    }

    public String getFeaturedImage() {
        return featuredImage;
    }

    public String getPhotosUrl() {
        return photosUrl;
    }

    public String getMenuUrl() {
        return menuUrl;
    }

    public String getEventUrl() {
        return eventUrl;
    }

    public UserRating getUserRating() {
        return userRating;
    }

    public boolean isHasOnlineDelivery() {
        if (hasOnlineDelivery == 0)
            return false;
        else
            return true;
    }

    public boolean isDeliveringNow() {
        if (isDeliveringNow == 0)
            return false;
        else
            return true;
    }

    public boolean isHasTableBooking() {
        if(hasTableBooking == 0)
            return false;
        else
            return true;
    }

    public String getCuisines() {
        return cuisines;
    }

    public String getDeeplink() {
        return deeplink;
    }
}

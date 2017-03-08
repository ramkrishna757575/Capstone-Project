package com.ram.capstone.capstone_project.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.squareup.moshi.Json;

/**
 * Created by ramkrishna on 4/3/17.
 */

public class Restaurant implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.url);
        dest.writeParcelable(this.location, flags);
        dest.writeInt(this.AvgCostForTwo);
        dest.writeInt(this.priceRange);
        dest.writeString(this.currency);
        dest.writeString(this.thumb);
        dest.writeString(this.featuredImage);
        dest.writeString(this.photosUrl);
        dest.writeString(this.menuUrl);
        dest.writeString(this.eventUrl);
        dest.writeParcelable(this.userRating, flags);
        dest.writeInt(this.hasOnlineDelivery);
        dest.writeInt(this.isDeliveringNow);
        dest.writeInt(this.hasTableBooking);
        dest.writeString(this.cuisines);
        dest.writeString(this.deeplink);
    }

    public Restaurant() {
    }

    protected Restaurant(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.url = in.readString();
        this.location = in.readParcelable(Location.class.getClassLoader());
        this.AvgCostForTwo = in.readInt();
        this.priceRange = in.readInt();
        this.currency = in.readString();
        this.thumb = in.readString();
        this.featuredImage = in.readString();
        this.photosUrl = in.readString();
        this.menuUrl = in.readString();
        this.eventUrl = in.readString();
        this.userRating = in.readParcelable(UserRating.class.getClassLoader());
        this.hasOnlineDelivery = in.readInt();
        this.isDeliveringNow = in.readInt();
        this.hasTableBooking = in.readInt();
        this.cuisines = in.readString();
        this.deeplink = in.readString();
    }

    public static final Parcelable.Creator<Restaurant> CREATOR = new Parcelable.Creator<Restaurant>() {
        @Override
        public Restaurant createFromParcel(Parcel source) {
            return new Restaurant(source);
        }

        @Override
        public Restaurant[] newArray(int size) {
            return new Restaurant[size];
        }
    };
}

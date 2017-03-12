package com.ram.capstone.capstone_project.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.squareup.moshi.Json;

/**
 * Created by ramkrishna on 4/3/17.
 */

public class UserRating implements Parcelable {
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

    public void setAggregateRating(float aggregateRating) {
        this.aggregateRating = aggregateRating;
    }

    public void setRatingText(String ratingText) {
        this.ratingText = ratingText;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeFloat(this.aggregateRating);
        dest.writeString(this.ratingText);
        dest.writeInt(this.votes);
    }

    public UserRating() {
    }

    protected UserRating(Parcel in) {
        this.aggregateRating = in.readFloat();
        this.ratingText = in.readString();
        this.votes = in.readInt();
    }

    public static final Parcelable.Creator<UserRating> CREATOR = new Parcelable.Creator<UserRating>() {
        @Override
        public UserRating createFromParcel(Parcel source) {
            return new UserRating(source);
        }

        @Override
        public UserRating[] newArray(int size) {
            return new UserRating[size];
        }
    };
}

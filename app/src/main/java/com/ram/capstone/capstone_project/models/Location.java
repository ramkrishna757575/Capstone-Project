package com.ram.capstone.capstone_project.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;
import com.squareup.moshi.Json;

/**
 * Created by ramkrishna on 4/3/17.
 */

public class Location implements Parcelable {
    @SerializedName("address")
    @Json(name = "address")
    String address;

    @Json(name = "locality")
    @SerializedName("locality")
    String locality;

    @Json(name = "city")
    @SerializedName("city")
    String city;

    @Json(name = "latitude")
    @SerializedName("latitude")
    String latitude;

    @Json(name = "longitude")
    @SerializedName("longitude")
    String longitude;

    @Json(name = "zipcode")
    @SerializedName("zipcode")
    String zipcode;

    @Json(name = "country_id")
    @SerializedName("country_id")
    int countryId;

    @Json(name = "locality_verbose")
    @SerializedName("locality_verbose")
    String localityVerbose;

    public String getLocalityVerbose() {
        return localityVerbose;
    }

    public String getAddress() {
        return address;
    }

    public String getLocality() {
        return locality;
    }

    public String getCity() {
        return city;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getZipcode() {
        return zipcode;
    }

    public int getCountryId() {
        return countryId;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setLocality(String locality) {
        this.locality = locality;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public void setCountryId(int countryId) {
        this.countryId = countryId;
    }

    public void setLocalityVerbose(String localityVerbose) {
        this.localityVerbose = localityVerbose;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.address);
        dest.writeString(this.locality);
        dest.writeString(this.city);
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeString(this.zipcode);
        dest.writeInt(this.countryId);
        dest.writeString(this.localityVerbose);
    }

    public Location() {
    }

    protected Location(Parcel in) {
        this.address = in.readString();
        this.locality = in.readString();
        this.city = in.readString();
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.zipcode = in.readString();
        this.countryId = in.readInt();
        this.localityVerbose = in.readString();
    }

    public static final Parcelable.Creator<Location> CREATOR = new Parcelable.Creator<Location>() {
        @Override
        public Location createFromParcel(Parcel source) {
            return new Location(source);
        }

        @Override
        public Location[] newArray(int size) {
            return new Location[size];
        }
    };
}

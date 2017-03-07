package com.ram.capstone.capstone_project.models;

import com.google.gson.annotations.SerializedName;
import com.squareup.moshi.Json;

/**
 * Created by ramkrishna on 4/3/17.
 */

public class Location {
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
}

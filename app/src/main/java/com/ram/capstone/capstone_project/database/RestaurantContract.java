package com.ram.capstone.capstone_project.database;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.net.Uri;
import android.provider.BaseColumns;

/**
 * Created by ramkrishna on 11/3/17.
 * Contract for @{@link RestaurantProvider}
 */

public class RestaurantContract {
    public static final String CONTENT_AUTHORITY = "com.ram.capstone.capstone_project";
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    public static final String PATH_RESTAURANT = "restaurants";
    public static final String PATH_LOCATION = "locations";
    public static final String PATH_USER_RATING = "user_ratings";

    /* Inner class that defines the table contents of the restaurant table */
    public static final class RestaurantEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RESTAURANT).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESTAURANT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_RESTAURANT;
        // Table name
        public static final String TABLE_NAME = "restaurants";
        public static final String COLUMN_ID = "id";
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_URL = "url";
        public static final String COLUMN_AVG_COST = "average_cost_for_two";
        public static final String COLUMN_PRICE_RANGE = "price_range";
        public static final String COLUMN_CURRENCY = "currency";
        public static final String COLUMN_THUMB = "thumb";
        public static final String COLUMN_FEATURED_IMAGE = "featured_image";
        public static final String COLUMN_PHOTOS_URL = "photos_url";
        public static final String COLUMN_MENU_URL = "menu_url";
        public static final String COLUMN_EVENTS_URL = "events_url";
        public static final String COLUMN_ONLINE_DELIVERY = "has_online_delivery";
        public static final String COLUMN_DELIVERING_NOW = "is_delivering_now";
        public static final String COLUMN_TABLE_BOOKING = "has_table_booking";
        public static final String COLUMN_CUISINES = "cuisines";
        public static final String COLUMN_DEEPLINK = "deeplink";

        public static Uri buildRestaurantUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /* Inner class that defines the table contents of the locations table */
    public static final class LocationEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_LOCATION).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_LOCATION;
        // Table name
        public static final String TABLE_NAME = "locations";
        public static final String COLUMN_RESTAURANT_ID = "restaurant_id";
        public static final String COLUMN_ADDRESS = "address";
        public static final String COLUMN_LOCALITY = "locality";
        public static final String COLUMN_CITY = "city";
        public static final String COLUMN_LATITUDE = "latitude";
        public static final String COLUMN_LONGITUDE = "longitude";
        public static final String COLUMN_ZIP_CODE = "zipcode";
        public static final String COLUMN_COUNTRY_ID = "country_id";
        public static final String COLUMN_LOCALITY_VERBOSE = "locality_verbose";

        public static Uri buildLocationUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /* Inner class that defines the table contents of the user_ratings table */
    public static final class UserRatingEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_USER_RATING).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER_RATING;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_USER_RATING;
        // Table name
        public static final String TABLE_NAME = "user_ratings";
        public static final String COLUMN_RESTAURANT_ID = "restaurant_id";
        public static final String COLUMN_AGGREGATE_RATING = "aggregate_rating";
        public static final String COLUMN_RATING_TEXT = "rating_text";
        public static final String COLUMN_VOTES = "votes";

        public static Uri buildUserRatingUri(long id) {
            return ContentUris.withAppendedId(CONTENT_URI, id);
        }
    }

    /* Inner class that defines the column names of the contents of the join tables */
    public static final class RestaurantDetails {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_RESTAURANT).appendPath("details").build();
        public static final String COLUMN_R_ID = "id";
        public static final String COLUMN_R_NAME = "name";
        public static final String COLUMN_R_URL = "url";
        public static final String COLUMN_R_PRICE_RANGE = "price_range";
        public static final String COLUMN_R_CURRENCY = "currency";
        public static final String COLUMN_R_THUMB = "thumb";
        public static final String COLUMN_R_FEATURED_IMAGE = "featured_image";
        public static final String COLUMN_R_PHOTOS_URL = "photos_url";
        public static final String COLUMN_R_MENU_URL = "menu_url";
        public static final String COLUMN_R_EVENTS_URL = "events_url";
        public static final String COLUMN_R_DELIVERING_NOW = "is_delivering_now";
        public static final String COLUMN_R_CUISINES = "cuisines";
        public static final String COLUMN_R_AVG_COST = "average_cost_for_two";
        public static final String COLUMN_R_ONLINE_DELIVERY = "has_online_delivery";
        public static final String COLUMN_R_TABLE_BOOKING = "has_table_booking";
        public static final String COLUMN_R_DEEPLINK = "deeplink";

        public static final String COLUMN_L_RESTAURANT_ID = "restaurant_id";
        public static final String COLUMN_L_ADDRESS = "address";
        public static final String COLUMN_L_LOCALITY = "locality";
        public static final String COLUMN_L_CITY = "city";
        public static final String COLUMN_L_LATITUDE = "latitude";
        public static final String COLUMN_L_LONGITUDE = "longitude";
        public static final String COLUMN_L_ZIP_CODE = "zipcode";
        public static final String COLUMN_L_COUNTRY_ID = "country_id";
        public static final String COLUMN_L_LOCALITY_VERBOSE = "locality_verbose";

        public static final String COLUMN_UR_RESTAURANT_ID = "restaurant_id";
        public static final String COLUMN_UR_AGGREGATE_RATING = "aggregate_rating";
        public static final String COLUMN_UR_RATING_TEXT = "rating_text";
        public static final String COLUMN_UR_VOTES = "votes";
    }
}

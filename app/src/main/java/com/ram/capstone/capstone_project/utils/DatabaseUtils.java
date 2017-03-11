package com.ram.capstone.capstone_project.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.ram.capstone.capstone_project.database.RestaurantContract;
import com.ram.capstone.capstone_project.database.RestaurantProvider;
import com.ram.capstone.capstone_project.models.Location;
import com.ram.capstone.capstone_project.models.Restaurant;
import com.ram.capstone.capstone_project.models.UserRating;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramkrishna on 11/3/17.
 */

public class DatabaseUtils {
    public static boolean isRestaurantBookmarked(Context context, int restaurantId) {
        Uri restaurantUri = RestaurantContract.RestaurantEntry.CONTENT_URI.buildUpon().appendPath(Integer.toString(restaurantId)).build();
        Cursor cursor = context.getContentResolver().query(
                restaurantUri,
                null,
                null,
                null,
                null
        );
        if(cursor != null) {
            boolean isFavourite = cursor.moveToFirst();
            cursor.close();
            return isFavourite;
        }
        return false;
    }

    public static void insertRestaurantInDb(Context context, Restaurant restaurant) {
        List<ContentValues> restaurantDetail = createRestaurantDetailsRecord(restaurant);
        context.getContentResolver().insert(RestaurantContract.RestaurantEntry.CONTENT_URI, restaurantDetail.get(0));
        context.getContentResolver().insert(RestaurantContract.LocationEntry.CONTENT_URI, restaurantDetail.get(1));
        context.getContentResolver().insert(RestaurantContract.UserRatingEntry.CONTENT_URI, restaurantDetail.get(2));
    }

    public static void deleteRestaurantFromDb(Context context, int restaurantId) {
        String[] selectionArgs = new String[]{Integer.toString(restaurantId)};
        Uri restaurantUri = RestaurantContract.RestaurantEntry.CONTENT_URI.buildUpon().appendPath(Integer.toString(restaurantId)).build();
        context.getContentResolver().delete(
                restaurantUri,
                RestaurantProvider.sRestaurantSelection,
                selectionArgs
        );

        Uri locationUri = RestaurantContract.LocationEntry.CONTENT_URI.buildUpon().appendPath(Integer.toString(restaurantId)).build();
        context.getContentResolver().delete(
                locationUri,
                RestaurantProvider.sLocationSelection,
                selectionArgs
        );

        Uri userRatingUri = RestaurantContract.UserRatingEntry.CONTENT_URI.buildUpon().appendPath(Integer.toString(restaurantId)).build();
        context.getContentResolver().delete(
                userRatingUri,
                RestaurantProvider.sUserRatingSelection,
                selectionArgs
        );
    }

    public static List<ContentValues> createRestaurantDetailsRecord(Restaurant restaurant) {
        List<ContentValues> detailsList = new ArrayList<>();
        detailsList.add(createRestaurantContentValue(restaurant));
        detailsList.add(createLocationContentValue(restaurant));
        detailsList.add(createUserRatingContentValue(restaurant));
        return detailsList;
    }

    public static ContentValues createRestaurantContentValue(Restaurant restaurant) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_ID, restaurant.getId());
        contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_NAME, restaurant.getName());
        contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_THUMB, restaurant.getThumb());
        contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_CUISINES, restaurant.getCuisines());
        contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_AVG_COST, restaurant.getAvgCostForTwo());
        contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_ONLINE_DELIVERY, restaurant.isHasOnlineDelivery());
        contentValues.put(RestaurantContract.RestaurantEntry.COLUMN_TABLE_BOOKING, restaurant.isHasTableBooking());
        return contentValues;
    }

    public static ContentValues createLocationContentValue(Restaurant restaurant) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RestaurantContract.LocationEntry.COLUMN_RESTAURANT_ID, restaurant.getId());
        Location location = restaurant.getLocation();
        contentValues.put(RestaurantContract.LocationEntry.COLUMN_LATITUDE, location.getLatitude());
        contentValues.put(RestaurantContract.LocationEntry.COLUMN_LONGITUDE, location.getLongitude());
        contentValues.put(RestaurantContract.LocationEntry.COLUMN_LOCALITY_VERBOSE, location.getLocalityVerbose());
        contentValues.put(RestaurantContract.LocationEntry.COLUMN_LOCALITY, location.getLocality());
        contentValues.put(RestaurantContract.LocationEntry.COLUMN_ADDRESS, location.getAddress());
        contentValues.put(RestaurantContract.LocationEntry.COLUMN_CITY, location.getCity());
        contentValues.put(RestaurantContract.LocationEntry.COLUMN_COUNTRY_ID, location.getCountryId());
        contentValues.put(RestaurantContract.LocationEntry.COLUMN_ZIP_CODE, location.getZipcode());
        return contentValues;
    }

    public static ContentValues createUserRatingContentValue(Restaurant restaurant) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(RestaurantContract.UserRatingEntry.COLUMN_RESTAURANT_ID, restaurant.getId());
        UserRating userRating = restaurant.getUserRating();
        contentValues.put(RestaurantContract.UserRatingEntry.COLUMN_AGGREGATE_RATING, userRating.getAggregateRating());
        contentValues.put(RestaurantContract.UserRatingEntry.COLUMN_RATING_TEXT, userRating.getRatingText());
        contentValues.put(RestaurantContract.UserRatingEntry.COLUMN_VOTES, userRating.getVotes());
        return contentValues;
    }
}

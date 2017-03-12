package com.ram.capstone.capstone_project.database;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

/**
 * Created by ramkrishna on 11/3/17.
 */

public class RestaurantProvider extends ContentProvider {

    // The URI Matcher used by this content provider.
    private static final UriMatcher sUriMatcher = buildUriMatcher();
    private RestaurantDbHelper restaurantDbHelper;

    public static final int RESTAURANTS = 100;
    public static final int RESTAURANTS_WITH_ID = 101;
    public static final int LOCATIONS = 201;
    public static final int LOCATION_WITH_RESTAURANT_ID = 200;
    public static final int USER_RATINGS = 301;
    public static final int USER_RATING_WITH_RESTAURANT_ID = 300;
    public static final int JOINED_RESTAURANTS_DETAILS= 400;
    public static final int JOINED_RESTAURANTS_DETAILS_WITH_ID = 401;

    private static final SQLiteQueryBuilder sRestaurantsQueryBuilder = new SQLiteQueryBuilder();

    public static final String sRestaurantSelection = RestaurantContract.RestaurantEntry.TABLE_NAME + "." + RestaurantContract.RestaurantEntry.COLUMN_ID+ "=?";
    public static final String sLocationSelection = RestaurantContract.LocationEntry.TABLE_NAME + "." + RestaurantContract.LocationEntry.COLUMN_RESTAURANT_ID+ "=?";
    public static final String sUserRatingSelection = RestaurantContract.UserRatingEntry.TABLE_NAME + "." + RestaurantContract.UserRatingEntry.COLUMN_RESTAURANT_ID+ "=?";

    public static UriMatcher buildUriMatcher() {
        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = RestaurantContract.CONTENT_AUTHORITY;

        // For each type of URI you want to add, create a corresponding code.
        matcher.addURI(authority, RestaurantContract.PATH_RESTAURANT, RESTAURANTS);
        matcher.addURI(authority, RestaurantContract.PATH_RESTAURANT + "/#", RESTAURANTS_WITH_ID);
        matcher.addURI(authority, RestaurantContract.PATH_LOCATION, LOCATIONS);
        matcher.addURI(authority, RestaurantContract.PATH_LOCATION + "/#", LOCATION_WITH_RESTAURANT_ID);
        matcher.addURI(authority, RestaurantContract.PATH_USER_RATING, USER_RATINGS);
        matcher.addURI(authority, RestaurantContract.PATH_USER_RATING + "/#", USER_RATING_WITH_RESTAURANT_ID);
        matcher.addURI(authority, RestaurantContract.PATH_RESTAURANT + "/details", JOINED_RESTAURANTS_DETAILS);
        matcher.addURI(authority, RestaurantContract.PATH_RESTAURANT + "/details/#", JOINED_RESTAURANTS_DETAILS_WITH_ID);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        restaurantDbHelper = new RestaurantDbHelper(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {
            case RESTAURANTS: {
                sRestaurantsQueryBuilder.setTables(RestaurantContract.RestaurantEntry.TABLE_NAME);
                retCursor = sRestaurantsQueryBuilder.query(restaurantDbHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                        );
                break;
            }
            case RESTAURANTS_WITH_ID: {
                sRestaurantsQueryBuilder.setTables(RestaurantContract.RestaurantEntry.TABLE_NAME);
                selectionArgs = new String[]{uri.getPathSegments().get(1)};
                retCursor = sRestaurantsQueryBuilder.query(restaurantDbHelper.getReadableDatabase(),
                        projection,
                        sRestaurantSelection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case LOCATIONS: {
                sRestaurantsQueryBuilder.setTables(RestaurantContract.LocationEntry.TABLE_NAME);
                retCursor = sRestaurantsQueryBuilder.query(restaurantDbHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case LOCATION_WITH_RESTAURANT_ID: {
                sRestaurantsQueryBuilder.setTables(RestaurantContract.LocationEntry.TABLE_NAME);
                selectionArgs = new String[]{uri.getPathSegments().get(1)};
                retCursor = sRestaurantsQueryBuilder.query(restaurantDbHelper.getReadableDatabase(),
                        projection,
                        sLocationSelection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case USER_RATINGS: {
                sRestaurantsQueryBuilder.setTables(RestaurantContract.UserRatingEntry.TABLE_NAME);
                retCursor = sRestaurantsQueryBuilder.query(restaurantDbHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case USER_RATING_WITH_RESTAURANT_ID: {
                sRestaurantsQueryBuilder.setTables(RestaurantContract.UserRatingEntry.TABLE_NAME);
                selectionArgs = new String[]{uri.getPathSegments().get(1)};
                retCursor = sRestaurantsQueryBuilder.query(restaurantDbHelper.getReadableDatabase(),
                        projection,
                        sUserRatingSelection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case JOINED_RESTAURANTS_DETAILS: {
                retCursor = restaurantDbHelper.getReadableDatabase().rawQuery(
                        "SELECT * FROM restaurants r " +
                                "INNER JOIN locations l ON l.restaurant_id = r.id " +
                                "INNER JOIN user_ratings ur ON ur.restaurant_id = r.id",
                        null
                );
                break;
            }
            case JOINED_RESTAURANTS_DETAILS_WITH_ID: {
                selectionArgs = new String[]{uri.getPathSegments().get(1)};
                retCursor = restaurantDbHelper.getReadableDatabase().rawQuery(
                        "SELECT * FROM restaurants r " +
                                "INNER JOIN locations l ON l.restaurant_id = r.id " +
                                "INNER JOIN user_ratings ur ON ur.restaurant_id = r.id " +
                                "WHERE r.id = ?",
                        selectionArgs
                );
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
        retCursor.setNotificationUri(getContext().getContentResolver(), RestaurantContract.BASE_CONTENT_URI);
        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        final int match = sUriMatcher.match(uri);

        switch(match) {
            case RESTAURANTS:
                return RestaurantContract.RestaurantEntry.CONTENT_TYPE;
            case RESTAURANTS_WITH_ID:
                return RestaurantContract.RestaurantEntry.CONTENT_ITEM_TYPE;
            case LOCATIONS:
                return RestaurantContract.LocationEntry.CONTENT_TYPE;
            case LOCATION_WITH_RESTAURANT_ID:
                return RestaurantContract.LocationEntry.CONTENT_ITEM_TYPE;
            case USER_RATINGS:
                return RestaurantContract.UserRatingEntry.CONTENT_TYPE;
            case USER_RATING_WITH_RESTAURANT_ID:
                return RestaurantContract.UserRatingEntry.CONTENT_ITEM_TYPE;
            default:
                throw new UnsupportedOperationException("Unknown uri " + uri);
        }
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
        final SQLiteDatabase db = restaurantDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        Uri returnUri;

        switch (match) {
            case RESTAURANTS: {
                long _id = db.insertOrThrow(RestaurantContract.RestaurantEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = RestaurantContract.RestaurantEntry.buildRestaurantUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case LOCATIONS: {
                long _id = db.insert(RestaurantContract.LocationEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = RestaurantContract.LocationEntry.buildLocationUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            case USER_RATINGS: {
                long _id = db.insert(RestaurantContract.UserRatingEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = RestaurantContract.UserRatingEntry.buildUserRatingUri(_id);
                else
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                break;
            }
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        getContext().getContentResolver().notifyChange(RestaurantContract.BASE_CONTENT_URI, null);
        return returnUri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = restaurantDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsDeleted;
        // this makes delete all rows return the number of rows deleted
        switch (match) {
            case RESTAURANTS_WITH_ID:
                rowsDeleted = db.delete(RestaurantContract.RestaurantEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case RESTAURANTS:
                rowsDeleted = db.delete(RestaurantContract.RestaurantEntry.TABLE_NAME, null, null);
                break;
            case LOCATION_WITH_RESTAURANT_ID:
                rowsDeleted = db.delete(RestaurantContract.LocationEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case LOCATIONS:
                rowsDeleted = db.delete(RestaurantContract.LocationEntry.TABLE_NAME, null, null);
                break;
            case USER_RATING_WITH_RESTAURANT_ID:
                rowsDeleted = db.delete(RestaurantContract.UserRatingEntry.TABLE_NAME, selection, selectionArgs);
                break;
            case USER_RATINGS:
                rowsDeleted = db.delete(RestaurantContract.UserRatingEntry.TABLE_NAME, null, null);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        // Because a null deletes all rows
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(RestaurantContract.BASE_CONTENT_URI, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = restaurantDbHelper.getWritableDatabase();
        final int match = sUriMatcher.match(uri);
        int rowsUpdated;
        switch (match) {
            case RESTAURANTS:
                rowsUpdated = db.update(RestaurantContract.RestaurantEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case LOCATIONS:
                rowsUpdated = db.update(RestaurantContract.LocationEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            case USER_RATINGS:
                rowsUpdated = db.update(RestaurantContract.UserRatingEntry.TABLE_NAME, values, selection, selectionArgs);
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsUpdated != 0) {
            getContext().getContentResolver().notifyChange(RestaurantContract.BASE_CONTENT_URI, null);
        }
        return rowsUpdated;
    }
}

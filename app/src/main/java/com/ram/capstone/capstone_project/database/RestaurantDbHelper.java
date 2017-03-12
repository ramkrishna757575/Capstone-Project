package com.ram.capstone.capstone_project.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ramkrishna on 11/3/17.
 */

public class RestaurantDbHelper extends SQLiteOpenHelper {
    // If you change the database schema, you must increment the database version.
    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "khana_khoj.db";

    public RestaurantDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        //Create a table to hold restaurant data
        final String SQL_CREATE_RESTAURANT_TABLE = "CREATE TABLE " + RestaurantContract.RestaurantEntry.TABLE_NAME + " (" +
                RestaurantContract.RestaurantEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RestaurantContract.RestaurantEntry.COLUMN_ID + " INTEGER UNIQUE NOT NULL, " +
                RestaurantContract.RestaurantEntry.COLUMN_NAME + " TEXT, " +
                RestaurantContract.RestaurantEntry.COLUMN_AVG_COST + " INTEGER, " +
                RestaurantContract.RestaurantEntry.COLUMN_URL + " TEXT, " +
                RestaurantContract.RestaurantEntry.COLUMN_PRICE_RANGE + " INTEGER, " +
                RestaurantContract.RestaurantEntry.COLUMN_CURRENCY + " TEXT, " +
                RestaurantContract.RestaurantEntry.COLUMN_THUMB + " TEXT, " +
                RestaurantContract.RestaurantEntry.COLUMN_FEATURED_IMAGE + " TEXT, " +
                RestaurantContract.RestaurantEntry.COLUMN_PHOTOS_URL + " TEXT, " +
                RestaurantContract.RestaurantEntry.COLUMN_MENU_URL + " TEXT, " +
                RestaurantContract.RestaurantEntry.COLUMN_EVENTS_URL + " TEXT, " +
                RestaurantContract.RestaurantEntry.COLUMN_ONLINE_DELIVERY + " BOOLEAN, " +
                RestaurantContract.RestaurantEntry.COLUMN_DELIVERING_NOW + " BOOLEAN, " +
                RestaurantContract.RestaurantEntry.COLUMN_TABLE_BOOKING + " BOOLEAN, " +
                RestaurantContract.RestaurantEntry.COLUMN_CUISINES + " TEXT, " +
                RestaurantContract.RestaurantEntry.COLUMN_DEEPLINK + " TEXT " +
                " );";

        final String SQL_CREATE_LOCATION_TABLE = "CREATE TABLE " + RestaurantContract.LocationEntry.TABLE_NAME + " (" +
                RestaurantContract.LocationEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RestaurantContract.LocationEntry.COLUMN_RESTAURANT_ID + " INTEGER NOT NULL, " +
                RestaurantContract.LocationEntry.COLUMN_ADDRESS + " TEXT, " +
                RestaurantContract.LocationEntry.COLUMN_LOCALITY + " TEXT, " +
                RestaurantContract.LocationEntry.COLUMN_CITY + " TEXT, " +
                RestaurantContract.LocationEntry.COLUMN_LATITUDE + " TEXT, " +
                RestaurantContract.LocationEntry.COLUMN_LONGITUDE + " TEXT, " +
                RestaurantContract.LocationEntry.COLUMN_ZIP_CODE + " INTEGER, " +
                RestaurantContract.LocationEntry.COLUMN_COUNTRY_ID + " INTEGER, " +
                RestaurantContract.LocationEntry.COLUMN_LOCALITY_VERBOSE + " TEXT, " +
                " FOREIGN KEY (" + RestaurantContract.LocationEntry.COLUMN_RESTAURANT_ID + ") REFERENCES " +
                RestaurantContract.RestaurantEntry.TABLE_NAME + " (" + RestaurantContract.RestaurantEntry.COLUMN_ID + "), " +
                " UNIQUE (" + RestaurantContract.LocationEntry.COLUMN_RESTAURANT_ID + ") ON CONFLICT REPLACE);";

        final String SQL_CREATE_USER_RATING_TABLE = "CREATE TABLE " + RestaurantContract.UserRatingEntry.TABLE_NAME + " (" +
                RestaurantContract.UserRatingEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                RestaurantContract.UserRatingEntry.COLUMN_RESTAURANT_ID + " INTEGER NOT NULL, " +
                RestaurantContract.UserRatingEntry.COLUMN_AGGREGATE_RATING + " REAL, " +
                RestaurantContract.UserRatingEntry.COLUMN_RATING_TEXT + " TEXT, " +
                RestaurantContract.UserRatingEntry.COLUMN_VOTES + " INTEGER, " +
                " FOREIGN KEY (" + RestaurantContract.UserRatingEntry.COLUMN_RESTAURANT_ID + ") REFERENCES " +
                RestaurantContract.RestaurantEntry.TABLE_NAME + " (" + RestaurantContract.RestaurantEntry.COLUMN_ID + "), " +
                " UNIQUE (" + RestaurantContract.UserRatingEntry.COLUMN_RESTAURANT_ID + ") ON CONFLICT REPLACE);";

        db.execSQL(SQL_CREATE_RESTAURANT_TABLE);
        db.execSQL(SQL_CREATE_LOCATION_TABLE);
        db.execSQL(SQL_CREATE_USER_RATING_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + RestaurantContract.RestaurantEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RestaurantContract.LocationEntry.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RestaurantContract.UserRatingEntry.TABLE_NAME);
        onCreate(db);
    }
}

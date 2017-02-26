package com.ram.capstone.capstone_project.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.ram.capstone.capstone_project.misc.SharedPref;

/**
 * Created by ramkrishna on 26/2/17.
 */

public class Common {
    public static SharedPreferences.Editor getSharedPreferenceEditor(Context c) {
        return c.getSharedPreferences(SharedPref.FILE_NAME, Activity.MODE_PRIVATE).edit();
    }

    public static Long getLongFromSharedPreference(Context context, String key) {
        return context.getSharedPreferences(SharedPref.FILE_NAME, Activity.MODE_PRIVATE).getLong(key, -1);
    }

    public static String getStringFromSharedPreference(Context context, String key) {
        return context.getSharedPreferences(SharedPref.FILE_NAME, Activity.MODE_PRIVATE).getString(key, "");
    }

    public static int getIntFromSharedPreference(Context context, String key) {
        return context.getSharedPreferences(SharedPref.FILE_NAME, Activity.MODE_PRIVATE).getInt(key, -1);
    }

    public static boolean getBooleanFromSharedPreference(Context context, String key) {
        return context.getSharedPreferences(SharedPref.FILE_NAME, Activity.MODE_PRIVATE).getBoolean(key, false);
    }
}

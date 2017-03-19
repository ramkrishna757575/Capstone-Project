package com.ram.capstone.capstone_project.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.ram.capstone.capstone_project.R;
import com.ram.capstone.capstone_project.misc.SharedPref;
import com.ram.capstone.capstone_project.widget.RestaurantWidget;

/**
 * Created by ramkrishna on 26/2/17.
 */

public class CommonUtils {
    public static AlertDialog alert = null;

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

    public static boolean isNetworkAvailable(Context c) {
        if (c == null) return false;
        final ConnectivityManager connectivityManager = ((ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE));
        if (connectivityManager.getActiveNetworkInfo() != null) {
            try {
                return connectivityManager.getActiveNetworkInfo().isConnected();
            } catch (Exception e) {
                Log.d("Exception", e.getMessage());
            }
        }
        return false;
    }

    public static void showDialogToConnectInternet(final Context c, boolean cancelable, boolean negativeButton) {
        if (c == null) return;

        final AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle(R.string.internetErrorTitle)
                .setMessage(R.string.internetErrorMsg)
                .setCancelable(cancelable)
                .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        c.startActivity(new Intent(Settings.ACTION_SETTINGS));
                    }
                });

        if (negativeButton)
            builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    if (c instanceof Activity) {
                        ((Activity) c).finish();
                    }
                }
            });
        if (alert != null && alert.isShowing())
            alert.dismiss();

        alert = builder.create();
        alert.show();
    }

    public static void hideViews(View... views) {

        for (View view : views) {
            if (view == null) continue;
            view.setVisibility(View.GONE);
        }
    }

    public static void showViews(View... views) {

        for (View view : views) {
            if (view == null) continue;
            view.setVisibility(View.VISIBLE);
        }
    }

    /***
     * for keyboard hide and show
     */
    public static void hideKeyboard(Context context) {
        if (context == null) return;


        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = ((Activity) context).getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(context);
        }
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static void showKeyboard(Context context, EditText editText) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT);
    }

    public static void updateWidget(Context context) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        if (appWidgetManager != null) {
            ComponentName name = new ComponentName(context, RestaurantWidget.class);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(name);
            if(appWidgetIds.length > 0)
                appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widgetRestaurantList);

        }
    }
}

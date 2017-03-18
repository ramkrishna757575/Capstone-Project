package com.ram.capstone.capstone_project.widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Binder;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.ram.capstone.capstone_project.R;
import com.ram.capstone.capstone_project.database.RestaurantContract;

import java.util.concurrent.ExecutionException;

/**
 * Created by ramkrishna on 18/03/17
 */

public class ListProvider implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext = null;
    private int appWidgetId;
    Cursor cursor;
    Uri restaurantDetailsUri = RestaurantContract.RestaurantEntry.CONTENT_URI.buildUpon().appendPath("details").build();

    public ListProvider(Context context, Intent intent) {
        this.mContext = context;
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        cursor = context.getContentResolver().query(restaurantDetailsUri,
                null,
                null,
                null,
                null);
    }

    @Override
    public void onCreate() {

    }

    @Override
    public void onDataSetChanged() {
        final long identityToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(restaurantDetailsUri,
                null,
                null,
                null,
                null);
        Binder.restoreCallingIdentity(identityToken);
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        if(cursor != null && cursor.getCount() > 0)
            return cursor.getCount();
        else
            return 1;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        if(getItemId(position) == -1)
            return new RemoteViews(mContext.getPackageName(), R.layout.widget_no_item);
        RemoteViews remoteViews = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_item);
        if (cursor != null && cursor.moveToPosition(position)) {
            try {
                Bitmap bitmap = Glide.with(mContext)
                        .load(cursor.getString(cursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_THUMB)))
                        .asBitmap()
                        .into(Target.SIZE_ORIGINAL,Target.SIZE_ORIGINAL)
                        .get();
                remoteViews.setImageViewBitmap(R.id.restaurantThumb, bitmap);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            remoteViews.setTextViewText(R.id.restaurantName, cursor.getString(cursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_NAME)));
            remoteViews.setTextViewText(R.id.restaurantLocation, cursor.getString(cursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_L_LOCALITY_VERBOSE)));
            remoteViews.setTextViewText(R.id.restaurantCuisines, cursor.getString(cursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_CUISINES)));
            remoteViews.setTextViewText(R.id.rating, Float.toString(cursor.getFloat(cursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_UR_AGGREGATE_RATING))));
        }
        return remoteViews;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public long getItemId(int position) {
        if(cursor != null && cursor.getCount() == 0 || cursor == null)
            return -1;
        else {
            cursor.moveToPosition(position);
            return cursor.getLong(cursor.getColumnIndexOrThrow(RestaurantContract.RestaurantDetails.COLUMN_R_ID));
        }
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}

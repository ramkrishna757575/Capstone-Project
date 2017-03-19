package com.ram.capstone.capstone_project.fragments;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ram.capstone.capstone_project.R;
import com.ram.capstone.capstone_project.misc.AppConstants;
import com.ram.capstone.capstone_project.misc.SharedPref;
import com.ram.capstone.capstone_project.models.Restaurant;
import com.ram.capstone.capstone_project.utils.CommonUtils;
import com.ram.capstone.capstone_project.utils.DatabaseUtils;
import com.ram.capstone.capstone_project.widget.RestaurantWidget;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantDetailFragment extends Fragment {
    private Restaurant restaurant;
    private View rootView, restaurantImageContainer, restaurantInfoContainer, restaurantAddressContainer;
    private TextView name, location, rating, ratedByCount, cuisines, avgCost, onlineDelivery, tableBooking, address, showInMaps, somethingWentWrong;
    private Button btnBookmark;
    private ImageView restaurantImage;
    private boolean noData;

    public RestaurantDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);

        Bundle bundle = getArguments();
        if (bundle == null) {
            noData = true;
        } else {
            restaurant = bundle.getParcelable(AppConstants.RESTAURANT_PARCEL_NAME);
            if (restaurant == null)
                noData = true;
        }
        init();
        setupData();
        setupListeners();
        return rootView;
    }

    @Override
    public void onResume() {
        if (CommonUtils.getBooleanFromSharedPreference(getContext(), SharedPref.TWO_PANE_MODE))
            LocalBroadcastManager.getInstance(getContext()).registerReceiver(mMessageReceiver, new IntentFilter(AppConstants.RESTAURANT_DETAIL_FRAGMENT_TAG));

        super.onResume();
    }

    @Override
    public void onPause() {
        if (CommonUtils.getBooleanFromSharedPreference(getContext(), SharedPref.TWO_PANE_MODE)) {
            LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mMessageReceiver);
        }
        super.onPause();
    }

    private void setupData() {
        if (noData) {
            CommonUtils.showViews(somethingWentWrong);
            CommonUtils.hideViews(restaurantImageContainer, restaurantInfoContainer, restaurantAddressContainer, ratedByCount, name, location);
            somethingWentWrong.setText(getString(R.string.nothing_to_show));
            return;
        } else {
            CommonUtils.hideViews(somethingWentWrong);
            CommonUtils.showViews(restaurantImageContainer, restaurantInfoContainer, restaurantAddressContainer, ratedByCount, name, location);
        }
        name.setText(restaurant.getName());
        location.setText(restaurant.getLocation().getLocalityVerbose());
        if (restaurant.getUserRating().getVotes() > 0) {
            rating.setText(Float.toString(restaurant.getUserRating().getAggregateRating()));
            ratedByCount.setText(getString(R.string.people_voted, restaurant.getUserRating().getVotes()));
        } else {
            CommonUtils.hideViews(rating, ratedByCount);
        }
        cuisines.setText(restaurant.getCuisines());
        avgCost.setText(getString(R.string.average_cost_for_two, restaurant.getCurrency(), restaurant.getAvgCostForTwo()));
        if (restaurant.isHasOnlineDelivery())
            onlineDelivery.setText(getString(R.string.online_delivery_available));
        else
            onlineDelivery.setText(getString(R.string.online_delivery_not_available));

        if (restaurant.isHasTableBooking())
            tableBooking.setText(getString(R.string.table_booking_available));
        else
            tableBooking.setText(getString(R.string.table_booking_not_available));
        address.setText(restaurant.getLocation().getAddress());
        if (restaurant.getFeaturedImage() == null || restaurant.getFeaturedImage().isEmpty())
            restaurantImage.setImageDrawable(getResources().getDrawable(R.drawable.default_restaurant_thumb));
        else
            Picasso.with(getContext()).load(restaurant.getFeaturedImage())
                    .placeholder(R.drawable.default_restaurant_thumb)
                    .error(R.drawable.default_restaurant_thumb)
                    .into(restaurantImage);
        if (DatabaseUtils.isRestaurantBookmarked(getContext(), restaurant.getId())) {
            btnBookmark.setText(getString(R.string.bookmarked));
        } else {
            btnBookmark.setText(getString(R.string.bookmark));
        }
    }

    private void setupListeners() {
        if (noData)
            return;
        showInMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRestaurantInMaps();
            }
        });
        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (DatabaseUtils.isRestaurantBookmarked(getContext(), restaurant.getId())) {
                    DatabaseUtils.deleteRestaurantFromDb(getContext(), restaurant.getId());
                    btnBookmark.setText(getString(R.string.bookmark));
                } else {
                    DatabaseUtils.insertRestaurantInDb(getContext(), restaurant);
                    btnBookmark.setText(getString(R.string.bookmarked));
                }
                RestaurantWidget.sendRefreshBroadcast(getContext());
            }
        });
    }

    private void init() {
        name = (TextView) rootView.findViewById(R.id.restaurantName);
        location = (TextView) rootView.findViewById(R.id.restaurantLocation);
        rating = (TextView) rootView.findViewById(R.id.rating);
        ratedByCount = (TextView) rootView.findViewById(R.id.ratedByCount);
        cuisines = (TextView) rootView.findViewById(R.id.cuisines);
        avgCost = (TextView) rootView.findViewById(R.id.avgCost);
        onlineDelivery = (TextView) rootView.findViewById(R.id.onlineDelivery);
        tableBooking = (TextView) rootView.findViewById(R.id.tableBooking);
        address = (TextView) rootView.findViewById(R.id.address);
        showInMaps = (TextView) rootView.findViewById(R.id.showInMaps);
        btnBookmark = (Button) rootView.findViewById(R.id.bookmark);
        restaurantImage = (ImageView) rootView.findViewById(R.id.restaurantImage);
        somethingWentWrong = (TextView) rootView.findViewById(R.id.somethingWentWrong);
        restaurantImageContainer = rootView.findViewById(R.id.restaurantImageContainer);
        restaurantInfoContainer = rootView.findViewById(R.id.restaurantInfoContainer);
        restaurantAddressContainer = rootView.findViewById(R.id.restaurantAddressContainer);
    }

    private void openRestaurantInMaps() {
        String latitude = restaurant.getLocation().getLatitude();
        String longitude = restaurant.getLocation().getLongitude();
        String label = restaurant.getName();
        String uriBegin = "geo:" + latitude + "," + longitude;
        String query = latitude + "," + longitude + "(" + label + ")";
        String encodedQuery = Uri.encode(query);
        String uriString = uriBegin + "?q=" + encodedQuery + "&z=18";
        Uri uri = Uri.parse(uriString);
        Intent intent = new Intent(android.content.Intent.ACTION_VIEW, uri);
        PackageManager packageManager = getContext().getPackageManager();
        List activities = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        if(activities.size() > 0)
            startActivity(intent);
        else
            CommonUtils.showDialogWithMessage(getContext(), getString(R.string.no_app_for_map));
    }

    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            restaurant = intent.getExtras().getParcelable(AppConstants.RESTAURANT_PARCEL_NAME);
            noData = restaurant == null;
            setupData();
            setupListeners();
        }
    };
}

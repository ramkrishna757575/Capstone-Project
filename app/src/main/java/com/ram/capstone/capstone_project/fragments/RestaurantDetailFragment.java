package com.ram.capstone.capstone_project.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.ram.capstone.capstone_project.R;
import com.ram.capstone.capstone_project.misc.AppConstants;
import com.ram.capstone.capstone_project.models.Restaurant;
import com.ram.capstone.capstone_project.utils.CommonUtils;
import com.ram.capstone.capstone_project.utils.DatabaseUtils;
import com.squareup.picasso.Picasso;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantDetailFragment extends Fragment {
    private View rootView;
    private Restaurant restaurant;
    private TextView name, location, rating, ratedByCount, cuisines, avgCost, onlineDelivery, tableBooking, address, showInMaps;
    private Button btnBookmark;
    private ImageView restaurantImage;

    public RestaurantDetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_restaurant_detail, container, false);

        Bundle bundle = getArguments();
        if(bundle == null)
            getActivity().finish();
        restaurant = bundle.getParcelable(AppConstants.RESTAURANT_PARCEL_NAME);
        if(restaurant == null)
            getActivity().finish();

        init();
        setupData();
        setupListeners();
        return rootView;
    }

    private void setupData() {
        name.setText(restaurant.getName());
        location.setText(restaurant.getLocation().getLocalityVerbose());
        if(restaurant.getUserRating().getVotes() > 0) {
            rating.setText(Float.toString(restaurant.getUserRating().getAggregateRating()));
            ratedByCount.setText(getString(R.string.people_voted, restaurant.getUserRating().getVotes()));
        } else {
            CommonUtils.hideViews(rating, ratedByCount);
        }
        cuisines.setText(restaurant.getCuisines());
        avgCost.setText(getString(R.string.average_cost_for_two, restaurant.getCurrency(), restaurant.getAvgCostForTwo()));
        if(restaurant.isHasOnlineDelivery())
            onlineDelivery.setText(getString(R.string.online_delivery_available));
        else
            onlineDelivery.setText(getString(R.string.online_delivery_not_available));

        if(restaurant.isHasTableBooking())
            tableBooking.setText(getString(R.string.table_booking_available));
        else
            tableBooking.setText(getString(R.string.table_booking_not_available));
        address.setText(restaurant.getLocation().getAddress());
        if (restaurant.getFeaturedImage() == null || restaurant.getFeaturedImage().isEmpty())
            restaurantImage.setImageDrawable(getResources().getDrawable(R.drawable.default_restaurant_thumb));
        else
            Picasso.with(getContext()).load(restaurant.getThumb())
                    .placeholder(R.drawable.default_restaurant_thumb)
                    .error(R.drawable.default_restaurant_thumb)
                    .into(restaurantImage);
        if(DatabaseUtils.isRestaurantBookmarked(getContext(), restaurant.getId())) {
            btnBookmark.setText(getString(R.string.bookmarked));
        } else {
            btnBookmark.setText(getString(R.string.bookmark));
        }
    }

    private void setupListeners() {
        showInMaps.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRestaurantInMaps();
            }
        });
        btnBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(DatabaseUtils.isRestaurantBookmarked(getContext(), restaurant.getId())) {
                    DatabaseUtils.deleteRestaurantFromDb(getContext(), restaurant.getId());
                    btnBookmark.setText(getString(R.string.bookmark));
                } else {
                    DatabaseUtils.insertRestaurantInDb(getContext(), restaurant);
                    btnBookmark.setText(getString(R.string.bookmarked));
                }
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
        startActivity(intent);
    }
}

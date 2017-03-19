package com.ram.capstone.capstone_project.activities;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.ram.capstone.capstone_project.R;
import com.ram.capstone.capstone_project.fragments.RestaurantDetailFragment;
import com.ram.capstone.capstone_project.misc.AppConstants;
import com.ram.capstone.capstone_project.models.Restaurant;

/***
 * RestaurantDetailActivity - shows the details of a restaurant
 */
public class RestaurantDetailActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_detail);

        Restaurant restaurant = getIntent().getParcelableExtra(AppConstants.RESTAURANT_PARCEL_NAME);
        if (restaurant == null) {
            finish();
            return;
        }

        //Setup tool bar
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Add the RestaurantDetailFragment to this activity.
        FragmentManager fragmentManager = getSupportFragmentManager();
        RestaurantDetailFragment restaurantDetailFragment;
        restaurantDetailFragment = (RestaurantDetailFragment) fragmentManager.findFragmentByTag(AppConstants.RESTAURANT_DETAIL_FRAGMENT_TAG);
        if (restaurantDetailFragment == null) {
            restaurantDetailFragment = new RestaurantDetailFragment();
            Bundle args = new Bundle();
            args.putParcelable(AppConstants.RESTAURANT_PARCEL_NAME, restaurant);
            restaurantDetailFragment.setArguments(args);

            fragmentManager.beginTransaction()
                    .add(R.id.restaurantDetailFragmentContainer, restaurantDetailFragment, AppConstants.RESTAURANT_DETAIL_FRAGMENT_TAG)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}

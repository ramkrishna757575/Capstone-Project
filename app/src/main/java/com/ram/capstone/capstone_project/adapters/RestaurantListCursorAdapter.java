package com.ram.capstone.capstone_project.adapters;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ram.capstone.capstone_project.R;
import com.ram.capstone.capstone_project.activities.RestaurantDetailActivity;
import com.ram.capstone.capstone_project.database.RestaurantContract;
import com.ram.capstone.capstone_project.misc.AppConstants;
import com.ram.capstone.capstone_project.models.Location;
import com.ram.capstone.capstone_project.models.Restaurant;
import com.ram.capstone.capstone_project.models.UserRating;
import com.squareup.picasso.Picasso;

/**
 * Created by ramkrishna on 12/3/17.
 */

public class RestaurantListCursorAdapter extends RecyclerView.Adapter<RestaurantListCursorAdapter.ViewHolder>{

    private Context mContext;
    private Cursor mCursor;

    public RestaurantListCursorAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Restaurant restaurant = createRestaurantObj(position);

        viewHolder.ratingText.setText(Float.toString(restaurant.getUserRating().getAggregateRating()));
        viewHolder.restaurantNameText.setText(restaurant.getName());
        viewHolder.localityVerboseText.setText(restaurant.getLocation().getLocalityVerbose());
        viewHolder.cuisinesText.setText(restaurant.getCuisines());
        if (restaurant.getThumb() == null || restaurant.getThumb().isEmpty())
            viewHolder.restaurantThumb.setImageDrawable(mContext.getResources().getDrawable(R.drawable.default_restaurant_thumb));
        else
            Picasso.with(mContext).load(restaurant.getThumb())
                    .placeholder(R.drawable.default_restaurant_thumb)
                    .error(R.drawable.default_restaurant_thumb)
                    .into(viewHolder.restaurantThumb);

        viewHolder.restaurantItemContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRestaurantDetailActivity(restaurant);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (null == mCursor) return 0;
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        mCursor = newCursor;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView restaurantThumb;
        private TextView ratingText;
        private TextView restaurantNameText;
        private TextView localityVerboseText;
        private TextView cuisinesText;
        private View restaurantItemContainer;

        public ViewHolder(View v) {
            super(v);
            restaurantThumb = (ImageView) v.findViewById(R.id.restaurantThumb);
            ratingText = (TextView) v.findViewById(R.id.rating);
            restaurantNameText = (TextView) v.findViewById(R.id.restaurantName);
            localityVerboseText = (TextView) v.findViewById(R.id.localityVerbose);
            cuisinesText = (TextView) v.findViewById(R.id.cuisines);
            restaurantItemContainer = v.findViewById(R.id.restaurantItemContainer);
        }
    }

    private void startRestaurantDetailActivity(Restaurant restaurant) {
        Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
        intent.putExtra(AppConstants.RESTAURANT_PARCEL_NAME, restaurant);
        mContext.startActivity(intent);
    }

    private Restaurant createRestaurantObj(int position) {
        mCursor.moveToPosition(position);
        Restaurant restaurant = new Restaurant();
        restaurant.setId(mCursor.getInt(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_ID)));
        restaurant.setName(mCursor.getString(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_NAME)));
        restaurant.setUrl(mCursor.getString(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_URL)));
        restaurant.setAvgCostForTwo(mCursor.getInt(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_AVG_COST)));
        restaurant.setPriceRange(mCursor.getInt(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_PRICE_RANGE)));
        restaurant.setCurrency(mCursor.getString(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_CURRENCY)));
        restaurant.setThumb(mCursor.getString(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_THUMB)));
        restaurant.setFeaturedImage(mCursor.getString(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_FEATURED_IMAGE)));
        restaurant.setPhotosUrl(mCursor.getString(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_PHOTOS_URL)));
        restaurant.setMenuUrl(mCursor.getString(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_MENU_URL)));
        restaurant.setEventUrl(mCursor.getString(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_EVENTS_URL)));
        restaurant.setHasOnlineDelivery(mCursor.getInt(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_ONLINE_DELIVERY)));
        restaurant.setIsDeliveringNow(mCursor.getInt(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_DELIVERING_NOW)));
        restaurant.setHasTableBooking(mCursor.getInt(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_TABLE_BOOKING)));
        restaurant.setCuisines(mCursor.getString(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_CUISINES)));
        restaurant.setDeeplink(mCursor.getString(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_R_DEEPLINK)));

        Location location = new Location();
        location.setAddress(mCursor.getString(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_L_ADDRESS)));
        location.setLocality(mCursor.getString(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_L_LOCALITY)));
        location.setCity(mCursor.getString(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_L_CITY)));
        location.setLatitude(mCursor.getString(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_L_LATITUDE)));
        location.setLongitude(mCursor.getString(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_L_LONGITUDE)));
        location.setZipcode(mCursor.getString(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_L_ZIP_CODE)));
        location.setCountryId(mCursor.getInt(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_L_COUNTRY_ID)));
        location.setLocalityVerbose(mCursor.getString(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_L_LOCALITY_VERBOSE)));
        restaurant.setLocation(location);

        UserRating userRating = new UserRating();
        userRating.setAggregateRating(mCursor.getFloat(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_UR_AGGREGATE_RATING)));
        userRating.setRatingText(mCursor.getString(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_UR_RATING_TEXT)));
        userRating.setVotes(mCursor.getInt(mCursor.getColumnIndex(RestaurantContract.RestaurantDetails.COLUMN_UR_VOTES)));
        restaurant.setUserRating(userRating);
        return restaurant;
    }
}

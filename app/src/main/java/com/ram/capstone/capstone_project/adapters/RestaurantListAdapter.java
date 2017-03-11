package com.ram.capstone.capstone_project.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ram.capstone.capstone_project.R;
import com.ram.capstone.capstone_project.activities.RestaurantDetailActivity;
import com.ram.capstone.capstone_project.misc.AppConstants;
import com.ram.capstone.capstone_project.models.Restaurant;
import com.ram.capstone.capstone_project.models.RestaurantContainer;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramkrishna on 5/3/17.
 */

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> {
    private List<RestaurantContainer> restaurantContainers;
    private Context mContext;

    public RestaurantListAdapter(Context context) {
        mContext = context;
        restaurantContainers = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.restaurant_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        final Restaurant restaurant = restaurantContainers.get(position).getRestaurant();
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
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemCount() {
        return restaurantContainers.size();
    }

    public void addAll(List<RestaurantContainer> moreRestaurantContainers) {
        if (restaurantContainers != null) {
            restaurantContainers.addAll(moreRestaurantContainers);
            notifyDataSetChanged();
        }
    }

    public void clearAll() {
        if(restaurantContainers != null) {
            restaurantContainers.clear();
            notifyDataSetChanged();
        }
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
}

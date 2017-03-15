package com.ram.capstone.capstone_project.adapters;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ram.capstone.capstone_project.R;
import com.ram.capstone.capstone_project.activities.RestaurantDetailActivity;
import com.ram.capstone.capstone_project.interfaces.INotifyTabChange;
import com.ram.capstone.capstone_project.misc.AppConstants;
import com.ram.capstone.capstone_project.misc.SharedPref;
import com.ram.capstone.capstone_project.models.Restaurant;
import com.ram.capstone.capstone_project.models.RestaurantContainer;
import com.ram.capstone.capstone_project.utils.CommonUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ramkrishna on 5/3/17.
 */

public class RestaurantListAdapter extends RecyclerView.Adapter<RestaurantListAdapter.ViewHolder> implements INotifyTabChange{
    private List<RestaurantContainer> restaurantContainers;
    private Context mContext;
    private ViewHolder previousSelected;

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
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        if(position == 0) {
            setSelected(viewHolder);
        }
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
                updateRestaurantDetail(restaurant);
                setSelected(viewHolder);
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
            if(restaurantContainers.size() <= 0 && CommonUtils.getBooleanFromSharedPreference(mContext, SharedPref.TWO_PANE_MODE))
                updateRestaurantDetail(moreRestaurantContainers.get(0).getRestaurant());
            restaurantContainers.addAll(moreRestaurantContainers);
            notifyDataSetChanged();
        }
    }

    public void clearAll() {
        if(restaurantContainers != null) {
            restaurantContainers.clear();
            notifyDataSetChanged();
        }
        if(CommonUtils.getBooleanFromSharedPreference(mContext, SharedPref.TWO_PANE_MODE))
            updateRestaurantDetail(null);
    }

    @Override
    public void tabChanged(int tabIndex) {
        if(tabIndex != AppConstants.RESTAURANT_LIST_TAB_INDEX)
            return;
        if(previousSelected != null && restaurantContainers != null && restaurantContainers.size() > 0) {
            updateRestaurantDetail(restaurantContainers.get(previousSelected.getAdapterPosition()).getRestaurant());
        } else {
            updateRestaurantDetail(null);
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView restaurantThumb;
        private TextView ratingText;
        private TextView restaurantNameText;
        private TextView localityVerboseText;
        private TextView cuisinesText;
        private View restaurantItemContainer;
        private View backgroundHighlighter;

        public ViewHolder(View v) {
            super(v);
            restaurantThumb = (ImageView) v.findViewById(R.id.restaurantThumb);
            ratingText = (TextView) v.findViewById(R.id.rating);
            restaurantNameText = (TextView) v.findViewById(R.id.restaurantName);
            localityVerboseText = (TextView) v.findViewById(R.id.localityVerbose);
            cuisinesText = (TextView) v.findViewById(R.id.cuisines);
            restaurantItemContainer = v.findViewById(R.id.restaurantItemContainer);
            backgroundHighlighter = v.findViewById(R.id.backgroundHighlighter);
        }
    }

    private void updateRestaurantDetail(Restaurant restaurant) {
        if(CommonUtils.getBooleanFromSharedPreference(mContext, SharedPref.TWO_PANE_MODE)) {
            Intent intent = new Intent(AppConstants.RESTAURANT_DETAIL_FRAGMENT_TAG);
            intent.putExtra(AppConstants.RESTAURANT_PARCEL_NAME, restaurant);
            LocalBroadcastManager.getInstance(mContext).sendBroadcast(intent);
        }else {
            Intent intent = new Intent(mContext, RestaurantDetailActivity.class);
            intent.putExtra(AppConstants.RESTAURANT_PARCEL_NAME, restaurant);
            mContext.startActivity(intent);
        }
    }

    private void setSelected(ViewHolder viewHolder) {
        if(!CommonUtils.getBooleanFromSharedPreference(mContext, SharedPref.TWO_PANE_MODE))
            return;
        if(previousSelected != null)
            previousSelected.backgroundHighlighter.setBackgroundColor(Color.WHITE);
        viewHolder.backgroundHighlighter.setBackgroundColor(mContext.getResources().getColor(R.color.colorSelection));
        previousSelected = viewHolder;
    }
}

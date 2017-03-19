package com.ram.capstone.capstone_project.fragments;


import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ram.capstone.capstone_project.R;
import com.ram.capstone.capstone_project.activities.RestaurantListActivity;
import com.ram.capstone.capstone_project.adapters.RestaurantListCursorAdapter;
import com.ram.capstone.capstone_project.database.RestaurantContract;
import com.ram.capstone.capstone_project.misc.SharedPref;
import com.ram.capstone.capstone_project.utils.CommonUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class BookmarkedRestaurantListFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor>{
    
    private static final String LIST_POSITION = "LIST_POSITION";
    private int selectedItemPosition = -1;
    private boolean isFirstTime = true;
    private View rootView;
    private Button btnRetry;
    private RecyclerView restaurantsGrid;
    private ProgressBar loadingIndicator;
    private TextView somethingWentWrong;
    private AdView mAdView;
    private View searchView;

    private RestaurantListCursorAdapter restaurantListCursorAdapter;
    private GridLayoutManager gridLayoutManager;

    private static final int LOADER_ID = 44;

    public BookmarkedRestaurantListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        if(savedInstanceState != null)
            selectedItemPosition = savedInstanceState.getInt(LIST_POSITION);
        init();
        setupInitialStateOfViews();
        getActivity().getSupportLoaderManager().initLoader(LOADER_ID, null, this);
        return rootView;
    }

    private void init() {
        btnRetry = (Button) rootView.findViewById(R.id.retryButton);
        restaurantsGrid = (RecyclerView) rootView.findViewById(R.id.restaurantGrid);
        loadingIndicator = (ProgressBar) rootView.findViewById(R.id.loadingIndicator);
        somethingWentWrong = (TextView) rootView.findViewById(R.id.somethingWentWrong);
        somethingWentWrong.setText(getResources().getString(R.string.nothing_to_show));
        searchView = rootView.findViewById(R.id.search_view);
        restaurantListCursorAdapter = new RestaurantListCursorAdapter(getContext());
        restaurantListCursorAdapter.setSelectedItemPosition(selectedItemPosition);
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        restaurantsGrid.setHasFixedSize(true);
        restaurantsGrid.setLayoutManager(gridLayoutManager);
        restaurantsGrid.setAdapter(restaurantListCursorAdapter);
        mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void setupInitialStateOfViews() {
        CommonUtils.hideViews(somethingWentWrong, btnRetry, searchView, loadingIndicator);
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        switch (id){
            case LOADER_ID: {
                Uri restaurantDetailsUri = RestaurantContract.RestaurantEntry.CONTENT_URI.buildUpon().appendPath("details").build();
                return new CursorLoader(getContext(),
                        restaurantDetailsUri,
                        null,
                        null,
                        null,
                        null
                );
            }
            default:
                throw new RuntimeException("Loader Not Implemented: " + id);
        }
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (data.getCount() <= 0) {
            CommonUtils.showViews(somethingWentWrong);
        } else {
            CommonUtils.hideViews(somethingWentWrong);
        }
        restaurantListCursorAdapter.swapCursor(data);
        if(isFirstTime) {
            gridLayoutManager.scrollToPositionWithOffset(selectedItemPosition, 0);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        restaurantListCursorAdapter.swapCursor(null);
    }

    @Override
    public void onResume() {
        if(getContext() instanceof RestaurantListActivity && restaurantListCursorAdapter != null && CommonUtils.getBooleanFromSharedPreference(getContext(), SharedPref.TWO_PANE_MODE))
            ((RestaurantListActivity) getContext()).setOnTabChangeListener(restaurantListCursorAdapter);
        super.onResume();
    }

    @Override
    public void onPause() {
        if(getContext() instanceof RestaurantListActivity && restaurantListCursorAdapter != null && CommonUtils.getBooleanFromSharedPreference(getContext(), SharedPref.TWO_PANE_MODE))
            ((RestaurantListActivity) getContext()).removeOnTabChangeListener(restaurantListCursorAdapter);
        super.onPause();
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(LIST_POSITION, restaurantListCursorAdapter.getSelectedItemPosition());
    }
    
}

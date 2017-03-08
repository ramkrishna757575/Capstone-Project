package com.ram.capstone.capstone_project.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.ram.capstone.capstone_project.BuildConfig;
import com.ram.capstone.capstone_project.R;
import com.ram.capstone.capstone_project.adapters.RestaurantListAdapter;
import com.ram.capstone.capstone_project.api.RestClient;
import com.ram.capstone.capstone_project.api.ServerResponseCode;
import com.ram.capstone.capstone_project.api.ZomatoApiInterface;
import com.ram.capstone.capstone_project.misc.AppConstants;
import com.ram.capstone.capstone_project.misc.SharedPref;
import com.ram.capstone.capstone_project.models.RestaurantSearchResult;
import com.ram.capstone.capstone_project.utils.CommonUtils;

import java.util.Timer;
import java.util.TimerTask;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class RestaurantListFragment extends Fragment {

    private int totalResultsFound = 0;
    private int resultsShownCount = 0;
    private int searchStartPosition = 0;
    private boolean isFetching;
    private View rootView;

    private EditText searchText;
    private Button btnRetry;
    private RecyclerView restaurantsGrid;
    private ProgressBar loadingIndicator;
    private TextView somethingWentWrong;
    private ImageView clearSearchText;
    private AdView mAdView;

    private RestaurantListAdapter restaurantListAdapter;
    private GridLayoutManager gridLayoutManager;

    Call<RestaurantSearchResult> call;

    public RestaurantListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_restaurant_list, container, false);
        init();
        setupInitialStateOfViews();
        setupListeners();
        if (!isFetching)
            fetchRestaurantsBySearch(searchText.getText().toString());
        return rootView;
    }

    private void init() {
        searchText = (EditText) rootView.findViewById(R.id.edit_search);
        btnRetry = (Button) rootView.findViewById(R.id.retryButton);
        restaurantsGrid = (RecyclerView) rootView.findViewById(R.id.restaurantGrid);
        loadingIndicator = (ProgressBar) rootView.findViewById(R.id.loadingIndicator);
        somethingWentWrong = (TextView) rootView.findViewById(R.id.somethingWentWrong);
        restaurantListAdapter = new RestaurantListAdapter(getContext());
        gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        restaurantsGrid.setHasFixedSize(true);
        restaurantsGrid.setLayoutManager(gridLayoutManager);
        restaurantsGrid.setAdapter(restaurantListAdapter);
        clearSearchText = (ImageView) rootView.findViewById(R.id.clearSearchText);
        mAdView = (AdView) rootView.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void setupInitialStateOfViews() {
        CommonUtils.hideViews(somethingWentWrong, btnRetry);
    }

    private void setupListeners() {
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isFetching)
                    fetchRestaurantsBySearch(searchText.getText().toString());
            }
        });

        restaurantsGrid.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                int lastVisibleItemPosition = gridLayoutManager.findLastVisibleItemPosition();
                if (lastVisibleItemPosition >= resultsShownCount - 1 && totalResultsFound > searchStartPosition && !isFetching) {
                    fetchRestaurantsBySearch(searchText.getText().toString());
                }
            }
        });

        searchText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            private Timer timer = new Timer();
            private final long DELAY = 500; // milliseconds

            @Override
            public void afterTextChanged(Editable editable) {
                if (editable.length() > 0) {
                    if (editable.length() > 2) {
                        timer.cancel();
                        timer = new Timer();
                        timer.schedule(
                                new TimerTask() {
                                    @Override
                                    public void run() {
                                        getActivity().runOnUiThread(new Runnable() {
                                            @Override
                                            public void run() {
                                                resetSearchAndFetch(View.VISIBLE);
                                            }
                                        });
                                    }
                                },
                                DELAY
                        );
                    }
                } else if (editable.length() == 0) {
                    resetSearchAndFetch(View.INVISIBLE);
                }
            }
        });

        clearSearchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchText.setText("");
                clearSearchText.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void fetchRestaurantsBySearch(String query) {
        isFetching = true;
        somethingWentWrong.setText(getResources().getString(R.string.something_went_wrong));
        if (!CommonUtils.isNetworkAvailable(getContext())) {
            CommonUtils.showDialogToConnectInternet(getContext(), true, false);
            CommonUtils.showViews(btnRetry);
            CommonUtils.hideViews(loadingIndicator);
            return;
        } else {
            CommonUtils.showViews(loadingIndicator);
            CommonUtils.hideViews(somethingWentWrong, btnRetry);
        }
        ZomatoApiInterface apiInterface = RestClient.getRestClient();
        String latitude = CommonUtils.getStringFromSharedPreference(getContext(), SharedPref.LOCATION_LAT);
        String longitude = CommonUtils.getStringFromSharedPreference(getContext(), SharedPref.LOCATION_LON);
        call = apiInterface.searchRestaurants(
                BuildConfig.API_KEY,
                AppConstants.SEARCH_RADIUS,
                latitude, longitude,
                searchStartPosition,
                AppConstants.PAGE_SIZE,
                query);
        call.enqueue(new Callback<RestaurantSearchResult>() {
            @Override
            public void onResponse(Call<RestaurantSearchResult> call, Response<RestaurantSearchResult> response) {
                if (response.code() == ServerResponseCode.SUCCESS) {
                    totalResultsFound = response.body().getResultCount();
                    resultsShownCount = response.body().getResultsShown();
                    searchStartPosition += resultsShownCount;
                    CommonUtils.hideViews(loadingIndicator);
                    restaurantListAdapter.addAll(response.body().getRestaurantsContainer());
                    isFetching = false;
                    if (restaurantListAdapter.getItemCount() <= 0) {
                        somethingWentWrong.setText(getResources().getString(R.string.nothing_to_show));
                        CommonUtils.showViews(somethingWentWrong);
                    } else {
                        CommonUtils.hideViews(somethingWentWrong);
                    }
                } else {
                    CommonUtils.hideViews(loadingIndicator);
                    CommonUtils.showViews(somethingWentWrong, btnRetry);
                    isFetching = false;
                }
            }

            @Override
            public void onFailure(Call<RestaurantSearchResult> call, Throwable t) {
                CommonUtils.hideViews(loadingIndicator);
                CommonUtils.showViews(somethingWentWrong, btnRetry);
                isFetching = false;
            }
        });
    }

    private void resetSearchAndFetch(int clearSearchTextButtonVisibility) {
        if (isFetching)
            call.cancel();
        restaurantListAdapter.clearAll();
        searchStartPosition = 0;
        fetchRestaurantsBySearch(searchText.getText().toString());
        CommonUtils.hideKeyboard(getContext());
        if (clearSearchTextButtonVisibility == View.VISIBLE)
            clearSearchText.setVisibility(View.VISIBLE);
        else
            clearSearchText.setVisibility(View.INVISIBLE);
    }
}

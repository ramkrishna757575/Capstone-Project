package com.ram.capstone.capstone_project.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.ram.capstone.capstone_project.R;
import com.ram.capstone.capstone_project.misc.AppConstants;
import com.ram.capstone.capstone_project.misc.SharedPref;
import com.ram.capstone.capstone_project.utils.CommonUtils;

public class SplashScreenActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    GoogleApiClient googleApiClient;
    private Location location;
    Button btnRetry;
    TextView errorMessage;

    private static int SPLASH_TIME_OUT = 3000;
    private boolean splashTimedOut = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getLocationService();

        errorMessage = (TextView) findViewById(R.id.errorMessage);
        btnRetry = (Button) findViewById(R.id.retryButton);
        btnRetry.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (googleApiClient.isConnected()) {
                    onConnected(null);
                } else {
                    googleApiClient.connect();
                }
            }
        });

        new Handler().postDelayed(new Runnable() {
            /*
             * Showing splash screen with a timer.
             */

            @Override
            public void run() {
                splashTimedOut = true;
                // This method will be executed once the timer is over
                // Start your app main activity
                if (location != null) {
                    Intent i = new Intent(SplashScreenActivity.this, RestaurantListActivity.class);
                    startActivity(i);
                    // close this activity
                    finish();
                }
            }
        }, SPLASH_TIME_OUT);
    }

    @Override
    protected void onStart() {
        googleApiClient.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        googleApiClient.disconnect();
        super.onStop();
    }

    private void getLocationService() {
        if (googleApiClient == null) {
            googleApiClient = new GoogleApiClient.Builder(this)
                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .build();
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        btnRetry.setVisibility(View.GONE);
        errorMessage.setVisibility(View.GONE);
        getLocation();
        saveLocationInSharedPrefs();
    }

    @Override
    public void onConnectionSuspended(int i) {
        btnRetry.setVisibility(View.VISIBLE);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        btnRetry.setVisibility(View.VISIBLE);
        errorMessage.setVisibility(View.VISIBLE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case AppConstants.PERMISSION_REQUEST_LOCATION: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                    getLocation();
                } else {
                    finish();
                }
                return;
            }
        }
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                    AppConstants.PERMISSION_REQUEST_LOCATION);
            return;
        }
        location = LocationServices.FusedLocationApi.getLastLocation(googleApiClient);
    }

    private void saveLocationInSharedPrefs() {
        if (location != null) {
            CommonUtils.getSharedPreferenceEditor(this).putString(SharedPref.LOCATION_LAT, Double.toString(location.getLatitude())).apply();
            CommonUtils.getSharedPreferenceEditor(this).putString(SharedPref.LOCATION_LON, Double.toString(location.getLongitude())).apply();

            if (splashTimedOut) {
                Intent intent = new Intent(this, RestaurantListActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            btnRetry.setVisibility(View.VISIBLE);
            errorMessage.setVisibility(View.VISIBLE);
        }
    }
}

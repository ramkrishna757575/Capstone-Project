package com.ram.capstone.capstone_project.api;

import com.ram.capstone.capstone_project.misc.AppConstants;
import retrofit2.Retrofit;
import retrofit2.converter.moshi.MoshiConverterFactory;

/**
 * Created by ramkrishna on 4/3/17.
 * RestAdapter required by Retrofit
 */

public class RestClient {
    private static ZomatoApiInterface zomatoApiInterface;

    public static ZomatoApiInterface getRestClient(){
        if(zomatoApiInterface != null)
            return zomatoApiInterface;
        Retrofit restAdapter = new Retrofit.Builder()
                .baseUrl(AppConstants.BASE_URL)//passing API_URL
                .addConverterFactory(MoshiConverterFactory.create())
                .build();
        zomatoApiInterface = restAdapter.create(ZomatoApiInterface.class);
        return zomatoApiInterface;
    }
}

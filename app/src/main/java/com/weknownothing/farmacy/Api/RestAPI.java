package com.weknownothing.farmacy.Api;

/**
 * Created by p2 on 6/5/18.
 */

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weknownothing.farmacy.Utilities.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RestAPI {

    public static Retrofit adapter = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_WEATHER)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static Api appService;

    public static Api getAppService() {


        appService = adapter.create(Api.class);
        return appService;
    }


}
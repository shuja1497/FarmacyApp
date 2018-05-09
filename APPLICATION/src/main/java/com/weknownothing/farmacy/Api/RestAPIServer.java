package com.weknownothing.farmacy.Api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.weknownothing.farmacy.Utilities.Constants;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by p2 on 6/5/18.
 */

public class RestAPIServer {

    public static Gson gson = new GsonBuilder()
            .setLenient()
            .create();

    public static Retrofit adapter = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL_SERVER)
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    public static Api appService;

    public static Api getAppService() {


        appService = adapter.create(Api.class);
        return appService;
    }

}

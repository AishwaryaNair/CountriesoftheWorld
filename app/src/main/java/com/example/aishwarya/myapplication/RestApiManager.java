package com.example.aishwarya.myapplication;

import com.google.gson.GsonBuilder;

import retrofit.RestAdapter;
import retrofit.converter.GsonConverter;

/**
 * Created by Aishwarya on 4/1/16.
 */
public class RestApiManager {

    private CountryApi mCountryApi;

    public CountryApi getCountryApi() {

        if(mCountryApi == null) {
            GsonBuilder gson = new GsonBuilder();
            gson.registerTypeAdapter(String.class, new StringDeserializer2());

            mCountryApi = new RestAdapter.Builder()
                    .setEndpoint(Constants.BASE_URL)
                    .setConverter(new GsonConverter(gson.create()))
                    .build()
                    .create(CountryApi.class);
        }
        return mCountryApi;
    }
}
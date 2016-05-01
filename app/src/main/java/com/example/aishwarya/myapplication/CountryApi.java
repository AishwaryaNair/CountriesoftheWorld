package com.example.aishwarya.myapplication;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Aishwarya on 4/1/16.
 */
public interface CountryApi {

    @GET("/all")
    void getCountries(Callback<String> countries);

    @GET("/region/{code}/")
    void getsinglecountry(@Path("code") String code, Callback<String> country);
}
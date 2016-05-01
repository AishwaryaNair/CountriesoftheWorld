package com.example.aishwarya.myapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by Aishwarya on 4/1/16.
 */
public class Controller {

    private static final String TAG = Controller.class.getSimpleName();
    private CountryCallbackListener mListener;
    private RestApiManager mApiManager;

    public Controller(CountryCallbackListener listener) {
        mListener = listener;
        mApiManager = new RestApiManager();
    }

    public void startFetching(String continent) {
        if(continent == null) {
            mApiManager.getCountryApi().getCountries(new Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    Log.d(TAG, "JSON :: " + s);

                    try {
                        JSONArray array = new JSONArray(s);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);

                            Country country = new Country.Builder()
                                    .setName(object.getString("name"))
                                    .setCapital(object.getString("capital"))
                                    .setRegion(object.getString("region"))
                                    .setPopulation(object.get("population"))
                                    .setArea(object.get("area"))
                                    .setCode(object.getString("alpha3Code"))
                                    .setLatLong(object.get("latlng"))
                                    .setBorder(object.get("borders"))
                                    .setCurrencies(object.get("currencies"))
                                    .setLanguages(object.get("languages"))
                                    .build();

                            mListener.onFetchProgress(country);

                        }

                    } catch (JSONException e) {
                        mListener.onFetchFailed();
                    }


                    mListener.onFetchComplete();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d(TAG, "Error :: " + error.getMessage());
                    mListener.onFetchComplete();
                }
            });
        }
        else {
            mApiManager.getCountryApi().getsinglecountry(continent, new Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    Log.d(TAG, "JSON :: " + s);

                    try {
                        JSONArray array = new JSONArray(s);

                        for (int i = 0; i < array.length(); i++) {
                            JSONObject object = array.getJSONObject(i);

                            Country flower = new Country.Builder()
                                    .setName(object.getString("name"))
                                    .setCapital(object.getString("capital"))
                                    .setRegion(object.getString("region"))
                                    .setPopulation(object.get("population"))
                                    .setArea(object.get("area"))
                                    .setCode(object.getString("alpha3Code"))
                                    .setLatLong(object.get("latlng"))
                                    .setBorder(object.get("borders"))
                                    .setCurrencies(object.get("currencies"))
                                    .setLanguages(object.get("languages"))
                                    .build();

                            mListener.onFetchProgress(flower);

                        }

                    } catch (JSONException e) {
                        mListener.onFetchFailed();
                    }


                    mListener.onFetchComplete();
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d(TAG, "Error :: " + error.getMessage());
                    mListener.onFetchComplete();
                }
            });
        }

    }

    public interface CountryCallbackListener {

        void onFetchStart();
        void onFetchProgress(Country country);
        void onFetchProgress(List<Country> countryList);
        void onFetchComplete();
        void onFetchFailed();
    }
}
package com.example.aishwarya.myapplication;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Created by Aishwarya on 4/1/16.
 */
public class Country {

    public String mName, mCapital, mRegion, mCode;
    public Integer mPopulation, mArea;
    public List<String> mLatLong = new ArrayList<String>();
    public List<String> mBorder = new ArrayList<String>();
    public List<String> mCurrencies = new ArrayList<String>();
    public List<String> mLanguages = new ArrayList<String>();

    private Country(Builder builder){
        mName = builder.mName;
        mCapital = builder.mCapital;
        mRegion = builder.mRegion;
        mPopulation = builder.mPopulation;
        mArea = builder.mArea;
        mCode = builder.mCode;
        mLatLong = builder.mLatLong;
        mBorder = builder.mBorder;
        mCurrencies = builder.mCurrencies;
        mLanguages = builder.mLanguages;

    }

    public static class Builder {

        private String mName, mCapital, mRegion, mCode;
        private Integer mPopulation, mArea;
        private List<String> mLatLong = new ArrayList<String>();
        private List<String> mBorder = new ArrayList<String>();
        private List<String> mCurrencies = new ArrayList<String>();
        private List<String> mLanguages = new ArrayList<String>();

        public Builder setName(String name) {
            mName = name;
            return Builder.this;
        }

        public Builder setCapital(String capital) {
            if(capital == null){
                mCapital = "";
            }
            else {
                mCapital = capital;
            }
            return Builder.this;
        }

        public Builder setRegion(String region) {
            if(region == null){
                mRegion = "";
            }
            else {
                mRegion = region;
            }
            return Builder.this;
        }

        public Builder setPopulation(Object population) {
            String tempPopulation = population.toString();
            if(tempPopulation == "null"){
                mPopulation = 0;
            }
            else {
                Double tempPopulationDouble = Double.parseDouble(tempPopulation);
                mPopulation =   tempPopulationDouble.intValue();
            }
            return Builder.this;
        }

        public Builder setArea(Object area) {
            String tempArea = area.toString();
            if(tempArea == "null"){
                mArea = 0;
            }
            else {
                Double tempAreaDouble = Double.parseDouble(tempArea);
                mArea = tempAreaDouble.intValue();
            }
            return Builder.this;
        }

        public Builder setCode(String code) {
            if(code == null){
                mCode = "";
            }
            else {
                mCode = code;
            }
            return Builder.this;
        }

        public Builder setLatLong(Object LatLong) {
            String tempLatLong =   LatLong.toString();
            tempLatLong = tempLatLong.substring(1, tempLatLong.length() - 1);
            mLatLong = Arrays.asList(tempLatLong.split(","));
            return Builder.this;
        }

        public Builder setBorder(Object Border) {
            String tempBorder =   Border.toString();
            tempBorder = tempBorder.substring(1, tempBorder.length() - 1);
            mBorder = Arrays.asList(tempBorder.split(","));
            return Builder.this;
        }

        public Builder setCurrencies(Object Currencies) {
            String tempCurrencies =   Currencies.toString();
            tempCurrencies = tempCurrencies.substring(1, tempCurrencies.length() - 1);
            mCurrencies = Arrays.asList(tempCurrencies.split(","));
            return Builder.this;
        }

        public Builder setLanguages(Object Languages) {
            String tempLanguages =   Languages.toString();
            tempLanguages = tempLanguages.substring(1, tempLanguages.length() - 1);
            mLanguages = Arrays.asList(tempLanguages.split(","));
            return Builder.this;
        }

        public Country build() {
            return new Country(Builder.this);
        }
    }


}
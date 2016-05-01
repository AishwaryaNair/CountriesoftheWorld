package com.example.aishwarya.myapplication;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.ShareActionProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.app.FragmentActivity;
import android.app.Activity;
import java.util.Locale;
import java.util.Currency;

import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Downloader;
import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by Aishwarya on 2/8/16.
 */
public class CountryDetailFragment extends Fragment {


    ShareActionProvider mShareActionProvider;

    View rootView = null;

    String mCountrycode;
    String mName;
    String mCapital;
    public static String PACKAGE_NAME;


    public static CountryDetailFragment newInstance() {
        CountryDetailFragment fragment = new CountryDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public CountryDetailFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setRetainInstance(true);
        PACKAGE_NAME = getActivity().getApplicationContext().getPackageName();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        if (menu.findItem(R.id.action_share) == null)
            inflater.inflate(R.menu.toolbar_countrydetailfragment, menu);

        MenuItem shareItem = menu.findItem(R.id.action_share);
        mShareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(shareItem);

        Intent intentShare = new Intent(Intent.ACTION_SEND);
        intentShare.setType("text/plain");
        String sendstring = "Name: " + mName + ", Capital: " + mCapital + ", Code: " + mCountrycode;
        intentShare.putExtra(Intent.EXTRA_TEXT, sendstring);
        mShareActionProvider.setShareIntent(intentShare);

        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {

            default:
                return super.onOptionsItemSelected(item);
        }
    }





    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(getArguments() != null){
            mCountrycode = getArguments().getString("Code");
            Integer temp = getArguments().getInt("Area");
            Log.d("TEMP", String.valueOf(temp));
            mCapital = getArguments().getString("Capital");
        }
        rootView = inflater.inflate(R.layout.fragment_countrydetail, container, false);

        final TextView capital = (TextView)rootView.findViewById(R.id.countrydetailcapital);
        final TextView region = (TextView)rootView.findViewById(R.id.countrydetailregion);
        final TextView area = (TextView)rootView.findViewById(R.id.countrydetailarea);
        final TextView code = (TextView)rootView.findViewById(R.id.countrydetailcode);
        final TextView population = (TextView)rootView.findViewById(R.id.countrydetailpopulation);
        final ImageView regionicon = (ImageView)rootView.findViewById(R.id.countrydetailregionimage);
        final TextView language = (TextView)rootView.findViewById(R.id.countrydetaillanguage);
        final TextView currency = (TextView)rootView.findViewById(R.id.countrydetailcurrency);
        final TextView latlng = (TextView)rootView.findViewById(R.id.countrydetaillatlng);
        mName = getArguments().getString("Name");
        capital.setText(getArguments().getString("Capital"));
        String mregion = getArguments().getString("Region");
        region.setText(mregion);
        String regiondrawable;
        if(mregion.equals("Asia")){
            regiondrawable = "@drawable/asia";
        }
        else if(mregion.equals("Africa")){
            regiondrawable = "@drawable/africa";
        }
        else if(mregion.equals("Europe")){
            regiondrawable = "@drawable/europe";
        }
        else if(mregion.equals("Americas")){
            regiondrawable = "@drawable/americas";
        }
        else{
            regiondrawable = "@drawable/australia";
        }

        int imageResource = getResources().getIdentifier(regiondrawable, null, PACKAGE_NAME);
        Drawable res = getResources().getDrawable(imageResource);
        regionicon.setImageDrawable(res);

        area.setText(String.valueOf(getArguments().getInt("Area")));
        population.setText(String.valueOf(getArguments().getInt("Population")));
        code.setText(getArguments().getString("Code"));
        ArrayList<String> classdetaillanguages = new ArrayList<String>();
        classdetaillanguages = getArguments().getStringArrayList("Languages");
        String mlanguagelist = "";
            for (int i = 0; i < classdetaillanguages.size(); i++) {
                if(!classdetaillanguages.get(i).equals("")) {
                    Locale locale = new Locale(classdetaillanguages.get(i).substring(1, classdetaillanguages.get(i).length() - 1));
                    mlanguagelist = mlanguagelist + locale.getDisplayName(locale) + "(" + classdetaillanguages.get(i).substring(1, classdetaillanguages.get(i).length() - 1) + "), ";
                }
                else {
                    mlanguagelist = "Not specified, ";
                }
            }
        mlanguagelist = mlanguagelist.substring(0, mlanguagelist.length() - 2);
        language.setText(mlanguagelist);

        ArrayList<String> classdetailcurrency = new ArrayList<String>();
        classdetailcurrency = getArguments().getStringArrayList("Currencies");
        String mcurrencylist = "";
        for (int i = 0; i < classdetailcurrency.size(); i++) {
            if(!classdetailcurrency.get(i).equals("")) {
                Currency c  = Currency.getInstance(classdetailcurrency.get(i).substring(1, classdetailcurrency.get(i).length() - 1));
                mcurrencylist = mcurrencylist + c.getDisplayName() + "(" + classdetailcurrency.get(i).substring(1, classdetailcurrency.get(i).length() - 1) + "), ";
                }
            else {
                mcurrencylist = "Not specified, ";
            }
        }
        mcurrencylist = mcurrencylist.substring(0, mcurrencylist.length() - 2);
        currency.setText(mcurrencylist);

        ArrayList<String> classdetaillatlong = new ArrayList<String>();

       ;
        String latitude;
        String longitude;
        if(mCountrycode.equals("UMI")){
            latitude = "19.2823";
            longitude = "166.6470";
        }
        else {
            classdetaillatlong = getArguments().getStringArrayList("LatLong");
            latitude = classdetaillatlong.get(0);
            longitude = classdetaillatlong.get(1);
        }
        latlng.setText("[" + latitude + ", " + longitude + "]");

        return rootView;
    }
}



package com.example.aishwarya.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ShareActionProvider;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.support.v4.view.ViewCompat;
import android.net.Uri;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.transition.Slide;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;

/**
 * Created by Aishwarya on 4/16/16.
 */
public class CountryDetail extends AppCompatActivity {
    ShareActionProvider mShareActionProvider;
    String countryCode;
    ImageView countrydetailflag;
    Bundle bundle;
    String mCountryName, latitude, longitude;
    ArrayList<String> latlong = new ArrayList<String>();
    //public static String PACKAGE_NAME;

    TextToSpeech t1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countrydetail);
        Firebase.setAndroidContext(this);
        bundle = getIntent().getExtras();
        countryCode = bundle.getString("Code");
        mCountryName = bundle.getString("Name");
        latlong = bundle.getStringArrayList("LatLong");
        if(countryCode.equals("UMI")){
            latitude = "19.2823";
            longitude = "166.6470";
        }
        else {
            latitude = latlong.get(0);
            longitude = latlong.get(1);
        }

        final Toolbar toolbar = (Toolbar) findViewById(R.id.countrydetailtoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsingtoolbar);
        collapsingToolbar.setTitle(mCountryName);
        collapsingToolbar.setExpandedTitleTextAppearance(R.style.ExpandedAppBar);
        collapsingToolbar.setCollapsedTitleTextAppearance(R.style.CollapsedAppBar);

        countrydetailflag = (ImageView) findViewById(R.id.countrydetailflag);
        countrydetailflag.setTransitionName("transitionimage");
        final String firbaseurl =  "https://countryurllist.firebaseio.com/worldcountries/" + countryCode;
        Firebase myFirebaseRef = new Firebase(firbaseurl);
        myFirebaseRef.child("flag").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                final Object flagobject = snapshot.getValue();
                final String flagurl = flagobject.toString();
                Picasso.with(getBaseContext()).load(flagurl).into(countrydetailflag);
            }

            @Override
            public void onCancelled(FirebaseError error) {
            }
        });



        if (savedInstanceState == null) {
            CountryDetailFragment countryDetailFragment = new CountryDetailFragment();
            countryDetailFragment.setArguments(bundle);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.countrydetailcontainer, countryDetailFragment)
                    .commit();
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.floatingmap);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(CountryDetail.this, MapActivity.class);
                Bundle mapbundle = new Bundle();
                mapbundle.putString("Name", mCountryName);
                mapbundle.putString("Latitude", latitude);
                mapbundle.putString("Longitude", longitude);
                intent1.putExtras(mapbundle);
                startActivity(intent1);

            }
        });

        //PACKAGE_NAME = getApplicationContext().getPackageName();
        t1 = new TextToSpeech(this.getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status == TextToSpeech.SUCCESS) {
                    Log.d("Tag", mCountryName);
                    t1.setLanguage(Locale.ENGLISH);
                }
            }
        });
        //setupWindowAnimations();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_countryactivity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.wikipedia:
                final String wikiurl =  "https://countryurllist.firebaseio.com/worldcountries/" + countryCode;
                Firebase myFirebaseRef = new Firebase(wikiurl);
                myFirebaseRef.child("wiki").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot snapshot) {
                        final Object wikiobject = snapshot.getValue();
                        final String wikiurl = wikiobject.toString();
                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(wikiurl));
                        startActivity(browserIntent);

                    }

                    @Override
                    public void onCancelled(FirebaseError error) {
                    }
                });
                break;

            case R.id.voice:
                t1.speak(mCountryName, TextToSpeech.QUEUE_FLUSH, null);
                return true;

            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void onPause(){
        if(t1 !=null){
            t1.stop();
            t1.shutdown();
        }
        super.onPause();
    }

    private void setupWindowAnimations() {
        Slide slide = new Slide();
        slide.setDuration(1000);
        getWindow().setExitTransition(slide);
    }

}

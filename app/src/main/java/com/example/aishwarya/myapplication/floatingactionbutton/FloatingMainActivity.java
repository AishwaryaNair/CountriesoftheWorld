package com.example.aishwarya.myapplication.floatingactionbutton;

import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.ActivityCompat;
import android.widget.ImageView;
import android.support.v4.util.Pair;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

import com.example.aishwarya.myapplication.navigation.QuizAcitvity;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

import com.example.aishwarya.myapplication.Controller;
import com.example.aishwarya.myapplication.Country;
import com.example.aishwarya.myapplication.CountryAdapter;
import com.example.aishwarya.myapplication.CountryDetail;
import com.example.aishwarya.myapplication.R;

/**
 * Created by Aishwarya on 4/1/16.
 */

public class FloatingMainActivity extends AppCompatActivity implements Controller.CountryCallbackListener {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Country> mCountryList = new ArrayList<>();
    private CountryAdapter mCountriesAdapter;
    private Controller mController;
    Bundle bundle;
    String region;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_floatingmain);
        Firebase.setAndroidContext(this);
        bundle = getIntent().getExtras();
        region = bundle.getString("Region");
        configToolbar();

        mController = new Controller(FloatingMainActivity.this);
        configViews();
        mController.startFetching(region);
        adapterAnimation();
    }


    private void configToolbar() {
        mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        mToolbar.setTitle("Countries of " + region);
        setSupportActionBar(mToolbar);
    }

    private void configViews() {
        mRecyclerView = (RecyclerView) this.findViewById(R.id.list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipe);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(FloatingMainActivity.this));
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());

        mCountriesAdapter = new CountryAdapter(mCountryList);
        mRecyclerView.setAdapter(mCountriesAdapter);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mController.startFetching(region);
            }
        });

        mCountriesAdapter.setOnItemClickListener(new CountryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, ImageView mImage) {
                Country clickedcountry = mCountryList.get(position);
                Intent intent1 = new Intent(FloatingMainActivity.this, CountryDetail.class);
                Bundle bundle = new Bundle();
                bundle.putString("Name", clickedcountry.mName);
                bundle.putString("Capital", clickedcountry.mCapital);
                bundle.putString("Region", clickedcountry.mRegion);
                bundle.putInt("Population", clickedcountry.mPopulation);
                bundle.putInt("Area", clickedcountry.mArea);
                bundle.putString("Code", clickedcountry.mCode);
                bundle.putStringArrayList("Borders", new ArrayList<String>(clickedcountry.mBorder));
                bundle.putStringArrayList("LatLong", new ArrayList<String>(clickedcountry.mLatLong));
                bundle.putStringArrayList("Currencies", new ArrayList<String>(clickedcountry.mCurrencies));
                bundle.putStringArrayList("Languages", new ArrayList<String>(clickedcountry.mLanguages));
                intent1.putExtras(bundle);
                Pair<View, String> imagePair = Pair.create((View) mImage, "transitionimage");
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(FloatingMainActivity.this,
                        imagePair);
                ActivityCompat.startActivity(FloatingMainActivity.this, intent1, options.toBundle());
            }

        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onFetchStart() {

    }

    @Override
    public void onFetchProgress(Country country) {
        mCountriesAdapter.addCountry(country);
    }

    @Override
    public void onFetchProgress(List<Country> flowerList) {

    }

    @Override
    public void onFetchComplete() {
        mSwipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onFetchFailed() {
    }

    private void adapterAnimation() {
        AlphaInAnimationAdapter alphaAdapter = new AlphaInAnimationAdapter(mCountriesAdapter);
        ScaleInAnimationAdapter scaleAdapter = new ScaleInAnimationAdapter(alphaAdapter);
        mRecyclerView.setAdapter(scaleAdapter);
    }

}
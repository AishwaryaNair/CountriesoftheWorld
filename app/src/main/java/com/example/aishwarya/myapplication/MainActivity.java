package com.example.aishwarya.myapplication;

import android.content.Intent;
import android.os.Build;
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
import android.widget.FrameLayout;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.graphics.PorterDuff;

import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import jp.wasabeef.recyclerview.adapters.ScaleInAnimationAdapter;

import com.example.aishwarya.myapplication.floatingactionbutton.FloatingMainActivity;
import com.example.aishwarya.myapplication.navigation.QuizAcitvity;
import com.firebase.client.Firebase;

import java.util.ArrayList;
import java.util.List;

import com.oguzdev.circularfloatingactionmenu.library.FloatingActionMenu;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.oguzdev.circularfloatingactionmenu.library.SubActionButton;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton.LayoutParams;


/**
 * Created by Aishwarya on 4/1/16.
 */

public class MainActivity extends AppCompatActivity implements Controller.CountryCallbackListener, NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<Country> mCountryList = new ArrayList<>();
    private CountryAdapter mCountriesAdapter;
    private Controller mController;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Firebase.setAndroidContext(this);
        configToolbar();

        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(this);

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);
        ActionBarDrawerToggle actionBarDrawerToggle =
                new ActionBarDrawerToggle(this, drawerLayout, mToolbar, R.string.OpenDrawer, R.string.CloseDrawer) {

                    @Override
                    public void onDrawerClosed(View drawerView) {
                        super.onDrawerClosed(drawerView);
                    }

                    @Override
                    public void onDrawerOpened(View drawerView) {
                        super.onDrawerOpened(drawerView);
                    }
                };

        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

        mController = new Controller(MainActivity.this);
        configViews();
        mController.startFetching(null);
        adapterAnimation();

        int redActionButtonSize = getResources().getDimensionPixelSize(R.dimen.red_action_button_size);
        int redActionButtonContentSize = getResources().getDimensionPixelSize(R.dimen.red_action_button_content_size);
        int blueSubActionButtonSize = getResources().getDimensionPixelSize(R.dimen.blue_sub_action_button_size);

        final ImageView fabIconStar = new ImageView(this);
        fabIconStar.setImageDrawable(getResources().getDrawable(R.drawable.floatingplus));
        fabIconStar.getDrawable().setColorFilter(getResources().getColor(R.color.textcolor), PorterDuff.Mode.SRC_ATOP );

        FloatingActionButton.LayoutParams starParams = new FloatingActionButton.LayoutParams(redActionButtonSize, redActionButtonSize);
        fabIconStar.setLayoutParams(starParams);

        FloatingActionButton.LayoutParams fabIconStarParams = new FloatingActionButton.LayoutParams(redActionButtonContentSize, redActionButtonContentSize);

        final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(this)
                .setContentView(fabIconStar)
                .build();

        fabIconStar.setLayoutParams(fabIconStarParams);

        SubActionButton.Builder lCSubBuilder = new SubActionButton.Builder(this);
       // lCSubBuilder.setBackgroundDrawable(getResources().getDrawable(R.drawable.button_action_blue_selector));

        FrameLayout.LayoutParams blueContentParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT);
        lCSubBuilder.setLayoutParams(blueContentParams);
        // Set custom layout params
        FrameLayout.LayoutParams blueParams = new FrameLayout.LayoutParams(blueSubActionButtonSize, blueSubActionButtonSize);
        lCSubBuilder.setLayoutParams(blueParams);

        final ImageView lcIcon1 = new ImageView(this);
        final ImageView lcIcon2 = new ImageView(this);
        final ImageView lcIcon3 = new ImageView(this);
        final ImageView lcIcon4 = new ImageView(this);
        final ImageView lcIcon5 = new ImageView(this);

        lcIcon1.setImageDrawable(getResources().getDrawable(R.drawable.asia));
        lcIcon2.setImageDrawable(getResources().getDrawable(R.drawable.americas));
        lcIcon3.setImageDrawable(getResources().getDrawable(R.drawable.europe));
        lcIcon4.setImageDrawable(getResources().getDrawable(R.drawable.australia));
        lcIcon5.setImageDrawable(getResources().getDrawable(R.drawable.africa));

        final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(lCSubBuilder.setContentView(lcIcon1).build())
                .addSubActionView(lCSubBuilder.setContentView(lcIcon2).build())
                .addSubActionView(lCSubBuilder.setContentView(lcIcon3).build())
                .addSubActionView(lCSubBuilder.setContentView(lcIcon4).build())
                .addSubActionView(lCSubBuilder.setContentView(lcIcon5).build())
                .attachTo(rightLowerButton)
                .build();

        // Listen menu open and close events to animate the button content view
        rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                fabIconStar.setRotation(0);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconStar, pvhR);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                fabIconStar.setRotation(45);
                PropertyValuesHolder pvhR = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconStar, pvhR);
                animation.start();
            }
        });

        lcIcon1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FloatingMainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Region", "Asia");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        lcIcon2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FloatingMainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Region", "Americas");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        lcIcon3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lcIcon3.setTransitionName("floatingtransition");
                Intent intent = new Intent(MainActivity.this, FloatingMainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Region", "Europe");
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });

        lcIcon4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lcIcon4.setTransitionName("floatingtransition");
                Intent intent = new Intent(MainActivity.this, FloatingMainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Region", "Oceania");
                intent.putExtras(bundle);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, v,"floatingtransition");
                    startActivity(intent, options.toBundle());
                }
                else {
                    startActivity(intent);
                }
            }
        });

        lcIcon5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lcIcon5.setTransitionName("floatingtransition");
                Intent intent = new Intent(MainActivity.this, FloatingMainActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("Region", "Africa");
                intent.putExtras(bundle);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
                    ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this, v,"floatingtransition");
                    startActivity(intent, options.toBundle());
                }
                else {
                    startActivity(intent);
                }
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id) {
            case R.id.quizes:
                Intent intent1 = new Intent(MainActivity.this, QuizAcitvity.class);
                startActivity(intent1);
                break;

            case R.id.logout:
                final Firebase ref = new Firebase("https://countryurllist.firebaseio.com/worldcountries/");
                ref.unauth();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                break;

            default:
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void configToolbar() {
        mToolbar = (Toolbar) this.findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    private void configViews() {
        mRecyclerView = (RecyclerView) this.findViewById(R.id.list);
        mSwipeRefreshLayout = (SwipeRefreshLayout) this.findViewById(R.id.swipe);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        mRecyclerView.setRecycledViewPool(new RecyclerView.RecycledViewPool());

        mCountriesAdapter = new CountryAdapter(mCountryList);
        mRecyclerView.setAdapter(mCountriesAdapter);

        mSwipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent),
                getResources().getColor(R.color.colorPrimary),
                getResources().getColor(R.color.colorPrimaryDark));

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mController.startFetching(null);
            }
        });

        mCountriesAdapter.setOnItemClickListener(new CountryAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position, ImageView mImage) {
                Country clickedcountry = mCountryList.get(position);
                Intent intent1 = new Intent(MainActivity.this, CountryDetail.class);
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
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(MainActivity.this,
                        imagePair);
                ActivityCompat.startActivity(MainActivity.this, intent1, options.toBundle());
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
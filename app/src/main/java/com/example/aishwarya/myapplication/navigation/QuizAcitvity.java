package com.example.aishwarya.myapplication.navigation;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.aishwarya.myapplication.MainActivity;
import com.example.aishwarya.myapplication.quizzes.CapitalQuizActivity;
import com.example.aishwarya.myapplication.R;
import com.example.aishwarya.myapplication.quizzes.FlagQuizActivity;

/**
 * Created by Aishwarya on 4/28/16.
 */
public class QuizAcitvity extends AppCompatActivity implements CapitalquizFragment.OnCapitalButtonClicked,FlagquizFragment.OnFlagButtonClicked {

    MyFragmentPagerAdapter myPagerAdapter;
    ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        myPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),2);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setCurrentItem(0);
        customizeViewPager();

        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(mViewPager);
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.quiztoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("PLAY QUIZZES");

    }

    private void customizeViewPager() {
        mViewPager.setPageTransformer(false, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View page, float position) {
                final float normalized_position = Math.abs(Math.abs(position) - 1);
                page.setScaleX(normalized_position / 2 + 0.5f);
                page.setScaleY(normalized_position /2 + 0.5f);
            }
        });
    }


    public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        int count;
        public MyFragmentPagerAdapter(FragmentManager fm, int size) {
            super(fm);
            count = size;
        }

        @Override
        public int getCount() {
            return count;
        }

        @Override
        public Fragment getItem(int position){
            switch(position) {
                case 0:
                    return CapitalquizFragment.newInstance();
                case 1:
                    return FlagquizFragment.newInstance();
                default:
                    return FlagquizFragment.newInstance();
            }
        }

        @Override
        public CharSequence getPageTitle(int position) {
            String name;
            switch(position) {
                case 0:
                    name = "COUNTRY QUIZ";
                    break;
                case 1:
                    name = "FLAG QUIZ";
                    break;
                default:
                    name = "FLAG QUIZ";
                    break;
            }
            return name;
        }
    }

    public void onCapitalButtonClicked(){
        Intent intent = new Intent(this, CapitalQuizActivity.class);

        startActivity(intent);
    }

    public void onFlagButtonClicked(){
        Intent intent = new Intent(this, FlagQuizActivity.class);

        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}

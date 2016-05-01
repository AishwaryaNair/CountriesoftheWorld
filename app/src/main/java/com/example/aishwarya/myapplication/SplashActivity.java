package com.example.aishwarya.myapplication;

import android.content.Intent;
import android.os.Build;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.graphics.drawable.AnimationDrawable;
import android.widget.ImageView;
import android.view.View;


/**
 * Created by Aishwarya on 4/27/16.
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView spinningworld = (ImageView) findViewById(R.id.spinningworld);
        spinningworld.setBackgroundResource(R.drawable.animation);
        AnimationDrawable spiningAnimation = (AnimationDrawable) spinningworld.getBackground();
        spiningAnimation.start();

        spinningworld.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent1 = new Intent(SplashActivity.this, LoginActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptionsCompat options = ActivityOptionsCompat.
                            makeSceneTransitionAnimation(SplashActivity.this, v, "testAnimation");
                    startActivity(intent1, options.toBundle());
                }
                else {
                    startActivity(intent1);
                }
            }
        });
    }


}

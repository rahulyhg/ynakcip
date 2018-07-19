package com.prism.pickany247;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.prism.pickany247.StationeryModule.StationeryHomeActivity;

public class SplashActivity extends Activity {
    int SPLASH_TIME_OUT = 2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        final android.support.v7.widget.AppCompatImageView imageView =(android.support.v7.widget.AppCompatImageView)findViewById(R.id.imgIcon);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                // shared animation between two activites
                Intent intent  = new Intent(SplashActivity.this, HomeActivity.class);
                Pair[] pair =new Pair[1];
                pair[0]=new Pair<View,String>(imageView,"imageTransision");
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(SplashActivity.this, pair);
                startActivity(intent, options.toBundle());
            }
        }, SPLASH_TIME_OUT);
    }
}

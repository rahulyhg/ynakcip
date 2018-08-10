package com.prism.pickany247.Modules.Rentals;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterViewFlipper;
import android.widget.FrameLayout;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.prism.pickany247.Adapters.FlipperAdapter;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.Helper.PrefManager;
import com.prism.pickany247.R;
import com.prism.pickany247.Response.CatResponse;
import com.prism.pickany247.Singleton.AppController;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RentalsHomeActivity extends AppCompatActivity {

    @BindView(R.id.btn1)
    RadioButton btn1;
    @BindView(R.id.btn2)
    RadioButton btn2;
    @BindView(R.id.rdogrp)
    RadioGroup rdogrp;
    @BindView(R.id.rentalRecyclerView)
    RecyclerView rentalRecyclerView;


    CatResponse homeResponse = new CatResponse();
    Gson gson;
    @BindView(R.id.adapterViewFlipper)
    AdapterViewFlipper adapterViewFlipper;
    private PrefManager pref;
    String userid;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rentals_home1);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(" Car & Bike Rentals ");

        prepareCatData();



    }




    private void prepareCatData() {


        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.GROCERY_HOME_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("CATURL", "" + Api.GROCERY_HOME_URL);
                Log.e("RESPONSE", "" + response);


                gson = new Gson();
                homeResponse = gson.fromJson(response, CatResponse.class);

                FlipperAdapter adapter = new FlipperAdapter(getApplicationContext(), homeResponse.getBanners());
                adapterViewFlipper.setAdapter(adapter);
                adapterViewFlipper.setFlipInterval(4000);
                adapterViewFlipper.startFlipping();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                if (error instanceof NetworkError) {
                } else if (error instanceof ServerError) {
                    Toast.makeText(getApplicationContext(), "Oops. Server error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof AuthFailureError) {
                    Toast.makeText(getApplicationContext(), "Oops. AuthFailure error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof ParseError) {
                    Toast.makeText(getApplicationContext(), "Oops. Parse error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof NoConnectionError) {
                    Toast.makeText(getApplicationContext(), "Oops. Connection error!", Toast.LENGTH_LONG).show();
                } else if (error instanceof TimeoutError) {
                    Toast.makeText(getApplicationContext(), "Oops. Timeout error!", Toast.LENGTH_LONG).show();
                }
            }
        });
        // Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
       /* RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);*/


    }







    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                invalidateOptionsMenu();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

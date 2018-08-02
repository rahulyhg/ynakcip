package com.prism.pickany247;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.prism.pickany247.Adapters.WishListAdapter;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.Helper.PrefManager;
import com.prism.pickany247.Response.WishlistResponse;
import com.prism.pickany247.Singleton.AppController;

import java.util.HashMap;

public class WishListActivity extends AppCompatActivity {
    AppController appController;
    private ProgressDialog pDialog;

    private WishListAdapter wishListAdapter;
    private PrefManager pref;
    private RecyclerView recyclerViewCart;
    WishlistResponse wishlistResponse = new WishlistResponse();
    Gson gson;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wish_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("My Wishlist");

        appController = (AppController) getApplication();
        pref = new PrefManager(getApplicationContext());
        // Displaying user information from shared preferences
        HashMap<String, String> profile = pref.getUserDetails();
        userId=profile.get("id");

        recyclerViewCart = (RecyclerView) findViewById(R.id.recyclerWishlist);

        if (appController.isConnection()) {

            // Showing progress dialog before making http request
            pDialog = new ProgressDialog(this);

            pDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.show();
            pDialog.setContentView(R.layout.my_progress);

            wishListData();


        } else {

            setContentView(R.layout.internet);

            Button tryButton = (Button) findViewById(R.id.btnTryagain);
            tryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });

            // app.internetDilogue(KitchenitemListActivity.this);

        }


    }

    private void wishListData(){

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.ADD_WISHLIST_URL +"?user_id="+userId, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {


                hidePDialog();
                gson = new Gson();
                wishlistResponse = gson.fromJson(response, WishlistResponse.class);


                // wishlist Adapter
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
                recyclerViewCart.setLayoutManager(mLayoutManager);
                recyclerViewCart.setItemAnimator(new DefaultItemAnimator());

                wishListAdapter = new WishListAdapter(WishListActivity.this, wishlistResponse.getWishList());
                recyclerViewCart.setAdapter(wishListAdapter);
                wishListAdapter.notifyDataSetChanged();




                //dynamic carAdapter
                // carAdapter.registerDataSetObserver(indicator.getDataSetObserver());


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                hidePDialog();
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
        RequestQueue requestQueue = Volley.newRequestQueue(WishListActivity.this);
        requestQueue.add(stringRequest);


    }



    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;


        }
        return super.onOptionsItemSelected(item);
    }

}

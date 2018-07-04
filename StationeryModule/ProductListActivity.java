package com.prism.pickany247.StationeryModule;

import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.prism.pickany247.Adapters.SationeryProductAadpter;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.HomeActivity;
import com.prism.pickany247.R;
import com.prism.pickany247.Response.StationeryResponse;
import com.prism.pickany247.Singleton.AppController;

public class ProductListActivity extends AppCompatActivity {
    SwipeRefreshLayout swipeRefreshLayout;
    AppController appController;
    private SationeryProductAadpter adapter;
    private RecyclerView rcProduct;
    Gson gson;
    StationeryResponse stationeryResponse =new StationeryResponse();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_list);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final String id = getIntent().getStringExtra("catId");
        String title =getIntent().getStringExtra("title");

        getSupportActionBar().setTitle(title);
        appController=(AppController)getApplicationContext();


        // init SwipeRefreshLayout and ListView
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.simpleSwipeRefreshLayout);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorBlue,R.color.colorPrimary);

        rcProduct = (RecyclerView) findViewById(R.id.rcProduct);


        if (appController.isConnection()) {

            prepareProductData(id);

            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {

                    prepareProductData(id);
                    swipeRefreshLayout.setRefreshing(false);
                }
            });





        } else {

            setContentView(R.layout.internet);

            Button tryButton =(Button)findViewById(R.id.btnTryagain);
            tryButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Intent intent =new Intent(getApplicationContext(),HomeActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                }
            });

            // app.internetDilogue(KitchenitemListActivity.this);

        }

    }

    private void prepareProductData(String id){

        swipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.PRODUCTS_URL+id, new com.android.volley.Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                swipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                stationeryResponse = gson.fromJson(response, StationeryResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),2);
                rcProduct.setLayoutManager(mLayoutManager);
                rcProduct.setItemAnimator(new DefaultItemAnimator());

                adapter = new SationeryProductAadpter(getApplicationContext(), stationeryResponse.getFiltered_products());
                rcProduct.setAdapter(adapter);
                adapter.notifyDataSetChanged();


                //dynamic carAdapter
                // carAdapter.registerDataSetObserver(indicator.getDataSetObserver());


            }
        }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                swipeRefreshLayout.setRefreshing(false);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


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

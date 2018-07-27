package com.prism.pickany247.GroceryModule;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.prism.pickany247.Adapters.SationeryProductAadpter;
import com.prism.pickany247.Adapters.StationeryCatAdapter;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.CartActivity;
import com.prism.pickany247.HomeActivity;
import com.prism.pickany247.R;
import com.prism.pickany247.Response.StationeryCatResponse;
import com.prism.pickany247.Response.StationeryResponse;
import com.prism.pickany247.Singleton.AppController;
import com.prism.pickany247.ProductListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

public class GroceryHomeActivity extends AppCompatActivity{
    AppController appController;
    @BindView(R.id.catRecycler)
    RecyclerView catRecycler;
    @BindView(R.id.banner_slider1)
    BannerSlider bannerSlider1;
    @BindView(R.id.txtFruits)
    TextView txtFruits;
    @BindView(R.id.fruitsViewAll)
    TextView fruitsViewAll;
    @BindView(R.id.fruitsRecycler)
    RecyclerView fruitsRecycler;
    @BindView(R.id.txtVegTitle)
    TextView txtVegTitle;
    @BindView(R.id.vegViewAll)
    TextView vegViewAll;
    @BindView(R.id.vegRecycler)
    RecyclerView vegRecycler;
    @BindView(R.id.txtBevrageTitle)
    TextView txtBevrageTitle;
    @BindView(R.id.bevrageViewAll)
    TextView bevrageViewAll;
    @BindView(R.id.bevrageRecycler)
    RecyclerView bevrageRecycler;
    @BindView(R.id.txtSnacksTitle)
    TextView txtSnacksTitle;
    @BindView(R.id.snacksViewAll)
    TextView snacksViewAll;
    @BindView(R.id.snacksRecycler)
    RecyclerView snacksRecycler;
    @BindView(R.id.txtredytoeatTitle)
    TextView txtredytoeatTitle;
    @BindView(R.id.readyViewAll)
    TextView readyViewAll;
    @BindView(R.id.readytoeatRecycler)
    RecyclerView readytoeatRecycler;
    @BindView(R.id.simpleSwipeRefreshLayout)
    SwipeRefreshLayout simpleSwipeRefreshLayout;

    private StationeryCatAdapter adapter;
    private SationeryProductAadpter stationeryHomeAdapter;
    StationeryCatResponse homeResponse = new StationeryCatResponse();
    StationeryResponse stationeryResponse = new StationeryResponse();
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grocery_home);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Grocery");

        appController = (AppController) getApplication();

        simpleSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorBlue, R.color.colorPrimary);
        appController = (AppController) getApplicationContext();
        if (appController.isConnection()) {

            prepareCatData();


            simpleSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                @Override
                public void onRefresh() {
                    prepareCatData();

                    simpleSwipeRefreshLayout.setRefreshing(false);
                }
            });

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


        }


    }

    private void prepareCatData() {

        simpleSwipeRefreshLayout.setRefreshing(true);
        Log.e("CATURL",""+Api.GROCERY_HOME_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.GROCERY_HOME_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                homeResponse = gson.fromJson(response, StationeryCatResponse.class);

                for (StationeryCatResponse.CategoriesBean mainCategoriesBean : homeResponse.getCategories()) {

                    if (mainCategoriesBean.getId().equals("14")){

                        prepareFruitsData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name(),mainCategoriesBean.getModule());

                    }
                    else if (mainCategoriesBean.getId().equals("2")){

                        prepareVegData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name(),mainCategoriesBean.getModule());

                    }
                    else if (mainCategoriesBean.getId().equals("7")){

                        prepareBevragesData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name(),mainCategoriesBean.getModule());

                    }
                    else if (mainCategoriesBean.getId().equals("11")){

                        prepareSnacksData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name(),mainCategoriesBean.getModule());

                    }
                    else if (mainCategoriesBean.getId().equals("15")){

                        prepareReadyData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name(),mainCategoriesBean.getModule());

                    }

                }

                // home Adapter
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                catRecycler.setLayoutManager(mLayoutManager);
                catRecycler.setItemAnimator(new DefaultItemAnimator());

                adapter = new StationeryCatAdapter(getApplicationContext(), homeResponse.getCategories());
                catRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();


                // home banners
                List<Banner> banners = new ArrayList<>();
                banners.clear();
                Log.e("BANNERS", "" + banners.size());
                for (final StationeryCatResponse.BannersBean homeBannersBean : homeResponse.getBanners()) {

                    banners.add(new RemoteBanner(homeBannersBean.getImage()));

                }

                BannerSlider bannerSlider = (BannerSlider) findViewById(R.id.banner_slider1);

                //add banner using image url
                // banners.add(new RemoteBanner("Put banner image url here ..."));
                //add banner using resource drawable
                bannerSlider.setBanners(banners);
                bannerSlider.onIndicatorSizeChange();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleSwipeRefreshLayout.setRefreshing(true);
            }
        });
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
       /* RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);*/


    }

    private void prepareFruitsData(final String id, final String catname,final String module) {

        simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.GROCERY_PRODUCT_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                stationeryResponse = gson.fromJson(response, StationeryResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                fruitsRecycler.setLayoutManager(mLayoutManager);
                fruitsRecycler.setItemAnimator(new DefaultItemAnimator());

                stationeryHomeAdapter = new SationeryProductAadpter(getApplicationContext(), stationeryResponse.getFiltered_products());
                fruitsRecycler.setAdapter(stationeryHomeAdapter);
                stationeryHomeAdapter.notifyDataSetChanged();

                // textview
                txtFruits.setText(catname);
                fruitsViewAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent1 = new Intent(getApplicationContext(), ProductListActivity.class);
                        intent1.putExtra("catId", id);
                        intent1.putExtra("title", catname);
                        intent1.putExtra("module", module);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleSwipeRefreshLayout.setRefreshing(false);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void prepareVegData(final String id, final String catname,final String module) {

        simpleSwipeRefreshLayout.setRefreshing(false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.GROCERY_PRODUCT_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                stationeryResponse = gson.fromJson(response, StationeryResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                vegRecycler.setLayoutManager(mLayoutManager);
                vegRecycler.setItemAnimator(new DefaultItemAnimator());

                stationeryHomeAdapter = new SationeryProductAadpter(getApplicationContext(), stationeryResponse.getFiltered_products());
                vegRecycler.setAdapter(stationeryHomeAdapter);
                stationeryHomeAdapter.notifyDataSetChanged();


                // textview
                txtVegTitle.setText(catname);
                vegViewAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent1 = new Intent(getApplicationContext(), ProductListActivity.class);
                        intent1.putExtra("catId", id);
                        intent1.putExtra("title", catname);
                        intent1.putExtra("module", module);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleSwipeRefreshLayout.setRefreshing(false);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void prepareBevragesData(final String id, final String catname,final String module) {

        simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.GROCERY_PRODUCT_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                stationeryResponse = gson.fromJson(response, StationeryResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                bevrageRecycler.setLayoutManager(mLayoutManager);
                bevrageRecycler.setItemAnimator(new DefaultItemAnimator());

                stationeryHomeAdapter = new SationeryProductAadpter(getApplicationContext(), stationeryResponse.getFiltered_products());
                bevrageRecycler.setAdapter(stationeryHomeAdapter);
                stationeryHomeAdapter.notifyDataSetChanged();

                // textview
                txtBevrageTitle.setText(catname);
                bevrageViewAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent1 = new Intent(getApplicationContext(), ProductListActivity.class);
                        intent1.putExtra("catId", id);
                        intent1.putExtra("title", catname);
                        intent1.putExtra("module", module);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleSwipeRefreshLayout.setRefreshing(false);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void prepareSnacksData(final String id, final String catname,final String module) {

        simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.GROCERY_PRODUCT_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                stationeryResponse = gson.fromJson(response, StationeryResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                snacksRecycler.setLayoutManager(mLayoutManager);
                snacksRecycler.setItemAnimator(new DefaultItemAnimator());

                stationeryHomeAdapter = new SationeryProductAadpter(getApplicationContext(), stationeryResponse.getFiltered_products());
                snacksRecycler.setAdapter(stationeryHomeAdapter);
                stationeryHomeAdapter.notifyDataSetChanged();

                // textview
                txtSnacksTitle.setText(catname);
                snacksViewAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent1 = new Intent(getApplicationContext(), ProductListActivity.class);
                        intent1.putExtra("catId", id);
                        intent1.putExtra("title", catname);
                        intent1.putExtra("module", module);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleSwipeRefreshLayout.setRefreshing(false);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void prepareReadyData(final String id, final String catname,final String module) {

        simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.GROCERY_PRODUCT_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                stationeryResponse = gson.fromJson(response, StationeryResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                readytoeatRecycler.setLayoutManager(mLayoutManager);
                readytoeatRecycler.setItemAnimator(new DefaultItemAnimator());

                stationeryHomeAdapter = new SationeryProductAadpter(getApplicationContext(), stationeryResponse.getFiltered_products());
                readytoeatRecycler.setAdapter(stationeryHomeAdapter);
                stationeryHomeAdapter.notifyDataSetChanged();

                // textview
                txtredytoeatTitle.setText(catname);
                readyViewAll.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent1 = new Intent(getApplicationContext(), ProductListActivity.class);
                        intent1.putExtra("catId", id);
                        intent1.putExtra("title", catname);
                        intent1.putExtra("module", module);
                        intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent1);
                        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                simpleSwipeRefreshLayout.setRefreshing(false);
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    ///
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                onBackPressed();
                break;

            case R.id.action_cart:
                Intent intent =new Intent(getApplicationContext(),CartActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                break;

            case R.id.action_search:

                break;
        }
        return super.onOptionsItemSelected(item);
    }


}

package com.prism.pickany247.Modules.Celebrations;

import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.prism.pickany247.Adapters.CatageoryAdapter;
import com.prism.pickany247.Adapters.ProductAadpter;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.Helper.Converter;
import com.prism.pickany247.Helper.PrefManager;
import com.prism.pickany247.HomeActivity;
import com.prism.pickany247.ProductDetailsActivity;
import com.prism.pickany247.ProductListActivity;
import com.prism.pickany247.R;
import com.prism.pickany247.Response.CatResponse;
import com.prism.pickany247.Response.ProductResponse;
import com.prism.pickany247.Singleton.AppController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ss.com.bannerslider.banners.Banner;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

public class CelebrationsHomeActivity extends AppCompatActivity {
    AppController appController;
    private CatageoryAdapter adapter;
    private ProductAadpter stationeryHomeAdapter;
    CatResponse homeResponse = new CatResponse();
    ProductResponse productResponse = new ProductResponse();
    Gson gson;
    @BindView(R.id.catRecycler)
    RecyclerView catRecycler;
    @BindView(R.id.banner_slider1)
    BannerSlider bannerSlider1;
    @BindView(R.id.txtCakes)
    TextView txtCakes;
    @BindView(R.id.cakesViewAll)
    TextView cakesViewAll;
    @BindView(R.id.cakesRecycler)
    RecyclerView cakesRecycler;
    @BindView(R.id.txtFlowersTitle)
    TextView txtFlowersTitle;
    @BindView(R.id.flowersViewAll)
    TextView flowersViewAll;
    @BindView(R.id.flowersRecycler)
    RecyclerView flowersRecycler;
    @BindView(R.id.txtCombosTitle)
    TextView txtCombosTitle;
    @BindView(R.id.combosViewAll)
    TextView combosViewAll;
    @BindView(R.id.combosRecycler)
    RecyclerView combosRecycler;
    @BindView(R.id.txtGiftHouseTitle)
    TextView txtGiftHouseTitle;
    @BindView(R.id.gift_HouseViewAll)
    TextView giftHouseViewAll;
    @BindView(R.id.gift_HouseRecycler)
    RecyclerView giftHouseRecycler;
    @BindView(R.id.txtAccessoriesTitle)
    TextView txtAccessoriesTitle;
    @BindView(R.id.accessoriesViewAll)
    TextView accessoriesViewAll;
    @BindView(R.id.accessoriesRecycler)
    RecyclerView accessoriesRecycler;
    @BindView(R.id.simpleSwipeRefreshLayout)
    SwipeRefreshLayout simpleSwipeRefreshLayout;

    private PrefManager pref;
    String userid;
    int  cartindex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_celebrations_home);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Celebrations");

        appController = (AppController) getApplication();

        pref = new PrefManager(getApplicationContext());

        // Displaying user information from shared preferences
        HashMap<String, String> profile = pref.getUserDetails();
        userid = profile.get("id");

        simpleSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorBlue, R.color.colorPrimary);
        appController = (AppController) getApplicationContext();
        if (appController.isConnection()) {

            prepareCatData();
            // cart count
            appController.cartCount(userid);
            SharedPreferences preferences =getSharedPreferences("CARTCOUNT",0);
            cartindex =preferences.getInt("itemCount",0);
            Log.e("cartindex",""+cartindex);
            invalidateOptionsMenu();



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
        Log.e("CATURL",""+ Api.CELEBRATIONS_HOME_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.CELEBRATIONS_HOME_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                homeResponse = gson.fromJson(response, CatResponse.class);

                for (CatResponse.CategoriesBean mainCategoriesBean : homeResponse.getCategories()) {

                    if (mainCategoriesBean.getId().equals("18") || mainCategoriesBean.getCategory_name().equalsIgnoreCase("Cakes")){

                        prepareCakesData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name(),mainCategoriesBean.getModule());

                    }
                    else if (mainCategoriesBean.getId().equals("20") || mainCategoriesBean.getCategory_name().equalsIgnoreCase("Flowers")){

                        prepareFlowersData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name(),mainCategoriesBean.getModule());

                    }
                    else if (mainCategoriesBean.getId().equals("19") || mainCategoriesBean.getCategory_name().equalsIgnoreCase("Combos")){

                        prepareCombosData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name(),mainCategoriesBean.getModule());

                    }
                    else if (mainCategoriesBean.getId().equals("21") || mainCategoriesBean.getCategory_name().equalsIgnoreCase("Gift House")){

                        prepareGiftHouseData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name(),mainCategoriesBean.getModule());

                    }
                    else if (mainCategoriesBean.getId().equals("22") || mainCategoriesBean.getCategory_name().equalsIgnoreCase("Accessories") ){

                        prepareAccessoriesData(mainCategoriesBean.getId(),mainCategoriesBean.getCategory_name(),mainCategoriesBean.getModule());

                    }

                }

                // home Adapter
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false);
                catRecycler.setLayoutManager(mLayoutManager);
                catRecycler.setItemAnimator(new DefaultItemAnimator());

                adapter = new CatageoryAdapter(getApplicationContext(), homeResponse.getCategories());
                catRecycler.setAdapter(adapter);
                adapter.notifyDataSetChanged();


                // home banners
                List<Banner> banners = new ArrayList<>();
                banners.clear();
                Log.e("BANNERS", "" + banners.size());
                for (final CatResponse.BannersBean homeBannersBean : homeResponse.getBanners()) {

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

    private void prepareCakesData(final String id, final String catname,final String module) {

        simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.CELEBRATIONS_PRODUCT_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                productResponse = gson.fromJson(response, ProductResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                cakesRecycler.setLayoutManager(mLayoutManager);
                cakesRecycler.setItemAnimator(new DefaultItemAnimator());

                stationeryHomeAdapter = new ProductAadpter(getApplicationContext(), productResponse.getFiltered_products());
                cakesRecycler.setAdapter(stationeryHomeAdapter);
                stationeryHomeAdapter.notifyDataSetChanged();

                // textview
                txtCakes.setText(catname);
                cakesViewAll.setOnClickListener(new View.OnClickListener() {
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void prepareFlowersData(final String id, final String catname,final String module) {

        simpleSwipeRefreshLayout.setRefreshing(false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.CELEBRATIONS_PRODUCT_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                productResponse = gson.fromJson(response, ProductResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                flowersRecycler.setLayoutManager(mLayoutManager);
                flowersRecycler.setItemAnimator(new DefaultItemAnimator());

                stationeryHomeAdapter = new ProductAadpter(getApplicationContext(), productResponse.getFiltered_products());
                flowersRecycler.setAdapter(stationeryHomeAdapter);
                stationeryHomeAdapter.notifyDataSetChanged();


                // textview
                txtFlowersTitle.setText(catname);
                flowersViewAll.setOnClickListener(new View.OnClickListener() {
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void prepareCombosData(final String id, final String catname,final String module) {

        simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.CELEBRATIONS_PRODUCT_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                productResponse = gson.fromJson(response, ProductResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                combosRecycler.setLayoutManager(mLayoutManager);
                combosRecycler.setItemAnimator(new DefaultItemAnimator());

                stationeryHomeAdapter = new ProductAadpter(getApplicationContext(), productResponse.getFiltered_products());
                combosRecycler.setAdapter(stationeryHomeAdapter);
                stationeryHomeAdapter.notifyDataSetChanged();

                // textview
                txtCombosTitle.setText(catname);
                combosViewAll.setOnClickListener(new View.OnClickListener() {
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void prepareGiftHouseData(final String id, final String catname,final String module) {

        simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.CELEBRATIONS_PRODUCT_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                productResponse = gson.fromJson(response, ProductResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                giftHouseRecycler.setLayoutManager(mLayoutManager);
                giftHouseRecycler.setItemAnimator(new DefaultItemAnimator());

                stationeryHomeAdapter = new ProductAadpter(getApplicationContext(), productResponse.getFiltered_products());
                giftHouseRecycler.setAdapter(stationeryHomeAdapter);
                stationeryHomeAdapter.notifyDataSetChanged();

                // textview
                txtGiftHouseTitle.setText(catname);
                giftHouseViewAll.setOnClickListener(new View.OnClickListener() {
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }

    private void prepareAccessoriesData(final String id, final String catname,final String module) {

        simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.CELEBRATIONS_PRODUCT_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                productResponse = gson.fromJson(response, ProductResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                accessoriesRecycler.setLayoutManager(mLayoutManager);
                accessoriesRecycler.setItemAnimator(new DefaultItemAnimator());

                stationeryHomeAdapter = new ProductAadpter(getApplicationContext(), productResponse.getFiltered_products());
                accessoriesRecycler.setAdapter(stationeryHomeAdapter);
                stationeryHomeAdapter.notifyDataSetChanged();

                // textview
                txtAccessoriesTitle.setText(catname);
                accessoriesViewAll.setOnClickListener(new View.OnClickListener() {
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
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);


    }


    @Override
    protected void onRestart() {

        appController.cartCount(userid);
        SharedPreferences preferences =getSharedPreferences("CARTCOUNT",0);
        cartindex =preferences.getInt("itemCount",0);
        Log.e("cartindexonstart",""+cartindex);
        invalidateOptionsMenu();
        super.onRestart();
    }

    @Override
    protected void onStart() {
        appController.cartCount(userid);
        SharedPreferences preferences =getSharedPreferences("CARTCOUNT",0);
        cartindex =preferences.getInt("itemCount",0);
        Log.e("cartindexonstart",""+cartindex);
        invalidateOptionsMenu();
        super.onStart();
    }

    ///
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        menuItem.setIcon(Converter.convertLayoutToImage(CelebrationsHomeActivity.this,cartindex,R.drawable.ic_actionbar_bag));
        return true;
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

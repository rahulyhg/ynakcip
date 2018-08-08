package com.prism.pickany247.Modules.Mobiles;

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
import com.prism.pickany247.CartActivity;
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

public class MobileHomeActivity extends AppCompatActivity {
    AppController appController;
    @BindView(R.id.catRecycler)
    RecyclerView catRecycler;
    @BindView(R.id.banner_slider1)
    BannerSlider bannerSlider1;
    @BindView(R.id.txtNew)
    TextView txtNew;
    @BindView(R.id.newViewAll)
    TextView newViewAll;
    @BindView(R.id.newMobileRecycler)
    RecyclerView newMobileRecycler;
    @BindView(R.id.txtUsedMobiles)
    TextView txtUsedMobiles;
    @BindView(R.id.usedViewAll)
    TextView usedViewAll;
    @BindView(R.id.usedRecycler)
    RecyclerView usedRecycler;
    @BindView(R.id.txtTabletTitle)
    TextView txtTabletTitle;
    @BindView(R.id.tabletViewAll)
    TextView tabletViewAll;
    @BindView(R.id.tabletRecycler)
    RecyclerView tabletRecycler;
    @BindView(R.id.txtAccessoriesTitle)
    TextView txtAccessoriesTitle;
    @BindView(R.id.accessoriesViewAll)
    TextView accessoriesViewAll;
    @BindView(R.id.accessoriesRecycler)
    RecyclerView accessoriesRecycler;
    @BindView(R.id.simpleSwipeRefreshLayout)
    SwipeRefreshLayout simpleSwipeRefreshLayout;


    private CatageoryAdapter adapter;
    private ProductAadpter stationeryHomeAdapter;
    CatResponse homeResponse = new CatResponse();
    ProductResponse productResponse = new ProductResponse();
    Gson gson;
    private PrefManager pref;
    String userid;
    int  cartindex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mbile_home);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Mobiles");

        appController = (AppController) getApplication();

        pref = new PrefManager(getApplicationContext());

        // Displaying user information from shared preferences
        HashMap<String, String> profile = pref.getUserDetails();
        userid = profile.get("id");


        simpleSwipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorBlue, R.color.colorPrimary);

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

            // app.internetDilogue(KitchenitemListActivity.this);

        }


    }

    private void prepareCatData() {

        simpleSwipeRefreshLayout.setRefreshing(true);
        Log.e("CATURL", "" + Api.MOBILE_HOME_URL);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.MOBILE_HOME_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                homeResponse = gson.fromJson(response, CatResponse.class);

                for (CatResponse.CategoriesBean mainCategoriesBean : homeResponse.getCategories()) {

                    if (mainCategoriesBean.getId().equals("6") || mainCategoriesBean.getCategory_name().equalsIgnoreCase("New")) {

                        prepareNewMobileData(mainCategoriesBean.getId(), mainCategoriesBean.getCategory_name(), mainCategoriesBean.getModule());

                    } else if (mainCategoriesBean.getId().equals("7") || mainCategoriesBean.getCategory_name().equalsIgnoreCase("Used")) {

                        prepareUsedMobileData(mainCategoriesBean.getId(), mainCategoriesBean.getCategory_name(), mainCategoriesBean.getModule());

                    } else if (mainCategoriesBean.getId().equals("8") || mainCategoriesBean.getCategory_name().equalsIgnoreCase("Tablets")) {

                        prepareTabletsData(mainCategoriesBean.getId(), mainCategoriesBean.getCategory_name(), mainCategoriesBean.getModule());

                    } else if (mainCategoriesBean.getId().equals("9") || mainCategoriesBean.getCategory_name().equalsIgnoreCase("Accessories")) {

                        prepareAccessioriesData(mainCategoriesBean.getId(), mainCategoriesBean.getCategory_name(), mainCategoriesBean.getModule());

                    } /*else if (mainCategoriesBean.getId().equals("15")) {

                        prepareReadyData(mainCategoriesBean.getId(), mainCategoriesBean.getCategory_name(), mainCategoriesBean.getModule());

                    }*/

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



    private void prepareNewMobileData(final String id, final String catname,final String module) {

        simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.MOBILE_PRODUCT_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                productResponse = gson.fromJson(response, ProductResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                newMobileRecycler.setLayoutManager(mLayoutManager);
                newMobileRecycler.setItemAnimator(new DefaultItemAnimator());

                stationeryHomeAdapter = new ProductAadpter(getApplicationContext(), productResponse.getFiltered_products());
                newMobileRecycler.setAdapter(stationeryHomeAdapter);
                stationeryHomeAdapter.notifyDataSetChanged();

                // textview
                txtNew.setText(catname);
                newViewAll.setOnClickListener(new View.OnClickListener() {
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

    private void prepareUsedMobileData(final String id, final String catname,final String module) {

        simpleSwipeRefreshLayout.setRefreshing(false);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.MOBILE_PRODUCT_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                productResponse = gson.fromJson(response, ProductResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                usedRecycler.setLayoutManager(mLayoutManager);
                usedRecycler.setItemAnimator(new DefaultItemAnimator());

                stationeryHomeAdapter = new ProductAadpter(getApplicationContext(), productResponse.getFiltered_products());
                usedRecycler.setAdapter(stationeryHomeAdapter);
                stationeryHomeAdapter.notifyDataSetChanged();


                // textview
                txtUsedMobiles.setText(catname);
                usedViewAll.setOnClickListener(new View.OnClickListener() {
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

    private void prepareTabletsData(final String id, final String catname,final String module) {

        simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.MOBILE_PRODUCT_URL + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                productResponse = gson.fromJson(response, ProductResponse.class);

                // homeKitchen Adapter
                RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
                tabletRecycler.setLayoutManager(mLayoutManager);
                tabletRecycler.setItemAnimator(new DefaultItemAnimator());

                stationeryHomeAdapter = new ProductAadpter(getApplicationContext(), productResponse.getFiltered_products());
                tabletRecycler.setAdapter(stationeryHomeAdapter);
                stationeryHomeAdapter.notifyDataSetChanged();

                // textview
                txtTabletTitle.setText(catname);
                tabletViewAll.setOnClickListener(new View.OnClickListener() {
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

    private void prepareAccessioriesData(final String id, final String catname,final String module) {

        simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.MOBILE_PRODUCT_URL + id, new Response.Listener<String>() {
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.actionbar_menu, menu);
        final MenuItem menuItem = menu.findItem(R.id.action_cart);
        menuItem.setIcon(Converter.convertLayoutToImage(MobileHomeActivity.this,cartindex,R.drawable.ic_actionbar_bag));
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



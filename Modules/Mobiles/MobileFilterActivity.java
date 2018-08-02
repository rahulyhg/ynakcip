package com.prism.pickany247.Modules.Mobiles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.prism.pickany247.Adapters.MyCheckBoxAdapter;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.ProductListActivity;
import com.prism.pickany247.R;
import com.prism.pickany247.Response.CatResponse;
import com.prism.pickany247.Model.CheckBoxItem;
import com.prism.pickany247.Response.MobileFilterResponse;
import com.prism.pickany247.Singleton.AppController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MobileFilterActivity extends AppCompatActivity implements View.OnClickListener {
    List<CheckBoxItem> catItems = new ArrayList<>();
    List<CheckBoxItem> osItems = new ArrayList<>();
    List<CheckBoxItem> colorItems = new ArrayList<>();
    List<CheckBoxItem> brandItems = new ArrayList<>();
    List<CheckBoxItem> ramItems = new ArrayList<>();
    List<CheckBoxItem> storageItems = new ArrayList<>();
    List<CheckBoxItem> ratingItems = new ArrayList<>();
    MyCheckBoxAdapter myMyCheckBoxAdapter;
    CatResponse homeResponse = new CatResponse();
    MobileFilterResponse filterResponse = new MobileFilterResponse();
    Gson gson;

    String strCat = "";
    String catId, title, module;
    @BindView(R.id.rbProductType)
    RadioButton rbProductType;
    @BindView(R.id.rbOS)
    RadioButton rbOS;
    @BindView(R.id.rbBrand)
    RadioButton rbBrand;
    @BindView(R.id.rbColor)
    RadioButton rbColor;
    @BindView(R.id.rbRam)
    RadioButton rbRam;
    @BindView(R.id.rbStorage)
    RadioButton rbStorage;
    @BindView(R.id.rbRatings)
    RadioButton rbRatings;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.productTypecatList)
    ListView productTypecatList;
    @BindView(R.id.productTypeLayout)
    LinearLayout productTypeLayout;
    @BindView(R.id.osList)
    ListView osList;
    @BindView(R.id.osLayout)
    LinearLayout osLayout;
    @BindView(R.id.brandList)
    ListView brandList;
    @BindView(R.id.brandLayout)
    LinearLayout brandLayout;
    @BindView(R.id.colorList)
    ListView colorList;
    @BindView(R.id.colorLayout)
    LinearLayout colorLayout;
    @BindView(R.id.ramList)
    ListView ramList;
    @BindView(R.id.ramLayout)
    LinearLayout ramLayout;
    @BindView(R.id.storageList)
    ListView storageList;
    @BindView(R.id.storageLayout)
    LinearLayout storageLayout;
    @BindView(R.id.ratingsList)
    ListView ratingsList;
    @BindView(R.id.ratingsLayout)
    LinearLayout ratingsLayout;
    @BindView(R.id.btnApply)
    Button btnApply;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_filter);
        ButterKnife.bind(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filter");

        catId = getIntent().getStringExtra("catId");
        title = getIntent().getStringExtra("title");
        module = getIntent().getStringExtra("module");

        prepareCatData();

    }

    private void prepareCatData() {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.MOBILE_HOME_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("CAT_RESPONSE", "" + response);
                //  simpleSwipeRefreshLayout.setRefreshing(false);
                progressBar.setVisibility(View.GONE);

                gson = new Gson();
                homeResponse = gson.fromJson(response, CatResponse.class);

                catItems.clear();
                boolean value;

                for (CatResponse.CategoriesBean mainCategoriesBean : homeResponse.getCategories()) {

                    if (mainCategoriesBean.getId().equals(catId)) {

                        value = true;
                    } else {
                        value = false;
                    }

                    catItems.add(new CheckBoxItem(value, mainCategoriesBean.getId(), mainCategoriesBean.getCategory_name()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCheckBoxAdapter = new MyCheckBoxAdapter(MobileFilterActivity.this, catItems);
                // Assign adapter to ListView
                productTypecatList.setAdapter(myMyCheckBoxAdapter);
                myMyCheckBoxAdapter.notifyDataSetChanged();


                rbOS.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        productTypeLayout.setVisibility(View.GONE);
                        osLayout.setVisibility(View.VISIBLE);
                        brandLayout.setVisibility(View.GONE);
                        ramLayout.setVisibility(View.GONE);
                        storageLayout.setVisibility(View.GONE);
                        colorLayout.setVisibility(View.GONE);
                        ratingsLayout.setVisibility(View.GONE);

                        String catStr = "";
                        for (int i = 0; i < catItems.size(); i++) {
                            if (catItems.get(i).isChecked()) {
                                catStr += catItems.get(i).getId() + ",";

                            }
                        }

                        if (catStr.length() > 0) {
                            strCat = catStr.substring(0, catStr.length() - 1);
                            Log.e("CHECKBOXES", "" + strCat);
                            //  Toast.makeText(StationeryFilterActivity.this, strone, Toast.LENGTH_LONG).show();
                            prepareOstData(strCat);
                            prepareBrandData(strCat);
                            prepareColorData(strCat);
                            prepareRamData(strCat);
                            prepareStorageData(strCat);
                            prepareRatingData(strCat);

                        } else {
                            System.out.println(catStr);
                        }
                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // simpleSwipeRefreshLayout.setRefreshing(true);
                progressBar.setVisibility(View.GONE);
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

    private void prepareOstData(String catId) {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.MOBILE_FILTER_URL + catId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("SUB_CAT_RESPONSE", "" + response);
                progressBar.setVisibility(View.GONE);

                gson = new Gson();
                filterResponse = gson.fromJson(response, MobileFilterResponse.class);

                osItems.clear();
                boolean value;

                for (MobileFilterResponse.OperatingSystemBean operatingSystemBean : filterResponse.getOperating_System()) {

                    osItems.add(new CheckBoxItem(false, operatingSystemBean.getId(), operatingSystemBean.getName()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCheckBoxAdapter = new MyCheckBoxAdapter(MobileFilterActivity.this, osItems);
                // ListView listView = (ListView) findViewById(R.id.catList);
                // Assign adapter to ListView
                osList.setAdapter(myMyCheckBoxAdapter);
                myMyCheckBoxAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
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

    private void prepareBrandData(String catId) {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.MOBILE_FILTER_URL + catId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                progressBar.setVisibility(View.GONE);
                gson = new Gson();
                filterResponse = gson.fromJson(response, MobileFilterResponse.class);
                brandItems = new ArrayList<>();
                brandItems.clear();
                boolean value;

                for (MobileFilterResponse.BrandsBean brandsBean : filterResponse.getBrands()) {

                    brandItems.add(new CheckBoxItem(false, brandsBean.getId(), brandsBean.getName()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCheckBoxAdapter = new MyCheckBoxAdapter(MobileFilterActivity.this, brandItems);
                // ListView listView = (ListView) findViewById(R.id.catList);
                // Assign adapter to ListView
                brandList.setAdapter(myMyCheckBoxAdapter);
                myMyCheckBoxAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
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

    private void prepareColorData(String catId) {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.MOBILE_FILTER_URL + catId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                progressBar.setVisibility(View.GONE);
                gson = new Gson();
                filterResponse = gson.fromJson(response, MobileFilterResponse.class);
                colorItems = new ArrayList<>();
                colorItems.clear();
                boolean value;

                for (MobileFilterResponse.ColorsBean colorsBean : filterResponse.getColors()) {

                    colorItems.add(new CheckBoxItem(false, colorsBean.getId(), colorsBean.getName()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCheckBoxAdapter = new MyCheckBoxAdapter(MobileFilterActivity.this, colorItems);
                // ListView listView = (ListView) findViewById(R.id.catList);
                // Assign adapter to ListView
                colorList.setAdapter(myMyCheckBoxAdapter);
                myMyCheckBoxAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
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

    private void prepareRamData(String catId) {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.MOBILE_FILTER_URL + catId, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {

                Log.e("RAM_RESPONSE", "" + response);
                progressBar.setVisibility(View.GONE);
                gson = new Gson();
                filterResponse = gson.fromJson(response, MobileFilterResponse.class);
                ramItems.clear();
                boolean value;

                for (MobileFilterResponse.RamBean ramBean : filterResponse.getRam()) {

                    ramItems.add(new CheckBoxItem(false, ramBean.getId(), ramBean.getName()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCheckBoxAdapter = new MyCheckBoxAdapter(MobileFilterActivity.this, ramItems);
                // ListView listView = (ListView) findViewById(R.id.catList);
                // Assign adapter to ListView
                ramList.setAdapter(myMyCheckBoxAdapter);
                myMyCheckBoxAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
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


    private void prepareStorageData(String catId) {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.MOBILE_FILTER_URL + catId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                progressBar.setVisibility(View.GONE);
                gson = new Gson();
                filterResponse = gson.fromJson(response, MobileFilterResponse.class);
                storageItems = new ArrayList<>();
                storageItems.clear();
                boolean value;

                for (MobileFilterResponse.StorageBean storageBean : filterResponse.getStorage()) {

                    storageItems.add(new CheckBoxItem(false, storageBean.getId(), storageBean.getName()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCheckBoxAdapter = new MyCheckBoxAdapter(MobileFilterActivity.this, storageItems);
                // ListView listView = (ListView) findViewById(R.id.catList);
                // Assign adapter to ListView
                storageList.setAdapter(myMyCheckBoxAdapter);
                myMyCheckBoxAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
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


    private void prepareRatingData(String catId) {

        progressBar.setVisibility(View.VISIBLE);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_FILTER_URL + catId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                progressBar.setVisibility(View.GONE);

                gson = new Gson();
                filterResponse = gson.fromJson(response, MobileFilterResponse.class);
                ratingItems = new ArrayList<>();
                catItems.clear();


                for (MobileFilterResponse.RatingBean ratingBean : filterResponse.getRating()) {

                    ratingItems.add(new CheckBoxItem(false, "1", "1.0 & above  (" + ratingBean.getRating1() + " )"));
                    ratingItems.add(new CheckBoxItem(false, "2", "2.0 & above  (" + ratingBean.getRating2() + " )"));
                    ratingItems.add(new CheckBoxItem(false, "3", "3.0 & above  (" + ratingBean.getRating3() + " )"));
                    ratingItems.add(new CheckBoxItem(false, "4", "4.0 & above  (" + ratingBean.getRating4() + " )"));
                    ratingItems.add(new CheckBoxItem(false, "5", "5.0 & above  (" + ratingBean.getRating5() + " )"));

                }

                //create an ArrayAdaptar from the String Array
                myMyCheckBoxAdapter = new MyCheckBoxAdapter(MobileFilterActivity.this, ratingItems);
                // ListView listView = (ListView) findViewById(R.id.catList);
                // Assign adapter to ListView
                ratingsList.setAdapter(myMyCheckBoxAdapter);
                myMyCheckBoxAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility(View.GONE);
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
                break;

        }
        return super.onOptionsItemSelected(item);
    }


    @OnClick({R.id.rbProductType, R.id.rbOS, R.id.rbBrand, R.id.rbColor, R.id.rbRam, R.id.rbStorage, R.id.rbRatings, R.id.btnApply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbProductType:
                prepareCatData();
                productTypeLayout.setVisibility(View.VISIBLE);
                osLayout.setVisibility(View.GONE);
                brandLayout.setVisibility(View.GONE);
                ramLayout.setVisibility(View.GONE);
                storageLayout.setVisibility(View.GONE);
                colorLayout.setVisibility(View.GONE);
                ratingsLayout.setVisibility(View.GONE);

                break;
            /*case R.id.rbOS:
                productTypeLayout.setVisibility(View.GONE);
                osLayout.setVisibility(View.VISIBLE);
                brandLayout.setVisibility(View.GONE);
                ramLayout.setVisibility(View.GONE);
                storageLayout.setVisibility(View.GONE);
                colorLayout.setVisibility(View.GONE);
                ratingsLayout.setVisibility(View.GONE);

                break;*/
            case R.id.rbBrand:
                productTypeLayout.setVisibility(View.GONE);
                osLayout.setVisibility(View.GONE);
                brandLayout.setVisibility(View.VISIBLE);
                ramLayout.setVisibility(View.GONE);
                storageLayout.setVisibility(View.GONE);
                colorLayout.setVisibility(View.GONE);
                ratingsLayout.setVisibility(View.GONE);

                break;
            case R.id.rbColor:
                productTypeLayout.setVisibility(View.GONE);
                osLayout.setVisibility(View.GONE);
                brandLayout.setVisibility(View.GONE);
                ramLayout.setVisibility(View.GONE);
                storageLayout.setVisibility(View.GONE);
                colorLayout.setVisibility(View.VISIBLE);
                ratingsLayout.setVisibility(View.GONE);

                break;
            case R.id.rbRam:
                productTypeLayout.setVisibility(View.GONE);
                osLayout.setVisibility(View.GONE);
                brandLayout.setVisibility(View.GONE);
                ramLayout.setVisibility(View.VISIBLE);
                storageLayout.setVisibility(View.GONE);
                colorLayout.setVisibility(View.GONE);
                ratingsLayout.setVisibility(View.GONE);

                break;
            case R.id.rbStorage:
                productTypeLayout.setVisibility(View.GONE);
                osLayout.setVisibility(View.GONE);
                brandLayout.setVisibility(View.GONE);
                ramLayout.setVisibility(View.GONE);
                storageLayout.setVisibility(View.VISIBLE);
                colorLayout.setVisibility(View.GONE);
                ratingsLayout.setVisibility(View.GONE);

                break;
            case R.id.rbRatings:
                productTypeLayout.setVisibility(View.GONE);
                osLayout.setVisibility(View.GONE);
                brandLayout.setVisibility(View.GONE);
                ramLayout.setVisibility(View.GONE);
                storageLayout.setVisibility(View.GONE);
                colorLayout.setVisibility(View.GONE);
                ratingsLayout.setVisibility(View.VISIBLE);

                break;

            case R.id.btnApply:
                // cat data
               /* String catStr = "";

                for (int i = 0; i < catItems.size(); i++) {
                    if (catItems.get(i).isChecked()) {
                        catStr += catItems.get(i).getId() + ",";

                    }
                }

                if (catStr.length() > 0) {
                    String strone = catStr.substring(0, catStr.length() - 1);
                    Log.e("CHECKBOXESONE", "" + strone);


                } else {
                    System.out.println(catStr);
                }*/

                Log.e("CHECKBOXESONE", "" + strCat);


                // subcat data
                String os = "";
                String strOs = "";
                for (CheckBoxItem checkBox : osItems) {

                    if (checkBox.checked) {
                        os += checkBox.getItemString() + ",";
                    }
                }
                if (os.length() > 0) {
                    strOs = os.substring(0, os.length() - 1);
                    Log.e("strSubCat", "" + strOs);
                }

                // brand data
                String brand = "";
                String strBrand = "";
                for (CheckBoxItem checkBox : brandItems) {

                    if (checkBox.checked) {
                        brand += checkBox.getId() + ",";
                    }
                }
                if (brand.length() > 0) {
                    strBrand = brand.substring(0, brand.length() - 1);
                    Log.e("strBrand", "" + strBrand);
                }

                // Ram data
                String ram = "";
                String strRam = "";
                for (CheckBoxItem checkBox : ramItems) {

                    if (checkBox.checked) {
                        ram += checkBox.getItemString() + ",";
                    }
                }
                if (ram.length() > 0) {
                    strRam = ram.substring(0, ram.length() - 1);
                    Log.e("strProductType", "" + strRam);
                }


                // Storage data
                String storage = "";
                String strStorage = "";
                for (CheckBoxItem checkBox : ramItems) {

                    if (checkBox.checked) {
                        storage += checkBox.getItemString() + ",";
                    }
                }
                if (storage.length() > 0) {
                    strStorage = storage.substring(0, storage.length() - 1);
                    Log.e("strProductType", "" + strStorage);
                }

                // color data
                String color = "";
                String strColor = "";
                for (CheckBoxItem checkBox : colorItems) {

                    if (checkBox.checked) {
                        color += checkBox.getItemString() + ",";
                    }
                }
                if (color.length() > 0) {
                    strColor = color.substring(0, color.length() - 1);
                    Log.e("strColor", "" + strColor);
                }


                // rating data
                String rating = "";
                String strRating = "";
                for (CheckBoxItem checkBox : ratingItems) {

                    if (checkBox.checked) {
                        rating += checkBox.getId() + ",";
                    }
                }
                if (rating.length() > 0) {
                    strRating = rating.substring(0, rating.length() - 1);
                    Log.e("strRating", "" + strRating);
                }


                String finalvalue = "";
                if (strCat.equals("")) {
                    finalvalue = catId + "&os=" + strOs + "&ram="+strRam +"&storage="+storage+ "&brand=" + strBrand  + "&color=" + strColor + "&rating=" + strRating;

                } else {
                    finalvalue = strCat + "&os=" + strOs + "&ram="+strRam +"&storage="+storage+ "&brand=" + strBrand  + "&color=" + strColor + "&rating=" + strRating;

                }

                Log.e("RESULTSTRING", "" + finalvalue);

                Intent intent = new Intent(getApplicationContext(), ProductListActivity.class);
                intent.putExtra("catId", finalvalue);
                intent.putExtra("title", title);
                intent.putExtra("module", module);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;
        }
    }
}

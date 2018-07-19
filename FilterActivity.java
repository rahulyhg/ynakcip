package com.prism.pickany247;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.interfaces.OnRangeSeekbarFinalValueListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalRangeSeekbar;
import com.google.gson.Gson;
import com.prism.pickany247.Adapters.MyCustomAdapter;
import com.prism.pickany247.Apis.Api;
import com.prism.pickany247.Response.CheckBoxItem;
import com.prism.pickany247.Response.StationerFilterResponse;
import com.prism.pickany247.Response.StationeryCatResponse;
import com.prism.pickany247.Response.StationerySubCatResponse;
import com.prism.pickany247.Singleton.AppController;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener{
    List<CheckBoxItem> checkBoxItems =new ArrayList<>();
    MyCustomAdapter myMyCustomAdapter;
    StationeryCatResponse homeResponse = new StationeryCatResponse();
    StationerFilterResponse filterResponse = new StationerFilterResponse();
    Gson gson;

    @BindView(R.id.rbPrice)
    RadioButton rbPrice;
    @BindView(R.id.rbCat)
    RadioButton rbCat;
    @BindView(R.id.rbSubCat)
    RadioButton rbSubCat;
    @BindView(R.id.rbBrand)
    RadioButton rbBrand;
    @BindView(R.id.rbProductType)
    RadioButton rbProductType;
    @BindView(R.id.rbColor)
    RadioButton rbColor;
    @BindView(R.id.rbRatings)
    RadioButton rbRatings;
    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;
    @BindView(R.id.rangeSeekbar3)
    CrystalRangeSeekbar rangeSeekbar3;
    @BindView(R.id.textMin1)
    TextView textMin1;
    @BindView(R.id.textMax1)
    TextView textMax1;
    @BindView(R.id.priceLayout)
    LinearLayout priceLayout;
    @BindView(R.id.catList)
    ListView catList;
    @BindView(R.id.catLayout)
    LinearLayout catLayout;
    @BindView(R.id.subcatList)
    ListView subcatList;
    @BindView(R.id.subcatLayout)
    LinearLayout subcatLayout;
    @BindView(R.id.brandList)
    ListView brandList;
    @BindView(R.id.brandLayout)
    LinearLayout brandLayout;
    @BindView(R.id.productTypecatList)
    ListView productTypecatList;
    @BindView(R.id.productTypeLayout)
    LinearLayout productTypeLayout;
    @BindView(R.id.colorList)
    ListView colorList;
    @BindView(R.id.colorLayout)
    LinearLayout colorLayout;
    @BindView(R.id.ratingsList)
    ListView ratingsList;
    @BindView(R.id.ratingsLayout)
    LinearLayout ratingsLayout;
    @BindView(R.id.btnApply)
    Button btnApply;

    String catId,title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filter");

        ButterKnife.bind(this);

         catId = getIntent().getStringExtra("catId");
         title =getIntent().getStringExtra("title");

         // price range
        priceRange();

    }

    private void priceRange() {

        // set listener
        rangeSeekbar3.setOnRangeSeekbarChangeListener(new OnRangeSeekbarChangeListener() {
            @Override
            public void valueChanged(Number minValue, Number maxValue) {
                textMin1.setText(String.valueOf(minValue));
                textMax1.setText(String.valueOf(maxValue));
            }
        });

        // set final value listener
        rangeSeekbar3.setOnRangeSeekbarFinalValueListener(new OnRangeSeekbarFinalValueListener() {
            @Override
            public void finalValue(Number minValue, Number maxValue) {
                Log.d("CRS=>", String.valueOf(minValue) + " : " + String.valueOf(maxValue));
            }
        });

    }

    private void prepareCatData() {

        Log.e("PRINTCAT",""+"print");

        //  simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_HOME_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                //  simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                homeResponse = gson.fromJson(response, StationeryCatResponse.class);

                checkBoxItems.clear();
                boolean value;

                for (StationeryCatResponse.CategoriesBean mainCategoriesBean : homeResponse.getCategories()) {

                    if (mainCategoriesBean.getId().equals(catId)) {

                        value = true;
                    } else {
                        value = false;
                    }

                    checkBoxItems.add(new CheckBoxItem(value,mainCategoriesBean.getId(), mainCategoriesBean.getCategory_name()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCustomAdapter = new MyCustomAdapter(FilterActivity.this, checkBoxItems);
                // Assign adapter to ListView
                catList.setAdapter(myMyCustomAdapter);
                myMyCustomAdapter.notifyDataSetChanged();

                // sub cat checkBoxItems
                rbSubCat.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onClick(View v) {
                        priceLayout.setVisibility(View.GONE);
                        catLayout.setVisibility(View.GONE);
                        subcatLayout.setVisibility(View.VISIBLE);
                        brandLayout.setVisibility(View.GONE);
                        productTypeLayout.setVisibility(View.GONE);
                        colorLayout.setVisibility(View.GONE);
                        ratingsLayout.setVisibility(View.GONE);

                        String catStr = "";

                        for (int i = 0; i< checkBoxItems.size(); i++){
                            if (checkBoxItems.get(i).isChecked()){
                                catStr += checkBoxItems.get(i).getId() + ",";

                            }
                        }
                        if(catStr.length()>0){
                            String strone =catStr.substring(0,catStr.length() - 1);
                            Log.e("CHECKBOXES",""+strone);
                            Toast.makeText(FilterActivity.this, strone, Toast.LENGTH_LONG).show();
                            prepareSubCatData(strone);

                        }else{
                            System.out.println(catStr);
                        }




                    }
                });


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // simpleSwipeRefreshLayout.setRefreshing(true);
            }
        });
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
       /* RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);*/


    }

    private void prepareSubCatData(String catId) {

        //  simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_SUB_CATEGORIES_URL + catId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                //  simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                StationerySubCatResponse homeResponse = gson.fromJson(response, StationerySubCatResponse.class);

                checkBoxItems.clear();
                boolean value;

                for (StationerySubCatResponse.FSubCatListBean subCatListBean : homeResponse.getFSubCatList()) {

                    checkBoxItems.add(new CheckBoxItem(false, subCatListBean.getSub_category_id(), subCatListBean.getSub_category_name()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCustomAdapter = new MyCustomAdapter(FilterActivity.this, checkBoxItems);
                // ListView listView = (ListView) findViewById(R.id.catList);
                // Assign adapter to ListView
                subcatList.setAdapter(myMyCustomAdapter);
                myMyCustomAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // simpleSwipeRefreshLayout.setRefreshing(true);
            }
        });
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
       /* RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);*/


    }

    private void prepareBrandData() {

        //  simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_FILTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                //  simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                filterResponse = gson.fromJson(response, StationerFilterResponse.class);
                checkBoxItems = new ArrayList<>();
                checkBoxItems.clear();
                boolean value;

                for (StationerFilterResponse.BrandsBean brandsBean : filterResponse.getBrands()) {

                    checkBoxItems.add(new CheckBoxItem(false, brandsBean.getId(), brandsBean.getBrand_name()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCustomAdapter = new MyCustomAdapter(FilterActivity.this, checkBoxItems);
                // ListView listView = (ListView) findViewById(R.id.catList);
                // Assign adapter to ListView
                brandList.setAdapter(myMyCustomAdapter);
                myMyCustomAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // simpleSwipeRefreshLayout.setRefreshing(true);
            }
        });
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
       /* RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);*/


    }

    private void prepareProductTypeData() {

        //  simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_FILTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                //  simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                filterResponse = gson.fromJson(response, StationerFilterResponse.class);
                checkBoxItems = new ArrayList<>();
                checkBoxItems.clear();
                boolean value;

                for (StationerFilterResponse.ProductTypesBean productTypesBean : filterResponse.getProduct_Types()) {

                    checkBoxItems.add(new CheckBoxItem(false, "", productTypesBean.getProduct_type()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCustomAdapter = new MyCustomAdapter(FilterActivity.this, checkBoxItems);
                // ListView listView = (ListView) findViewById(R.id.catList);
                // Assign adapter to ListView
                productTypecatList.setAdapter(myMyCustomAdapter);
                myMyCustomAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // simpleSwipeRefreshLayout.setRefreshing(true);
            }
        });
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
       /* RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);*/


    }

    private void prepareColorData() {

        //  simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_FILTER_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                //  simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                filterResponse = gson.fromJson(response, StationerFilterResponse.class);
                checkBoxItems = new ArrayList<>();
                checkBoxItems.clear();
                boolean value;

                for (StationerFilterResponse.ColorsBean colorsBean : filterResponse.getColors()) {

                    checkBoxItems.add(new CheckBoxItem(false,colorsBean.getId(), colorsBean.getName()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCustomAdapter = new MyCustomAdapter(FilterActivity.this, checkBoxItems);
                // ListView listView = (ListView) findViewById(R.id.catList);
                // Assign adapter to ListView
                colorList.setAdapter(myMyCustomAdapter);
                myMyCustomAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // simpleSwipeRefreshLayout.setRefreshing(true);
            }
        });
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
       /* RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);*/


    }

    private void prepareRatingData() {

        //  simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_FILTER_URL , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                //  simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                filterResponse = gson.fromJson(response, StationerFilterResponse.class);
                checkBoxItems = new ArrayList<>();
                checkBoxItems.clear();
                boolean value;

                for (StationerFilterResponse.RatingBean ratingBean : filterResponse.getRating()) {

                    checkBoxItems.add(new CheckBoxItem(false, ratingBean.getRating1(), ratingBean.getRating1()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCustomAdapter = new MyCustomAdapter(FilterActivity.this, checkBoxItems);
                // ListView listView = (ListView) findViewById(R.id.catList);
                // Assign adapter to ListView
                ratingsList.setAdapter(myMyCustomAdapter);
                myMyCustomAdapter.notifyDataSetChanged();


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // simpleSwipeRefreshLayout.setRefreshing(true);
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

    @OnClick({R.id.rbPrice, R.id.rbCat, R.id.rbSubCat, R.id.rbBrand, R.id.rbProductType, R.id.rbColor, R.id.rbRatings, R.id.btnApply})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rbPrice:

                priceRange();
                priceLayout.setVisibility(View.VISIBLE);
                catLayout.setVisibility(View.GONE);
                subcatLayout.setVisibility(View.GONE);
                brandLayout.setVisibility(View.GONE);
                productTypeLayout.setVisibility(View.GONE);
                colorLayout.setVisibility(View.GONE);
                ratingsLayout.setVisibility(View.GONE);

                break;
            case R.id.rbCat:

                prepareCatData();
                priceLayout.setVisibility(View.GONE);
                catLayout.setVisibility(View.VISIBLE);
                subcatLayout.setVisibility(View.GONE);
                brandLayout.setVisibility(View.GONE);
                productTypeLayout.setVisibility(View.GONE);
                colorLayout.setVisibility(View.GONE);
                ratingsLayout.setVisibility(View.GONE);

                break;
           /* case R.id.rbSubCat:

                priceLayout.setVisibility(View.GONE);
                catLayout.setVisibility(View.GONE);
                subcatLayout.setVisibility(View.VISIBLE);
                brandLayout.setVisibility(View.GONE);
                productTypeLayout.setVisibility(View.GONE);
                colorLayout.setVisibility(View.GONE);
                ratingsLayout.setVisibility(View.GONE);

                break;*/
            case R.id.rbBrand:

               // prepareBrandData();
                priceLayout.setVisibility(View.GONE);
                catLayout.setVisibility(View.GONE);
                subcatLayout.setVisibility(View.GONE);
                brandLayout.setVisibility(View.VISIBLE);
                productTypeLayout.setVisibility(View.GONE);
                colorLayout.setVisibility(View.GONE);
                ratingsLayout.setVisibility(View.GONE);

                break;
            case R.id.rbProductType:

              //  prepareProductTypeData();
                priceLayout.setVisibility(View.GONE);
                catLayout.setVisibility(View.GONE);
                subcatLayout.setVisibility(View.GONE);
                brandLayout.setVisibility(View.GONE);
                productTypeLayout.setVisibility(View.VISIBLE);
                colorLayout.setVisibility(View.GONE);
                ratingsLayout.setVisibility(View.GONE);

                break;
            case R.id.rbColor:

              //  prepareColorData();
                priceLayout.setVisibility(View.GONE);
                catLayout.setVisibility(View.GONE);
                subcatLayout.setVisibility(View.GONE);
                brandLayout.setVisibility(View.GONE);
                productTypeLayout.setVisibility(View.GONE);
                colorLayout.setVisibility(View.VISIBLE);
                ratingsLayout.setVisibility(View.GONE);


                break;
            case R.id.rbRatings:

              //  prepareRatingData();
                priceLayout.setVisibility(View.GONE);
                catLayout.setVisibility(View.GONE);
                subcatLayout.setVisibility(View.GONE);
                brandLayout.setVisibility(View.GONE);
                productTypeLayout.setVisibility(View.GONE);
                colorLayout.setVisibility(View.GONE);
                ratingsLayout.setVisibility(View.VISIBLE);


                break;
            case R.id.btnApply:

                String catStr = "Check checkBoxItems:\n";

                for (int i = 0; i< checkBoxItems.size(); i++){
                    if (checkBoxItems.get(i).isChecked()){
                        catStr += checkBoxItems.get(i).getId() + ",";

                    }
                }
                String strone =catStr.substring(0,catStr.length() - 1);
                Log.e("CHECKBOXES",""+strone);


                break;
        }
    }


}

package com.prism.pickany247;

import android.content.Intent;
import android.os.Bundle;
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
import com.prism.pickany247.Singleton.AppController;
import com.prism.pickany247.StationeryModule.ProductListActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FilterActivity extends AppCompatActivity implements View.OnClickListener {
    List<CheckBoxItem> catItems = new ArrayList<>();
    List<CheckBoxItem> subCatItems = new ArrayList<>();
    List<CheckBoxItem> productTypeItems = new ArrayList<>();
    List<CheckBoxItem> colorItems = new ArrayList<>();
    List<CheckBoxItem> brandItems = new ArrayList<>();
    List<CheckBoxItem> ratingItems = new ArrayList<>();
    MyCustomAdapter myMyCustomAdapter;
    StationeryCatResponse homeResponse = new StationeryCatResponse();
    StationerFilterResponse filterResponse = new StationerFilterResponse();
    Gson gson;
    String strCat="";

    String catId, title;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Filter");

        ButterKnife.bind(this);

        catId = getIntent().getStringExtra("catId");
        title = getIntent().getStringExtra("title");


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

                Log.e("VALUEMX",""+textMax1.getText().toString());
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

        //  simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_HOME_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("CAT_RESPONSE", "" + response);
                //  simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                homeResponse = gson.fromJson(response, StationeryCatResponse.class);

                catItems.clear();
                boolean value;

                for (StationeryCatResponse.CategoriesBean mainCategoriesBean : homeResponse.getCategories()) {

                    if (mainCategoriesBean.getId().equals(catId)) {

                        value = true;
                    } else {
                        value = false;
                    }

                    catItems.add(new CheckBoxItem(value, mainCategoriesBean.getId(), mainCategoriesBean.getCategory_name()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCustomAdapter = new MyCustomAdapter(FilterActivity.this, catItems);
                // Assign adapter to ListView
                catList.setAdapter(myMyCustomAdapter);
                myMyCustomAdapter.notifyDataSetChanged();



                rbSubCat.setOnClickListener(new View.OnClickListener() {
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
                        for (int i = 0; i < catItems.size(); i++) {
                            if (catItems.get(i).isChecked()) {
                                catStr += catItems.get(i).getId() + ",";

                            }
                        }

                        if (catStr.length() > 0) {
                            strCat = catStr.substring(0, catStr.length() - 1);
                            Log.e("CHECKBOXES", "" + strCat);
                            //  Toast.makeText(FilterActivity.this, strone, Toast.LENGTH_LONG).show();
                            prepareSubCatData(strCat);
                            prepareBrandData(strCat);
                            prepareColorData(strCat);
                            prepareProductTypeData(strCat);
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
            }
        });
// Adding request to request queue
        AppController.getInstance().addToRequestQueue(stringRequest);
       /* RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        requestQueue.add(stringRequest);*/


    }

    private void prepareSubCatData(String catId) {

        //  simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_FILTER_URL + catId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("SUB_CAT_RESPONSE", "" + response);
                //  simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                StationerFilterResponse homeResponse = gson.fromJson(response, StationerFilterResponse.class);

                subCatItems.clear();
                boolean value;

                for (StationerFilterResponse.SubCategoryBean subCatListBean : homeResponse.getSub_Category()) {

                    subCatItems.add(new CheckBoxItem(false, subCatListBean.getSub_category_id(), subCatListBean.getSub_category_name()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCustomAdapter = new MyCustomAdapter(FilterActivity.this, subCatItems);
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

    private void prepareBrandData(String catId) {

        //  simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_FILTER_URL + catId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                //  simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                filterResponse = gson.fromJson(response, StationerFilterResponse.class);
                brandItems = new ArrayList<>();
                brandItems.clear();
                boolean value;

                for (StationerFilterResponse.BrandsBean brandsBean : filterResponse.getBrands()) {

                    brandItems.add(new CheckBoxItem(false, brandsBean.getId(), brandsBean.getBrand_name()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCustomAdapter = new MyCustomAdapter(FilterActivity.this, brandItems);
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

    private void prepareProductTypeData(String catId) {

        //  simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_FILTER_URL + catId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                //  simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                filterResponse = gson.fromJson(response, StationerFilterResponse.class);
                productTypeItems = new ArrayList<>();
                productTypeItems.clear();
                boolean value;

                for (StationerFilterResponse.ProductTypesBean productTypesBean : filterResponse.getProduct_Types()) {

                    productTypeItems.add(new CheckBoxItem(false, "", productTypesBean.getProduct_type()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCustomAdapter = new MyCustomAdapter(FilterActivity.this, productTypeItems);
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

    private void prepareColorData(String catId) {

        //  simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_FILTER_URL + catId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                //  simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                filterResponse = gson.fromJson(response, StationerFilterResponse.class);
                colorItems = new ArrayList<>();
                colorItems.clear();
                boolean value;

                for (StationerFilterResponse.ColorsBean colorsBean : filterResponse.getColors()) {

                    colorItems.add(new CheckBoxItem(false, colorsBean.getId(), colorsBean.getName()));

                }

                //create an ArrayAdaptar from the String Array
                myMyCustomAdapter = new MyCustomAdapter(FilterActivity.this, colorItems);
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

    private void prepareRatingData(String catId) {

        //  simpleSwipeRefreshLayout.setRefreshing(true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET, Api.STATIONERY_FILTER_URL + catId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Log.e("RESPONSE", "" + response);
                //  simpleSwipeRefreshLayout.setRefreshing(false);

                gson = new Gson();
                filterResponse = gson.fromJson(response, StationerFilterResponse.class);
                ratingItems = new ArrayList<>();
                catItems.clear();


                for (StationerFilterResponse.RatingBean ratingBean : filterResponse.getRating()) {

                    ratingItems.add(new CheckBoxItem(false, "1", "1.0 & above  (" + ratingBean.getRating1() + " )"));
                    ratingItems.add(new CheckBoxItem(false, "2", "2.0 & above  (" + ratingBean.getRating2() + " )"));
                    ratingItems.add(new CheckBoxItem(false, "3", "3.0 & above  (" + ratingBean.getRating3() + " )"));
                    ratingItems.add(new CheckBoxItem(false, "4", "4.0 & above  (" + ratingBean.getRating4() + " )"));
                    ratingItems.add(new CheckBoxItem(false, "5", "5.0 & above  (" + ratingBean.getRating5() + " )"));

                }

                //create an ArrayAdaptar from the String Array
                myMyCustomAdapter = new MyCustomAdapter(FilterActivity.this, ratingItems);
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
                String subCat = "";
                for (CheckBoxItem checkBox : subCatItems) {

                    if (checkBox.checked) {
                        subCat += checkBox.getId() + ",";
                    }
                }
                if (subCat.length() > 0) {
                    String strSubCat = subCat.substring(0, subCat.length() - 1);
                    Log.e("strSubCat", "" + strSubCat);
                }

                // brand data
                String brand = "";
                for (CheckBoxItem checkBox : brandItems) {

                    if (checkBox.checked) {
                        brand += checkBox.getId() + ",";
                    }
                }
                if (brand.length() > 0) {
                    String strBrand = brand.substring(0, brand.length() - 1);
                    Log.e("strBrand", "" + strBrand);
                }

                // product data
                String productType = "";
                for (CheckBoxItem checkBox : productTypeItems) {

                    if (checkBox.checked) {
                        productType += checkBox.getItemString() + ",";
                    }
                }
                if (productType.length() > 0) {
                    String strProductType = productType.substring(0, productType.length() - 1);
                    Log.e("strProductType", "" + strProductType);
                }

                // color data
                String color = "";
                for (CheckBoxItem checkBox : colorItems) {

                    if (checkBox.checked) {
                        color += checkBox.getItemString() + ",";
                    }
                }
                if (color.length() > 0) {
                    String strColor = color.substring(0, color.length() - 1);
                    Log.e("strColor", "" + strColor);
                }


                // rating data
                String rating = "";
                for (CheckBoxItem checkBox : ratingItems) {

                    if (checkBox.checked) {
                        rating += checkBox.getId() + ",";
                    }
                }
                if (rating.length() > 0) {
                    String strRating = rating.substring(0, rating.length() - 1);
                    Log.e("strRating", "" + strRating);
                }
                 String finalvalue="";
                if (strCat.equals("")){
                     finalvalue =catId+"&subcatId="+subCat+"&priceRange="+textMin1.getText().toString()+","+textMax1.getText().toString()+"&brand="+brand+"&productType="+productType+"&color="+color+"&rating="+rating;

                }
                else {
                    finalvalue =strCat+"&subcatId="+subCat+"&priceRange="+textMin1.getText().toString()+","+textMax1.getText().toString()+"&brand="+brand+"&productType="+productType+"&color="+color+"&rating="+rating;
                }

                Log.e("RESULTSTRING",""+finalvalue);

                Intent intent =new Intent(getApplicationContext(),ProductListActivity.class);
                intent.putExtra("catId", finalvalue);
                intent.putExtra("title", title);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out);

                break;
        }
    }
}
